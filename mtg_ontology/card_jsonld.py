"""Scryfall JSON -> JSON-LD (1:1 field mapping).

Design goals:

- Preserve the Scryfall JSON structure 1:1 (no renaming of keys).
- Add only JSON-LD framing (`@context`, `@graph`) and a stable `@id` per card.
- Keep modeling decisions in the Scryfall vocabulary/context (see docs).
"""

from __future__ import annotations

import gzip
import json
from pathlib import Path
from typing import Any

from .constants import SCRYFALL_API_BASE_IRI, SCRYFALL_JSONLD_CONTEXT


def card_to_jsonld(card: dict[str, Any]) -> dict[str, Any]:
    """Convert a single Scryfall card object to a JSON-LD node.

    The node is the original JSON object plus `@id`.
    """
    node = dict(card)

    card_id = card.get("id")
    node_id = card.get("uri")
    if not node_id and card_id:
        node_id = f"{SCRYFALL_API_BASE_IRI.rstrip('/')}/cards/{card_id}"

    if node_id:
        node["@id"] = str(node_id)

    return node


def cards_to_jsonld(cards: list[dict[str, Any]]) -> dict[str, Any]:
    """Wrap a list of Scryfall card objects into a JSON-LD document."""
    graph = [card_to_jsonld(card) for card in cards]
    return {
        "@context": SCRYFALL_JSONLD_CONTEXT,
        "@graph": graph,
    }


def load_json(path: Path) -> Any:
    """Load UTF-8 JSON content from disk."""
    if path.suffix == ".gz":
        with gzip.open(path, "rt", encoding="utf-8") as fh:
            return json.loads(fh.read())
    return json.loads(path.read_text(encoding="utf-8"))


def write_json(path: Path, data: Any) -> Path:
    """Write JSON data to disk."""
    path.parent.mkdir(parents=True, exist_ok=True)
    encoded = json.dumps(data, indent=2, ensure_ascii=False)
    if path.suffix == ".gz":
        with gzip.open(path, "wt", encoding="utf-8") as fh:
            fh.write(encoded)
    else:
        path.write_text(encoded, encoding="utf-8")
    return path
