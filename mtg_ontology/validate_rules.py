"""Validate the generated rules SKOS JSON-LD against basic SKOS best-practice checks."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any


def _as_type_list(value: Any) -> list[str]:
    if value is None:
        return []
    if isinstance(value, str):
        return [value]
    if isinstance(value, list):
        return [str(item) for item in value]
    return [str(value)]


def _lang_value(obj: Any) -> str | None:
    if isinstance(obj, dict) and "@value" in obj:
        return str(obj.get("@value"))
    if isinstance(obj, str):
        return obj
    return None


def validate_rules_jsonld(path: Path, *, strict: bool = False) -> dict[str, Any]:
    payload = json.loads(path.read_text(encoding="utf-8"))
    graph = payload.get("@graph", [])
    if not isinstance(graph, list):
        raise ValueError(f"JSON-LD '@graph' is not a list in {path}")

    nodes_by_id: dict[str, dict[str, Any]] = {}
    for node in graph:
        if isinstance(node, dict) and node.get("@id"):
            nodes_by_id[str(node["@id"])] = node

    errors: list[str] = []
    warnings: list[str] = []

    schemes = [
        node
        for node in graph
        if isinstance(node, dict) and "skos:ConceptScheme" in _as_type_list(node.get("@type"))
    ]
    if len(schemes) != 1:
        errors.append(f"Expected exactly 1 skos:ConceptScheme, found {len(schemes)}")
        scheme_id = None
    else:
        scheme_id = str(schemes[0].get("@id"))

    for node in graph:
        if not isinstance(node, dict):
            continue
        if "skos:hasTopConcept" in node and "skos:ConceptScheme" not in _as_type_list(node.get("@type")):
            errors.append(
                f"skos:hasTopConcept appears on non-ConceptScheme node: {node.get('@id')}"
            )

    concepts = [
        node
        for node in graph
        if isinstance(node, dict) and "skos:Concept" in _as_type_list(node.get("@type"))
    ]

    for concept in concepts:
        cid = str(concept.get("@id"))

        in_scheme = concept.get("skos:inScheme")
        if not isinstance(in_scheme, dict) or "@id" not in in_scheme:
            errors.append(f"Missing/invalid skos:inScheme on concept: {cid}")
        elif scheme_id and str(in_scheme.get("@id")) != scheme_id:
            errors.append(f"Concept inScheme does not match scheme id: {cid}")

        pref = concept.get("skos:prefLabel")
        if not (isinstance(pref, dict) and "@value" in pref and "@language" in pref):
            errors.append(f"Missing/invalid skos:prefLabel language string on concept: {cid}")

        definition = concept.get("skos:definition")
        if definition is not None and not (
            isinstance(definition, dict) and "@value" in definition and "@language" in definition
        ):
            errors.append(f"Invalid skos:definition (expected language string) on concept: {cid}")

        notation = concept.get("skos:notation")
        if "/rule/" in cid or "/section/" in cid or "/chapter/" in cid:
            if not notation:
                errors.append(f"Missing skos:notation on numbered concept: {cid}")

        if "/rule/" in cid and notation:
            pref_value = _lang_value(pref)
            if pref_value and pref_value != str(notation):
                errors.append(
                    f"Rule prefLabel should match skos:notation (got '{pref_value}' vs '{notation}'): {cid}"
                )

        refs = concept.get("dcterms:references")
        if refs is not None:
            if not isinstance(refs, list):
                errors.append(f"dcterms:references is not a list on concept: {cid}")
            else:
                for ref in refs:
                    if not isinstance(ref, dict) or "@id" not in ref:
                        errors.append(f"Invalid dcterms:references entry on concept: {cid}")
                        continue
                    rid = str(ref["@id"])
                    if rid not in nodes_by_id:
                        msg = f"Reference target not found in graph: {cid} -> {rid}"
                        if strict:
                            errors.append(msg)
                        else:
                            warnings.append(msg)

    return {
        "path": str(path),
        "graph_nodes": len(graph),
        "concepts": len(concepts),
        "schemes": len(schemes),
        "errors": errors,
        "error_count": len(errors),
        "warnings": warnings,
        "warning_count": len(warnings),
    }
