"""JSON-LD ingestion helpers for Qdrant."""

from __future__ import annotations

import gzip
import json
from pathlib import Path
from typing import Any


def load_jsonld_graph(path: Path) -> list[dict[str, Any]]:
    """Read JSON-LD file and return graph node list."""
    if path.suffix == ".gz":
        with gzip.open(path, "rt", encoding="utf-8") as fh:
            payload = json.loads(fh.read())
    else:
        payload = json.loads(path.read_text(encoding="utf-8"))
    graph = payload.get("@graph", [])
    if not isinstance(graph, list):
        raise ValueError(f"JSON-LD '@graph' is not a list in {path}")
    return graph


def _has_type(node: dict[str, Any], type_id: str) -> bool:
    values = node.get("@type", [])
    if isinstance(values, str):
        values = [values]
    return type_id in values


def _lang_value(value: Any) -> str:
    """Extract '@value' from JSON-LD language strings, otherwise stringify."""
    if isinstance(value, dict) and "@value" in value:
        return str(value.get("@value") or "")
    if value is None:
        return ""
    return str(value)


def _id_value(value: Any) -> str:
    """Extract '@id' from JSON-LD IRI objects, otherwise stringify."""
    if isinstance(value, dict) and "@id" in value:
        return str(value.get("@id") or "")
    if value is None:
        return ""
    return str(value)


def _id_list(value: Any) -> list[str]:
    """Extract a list of IRIs from a JSON-LD value that may be a list or a single object."""
    if not value:
        return []
    if isinstance(value, list):
        values = [_id_value(item) for item in value]
        return [v for v in values if v]
    single = _id_value(value)
    return [single] if single else []


def select_rule_concepts(nodes: list[dict[str, Any]]) -> list[dict[str, Any]]:
    """Select SKOS concepts related to rules/glossary.

    This includes rules, glossary terms, sections, and chapters.
    """
    selected = []
    for node in nodes:
        if _has_type(node, "skos:Concept"):
            selected.append(node)
    return selected


def select_card_concepts(nodes: list[dict[str, Any]]) -> list[dict[str, Any]]:
    """Select card objects from card JSON-LD graphs."""
    selected = []
    for node in nodes:
        # Scryfall card objects have {"object": "card"}.
        if node.get("object") == "card":
            selected.append(node)
    return selected


def rules_text_builder(node: dict[str, Any]) -> str:
    """Build vector text for rules concepts."""
    label_obj = node.get("skos:prefLabel") or node.get("rdfs:label") or ""
    label = _lang_value(label_obj)

    notation = str(node.get("skos:notation") or "")

    def_obj = node.get("skos:definition") or ""
    definition = _lang_value(def_obj)

    examples_obj = node.get("skos:example") or []
    examples: list[str] = []
    if isinstance(examples_obj, list):
        for item in examples_obj:
            if isinstance(item, dict) and "@value" in item:
                examples.append(str(item.get("@value", "")))
            elif item:
                examples.append(str(item))

    parts = [label, notation, definition]
    parts.extend(examples)
    return "\n".join(part for part in parts if part)


def cards_text_builder(node: dict[str, Any]) -> str:
    """Build vector text for card concepts."""
    name = str(node.get("name") or "")
    oracle_text = str(node.get("oracle_text") or "")
    type_line = str(node.get("type_line") or "")
    keywords = node.get("keywords") or []
    if isinstance(keywords, list):
        keyword_text = ", ".join(str(value) for value in keywords)
    else:
        keyword_text = str(keywords)
    return "\n".join(part for part in [name, type_line, oracle_text, keyword_text] if part)


def rules_payload_builder(node: dict[str, Any]) -> dict[str, Any]:
    """Build a compact, query-friendly payload for rules SKOS nodes."""
    uri = str(node.get("@id") or "")
    scheme_uri = _id_value(node.get("skos:inScheme"))
    rules_token = scheme_uri.rstrip("/").split("/")[-1] if scheme_uri else ""

    label = _lang_value(node.get("skos:prefLabel") or node.get("rdfs:label") or "")
    definition = _lang_value(node.get("skos:definition") or "")
    notation = str(node.get("skos:notation") or "")
    broader = _id_value(node.get("skos:broader"))
    references = _id_list(node.get("dcterms:references"))

    if uri.endswith("/concept/rules"):
        kind = "rules_root"
    elif uri.endswith("/concept/glossary"):
        kind = "glossary_root"
    elif "/chapter/" in uri:
        kind = "chapter"
    elif "/section/" in uri:
        kind = "section"
    elif "/rule/" in uri:
        kind = "rule"
    elif "/glossary/" in uri:
        kind = "glossary_term"
    else:
        kind = "concept"

    return {
        "uri": uri,
        "kind": kind,
        "label": label,
        "notation": notation,
        "broader": broader,
        "references": references,
        "scheme": scheme_uri,
        "rules_token": rules_token,
        # Keep definition separately so agents can fetch it without pulling full jsonld.
        "definition": definition,
        "jsonld": node,
    }


def cards_payload_builder(node: dict[str, Any]) -> dict[str, Any]:
    """Build a compact, query-friendly payload for Scryfall card JSON-LD nodes."""
    uri = str(node.get("@id") or node.get("uri") or "")
    return {
        "uri": uri,
        "kind": "card",
        # Common, indexable fields
        "id": node.get("id"),
        "oracle_id": node.get("oracle_id"),
        "name": node.get("name"),
        "set": node.get("set"),
        "collector_number": node.get("collector_number"),
        "lang": node.get("lang"),
        "released_at": node.get("released_at"),
        "layout": node.get("layout"),
        "type_line": node.get("type_line"),
        "mana_cost": node.get("mana_cost"),
        "rarity": node.get("rarity"),
        "cmc": node.get("cmc"),
        "colors": node.get("colors"),
        "color_identity": node.get("color_identity"),
        "keywords": node.get("keywords"),
        "oracle_text": node.get("oracle_text"),
        # Full, 1:1 Scryfall JSON-LD
        "jsonld": node,
    }
