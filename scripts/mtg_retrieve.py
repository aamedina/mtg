#!/usr/bin/env python3
"""Retrieve MTG rules/cards context from Qdrant for agent-style answering.

This script is intentionally simple and outputs JSON, so it can be used by:
- humans (copy/paste into a prompt)
- agent runtimes (parse JSON and decide next steps)

It does *not* generate answers. The intended workflow is:
1) retrieve context (this script)
2) answer with an LLM (Codex / ChatGPT / your own agent), citing rule numbers and card oracle text.
"""

from __future__ import annotations

import argparse
import json
import os
import re
import sys
from dataclasses import dataclass
from pathlib import Path
from urllib.parse import urlparse

from qdrant_client import QdrantClient
from qdrant_client.http import models

# Ensure `import mtg_ontology` works when this script is executed as a file.
REPO_ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(REPO_ROOT))

from mtg_ontology.embeddings import OpenAIEmbedder, OpenAIEmbeddingConfig, resolve_openai_api_key


@dataclass(frozen=True)
class ParsedCardFilters:
    """Lightweight filters inferred from a natural-language card query."""

    cmc: float | None = None
    color_identities: list[str] | None = None  # e.g. ["R"] or ["U","B"]
    type_tokens: list[str] | None = None  # e.g. ["instant", "creature"]
    oracle_text_must_tokens: list[str] | None = None  # e.g. ["damage", "3"]


_COLOR_WORDS = {
    "white": "W",
    "blue": "U",
    "black": "B",
    "red": "R",
    "green": "G",
    "colorless": "C",
}

_MANA_WORDS = {
    "one": 1.0,
    "two": 2.0,
    "three": 3.0,
    "four": 4.0,
    "five": 5.0,
    "six": 6.0,
    "seven": 7.0,
    "eight": 8.0,
    "nine": 9.0,
    "ten": 10.0,
}

_TYPE_TOKENS = [
    "instant",
    "sorcery",
    "creature",
    "artifact",
    "enchantment",
    "planeswalker",
    "land",
    "legendary",
]


def _infer_default_collection(*, prefix: str, explicit: str | None) -> str | None:
    """Pick the latest tokenized collection name when caller didn't pass one.

    Example:
    - mtg_rules_20260116
    - mtg_oracle_cards_20260208220524
    """
    if explicit:
        return explicit
    env_key = "MTG_RULES_COLLECTION" if prefix == "mtg_rules_" else "MTG_CARDS_COLLECTION"
    if os.getenv(env_key):
        return os.getenv(env_key)
    return None


def _parse_rules_notation(text: str) -> str | None:
    # Accept common forms: "614.1a", "CR 614.1a", "rule 614.1a"
    m = re.search(r"\b(\d{3}\.\d+[a-z]?)\b", text)
    return m.group(1) if m else None


def _infer_card_filters(question: str) -> ParsedCardFilters:
    q = question.lower()

    # Mana value hints: "one mana", "2 mana", "mana value 3"
    cmc = None
    m = re.search(r"\b(mana value|mv)\s*(\d+)\b", q)
    if m:
        cmc = float(m.group(2))
    else:
        m = re.search(r"\b(\d+)\s+mana\b", q)
        if m:
            cmc = float(m.group(1))
        else:
            m = re.search(r"\b(" + "|".join(_MANA_WORDS.keys()) + r")\s+mana\b", q)
            if m:
                cmc = _MANA_WORDS[m.group(1)]

    # Color identity hints.
    colors: list[str] = []
    for word, sym in _COLOR_WORDS.items():
        if re.search(rf"\b{re.escape(word)}\b", q):
            colors.append(sym)

    # Type hints.
    types = [tok for tok in _TYPE_TOKENS if re.search(rf"\b{re.escape(tok)}\b", q)]

    # Oracle text hints: add a few high-signal lexical tokens as hard constraints.
    # This helps prevent obviously-wrong matches when the structural filters are broad.
    oracle_tokens: list[str] = []

    m = re.search(r"\bdeal(?:s)?\s+(\d+)\s+damage\b", q)
    if m:
        oracle_tokens.extend(["damage", m.group(1)])
    elif "damage" in q:
        oracle_tokens.append("damage")

    if "counter" in q and ("spell" in q or "spells" in q):
        oracle_tokens.extend(["counter", "spell"])

    return ParsedCardFilters(
        cmc=cmc,
        color_identities=colors or None,
        type_tokens=types or None,
        oracle_text_must_tokens=oracle_tokens or None,
    )


def _filters_to_qdrant_filter(filters: ParsedCardFilters) -> models.Filter | None:
    must: list[models.FieldCondition] = []

    if filters.cmc is not None:
        must.append(models.FieldCondition(key="cmc", range=models.Range(gte=filters.cmc, lte=filters.cmc)))

    if filters.color_identities:
        # Require all specified colors in color_identity.
        for sym in filters.color_identities:
            must.append(models.FieldCondition(key="color_identity", match=models.MatchAny(any=[sym])))

    if filters.type_tokens:
        # Token-based match on the type line (requires a text index for speed; still works without it).
        for tok in filters.type_tokens:
            must.append(models.FieldCondition(key="type_line", match=models.MatchText(text=tok)))

    if filters.oracle_text_must_tokens:
        # Token-based match on oracle text. Provide individual tokens as separate conditions so
        # all tokens are required (MatchText itself is not guaranteed to enforce all terms).
        for tok in filters.oracle_text_must_tokens:
            must.append(models.FieldCondition(key="oracle_text", match=models.MatchText(text=tok)))

    return models.Filter(must=must) if must else None


def _embedder(model: str) -> OpenAIEmbedder:
    return OpenAIEmbedder(OpenAIEmbeddingConfig(api_key=resolve_openai_api_key(), model=model))


def _payload_fields_for_rules(full: bool) -> list[str]:
    base = ["uri", "kind", "notation", "label", "definition", "references", "scheme", "rules_token"]
    if full:
        base.append("jsonld")
    return base


def _payload_fields_for_cards(full: bool) -> list[str]:
    base = [
        "uri",
        "kind",
        "id",
        "oracle_id",
        "name",
        "set",
        "collector_number",
        "lang",
        "mana_cost",
        "cmc",
        "type_line",
        "oracle_text",
        "colors",
        "color_identity",
        "keywords",
        "rarity",
        "layout",
    ]
    if full:
        base.append("jsonld")
    return base


def _as_hit(point: models.ScoredPoint) -> dict:
    payload = point.payload or {}
    return {"score": point.score, "id": str(point.id), "payload": payload}


def main(argv: list[str] | None = None) -> int:
    ap = argparse.ArgumentParser(description="Retrieve rules/cards context from Qdrant")
    ap.add_argument("--qdrant-url", default=os.getenv("QDRANT_URL", "http://localhost:6333"))
    ap.add_argument("--qdrant-api-key", default=None)
    ap.add_argument("--embedding-model", default=os.getenv("OPENAI_EMBEDDING_MODEL", "text-embedding-3-small"))
    ap.add_argument("--rules-collection", default=None)
    ap.add_argument("--cards-collection", default=None)
    ap.add_argument("--only", choices=["auto", "rules", "cards"], default="auto")
    ap.add_argument("--limit", type=int, default=8)
    ap.add_argument("--full-jsonld", action="store_true", help="Include full JSON-LD nodes in hits (large).")
    ap.add_argument("--no-heuristics", action="store_true", help="Disable inferred card filters from the query text.")
    ap.add_argument("question", nargs="+", help="The user question to retrieve context for")
    args = ap.parse_args(argv)

    question = " ".join(args.question).strip()
    if not question:
        raise SystemExit(2)

    api_key = args.qdrant_api_key or os.getenv("QDRANT_API_KEY") or None
    if not args.qdrant_api_key and api_key:
        try:
            parsed = urlparse(args.qdrant_url)
            if parsed.scheme == "http" and (parsed.hostname or "") in {"localhost", "127.0.0.1", "qdrant"}:
                api_key = None
        except Exception:  # noqa: BLE001
            pass

    client = QdrantClient(url=args.qdrant_url, api_key=api_key, timeout=60)
    embedder = _embedder(args.embedding_model)

    rules_collection = _infer_default_collection(prefix="mtg_rules_", explicit=args.rules_collection)
    cards_collection = _infer_default_collection(prefix="mtg_oracle_cards_", explicit=args.cards_collection)

    out: dict = {
        "question": question,
        "qdrant_url": args.qdrant_url,
        "embedding_model": args.embedding_model,
        "rules_collection": rules_collection,
        "cards_collection": cards_collection,
        "rules": [],
        "cards": [],
    }

    want_rules = args.only in ("auto", "rules")
    want_cards = args.only in ("auto", "cards")

    # Rules retrieval
    if want_rules and rules_collection:
        notation = _parse_rules_notation(question)
        if notation:
            records, _ = client.scroll(
                collection_name=rules_collection,
                scroll_filter=models.Filter(
                    must=[models.FieldCondition(key="notation", match=models.MatchValue(value=notation))]
                ),
                limit=3,
                with_payload=models.PayloadSelectorInclude(include=_payload_fields_for_rules(args.full_jsonld)),
                with_vectors=False,
            )
            out["rules"] = [{"score": None, "id": str(r.id), "payload": r.payload or {}} for r in records]
        else:
            vec = embedder.embed([question])[0]
            # Default to rules + glossary terms. Agents can narrow after inspecting results.
            flt = models.Filter(
                should=[
                    models.FieldCondition(key="kind", match=models.MatchValue(value="rule")),
                    models.FieldCondition(key="kind", match=models.MatchValue(value="glossary_term")),
                ]
            )
            resp = client.query_points(
                collection_name=rules_collection,
                query=vec,
                query_filter=flt,
                limit=args.limit,
                with_payload=models.PayloadSelectorInclude(include=_payload_fields_for_rules(args.full_jsonld)),
                with_vectors=False,
            )
            out["rules"] = [_as_hit(p) for p in resp.points]

    # Card retrieval
    if want_cards and cards_collection:
        vec = embedder.embed([question])[0]
        flt = None
        inferred = None
        if not args.no_heuristics:
            inferred = _infer_card_filters(question)
            flt = _filters_to_qdrant_filter(inferred)

        resp = client.query_points(
            collection_name=cards_collection,
            query=vec,
            query_filter=flt,
            limit=args.limit,
            with_payload=models.PayloadSelectorInclude(include=_payload_fields_for_cards(args.full_jsonld)),
            with_vectors=False,
        )
        out["cards"] = [_as_hit(p) for p in resp.points]
        if inferred:
            out["inferred_card_filters"] = {
                "cmc": inferred.cmc,
                "color_identity": inferred.color_identities,
                "type_tokens": inferred.type_tokens,
                "oracle_text_must_tokens": inferred.oracle_text_must_tokens,
            }

    print(json.dumps(out, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
