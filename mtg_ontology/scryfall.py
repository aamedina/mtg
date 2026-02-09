"""Scryfall API and local bulk-data helpers."""

from __future__ import annotations

import gzip
import json
from dataclasses import dataclass
from pathlib import Path
from typing import Any

import requests

BULK_DATA_INDEX_URL = "https://api.scryfall.com/bulk-data"
SCRYFALL_SEARCH_URL = "https://api.scryfall.com/cards/search"


@dataclass(frozen=True)
class BulkDataReference:
    """Metadata for a Scryfall bulk dataset."""

    id: str
    name: str
    type: str
    download_uri: str
    updated_at: str


def fetch_bulk_index() -> list[dict[str, Any]]:
    """Fetch all available Scryfall bulk dataset descriptors."""
    response = requests.get(BULK_DATA_INDEX_URL, timeout=30)
    response.raise_for_status()
    payload = response.json()
    return payload.get("data", [])


def get_bulk_reference(*, bulk_type: str = "oracle_cards") -> BulkDataReference:
    """Resolve a bulk dataset by type from Scryfall bulk index."""
    for entry in fetch_bulk_index():
        if entry.get("type") == bulk_type:
            return BulkDataReference(
                id=str(entry.get("id")),
                name=str(entry.get("name")),
                type=str(entry.get("type")),
                download_uri=str(entry.get("download_uri")),
                updated_at=str(entry.get("updated_at")),
            )
    raise RuntimeError(f"No Scryfall bulk dataset found for type='{bulk_type}'")


def download_json(url: str, output_path: Path) -> Path:
    """Download JSON from URL to path with streaming writes."""
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with requests.get(url, timeout=120, stream=True) as response:
        response.raise_for_status()
        if output_path.suffix == ".gz":
            with gzip.open(output_path, "wb") as fh:
                for chunk in response.iter_content(chunk_size=1024 * 1024):
                    if chunk:
                        fh.write(chunk)
        else:
            with output_path.open("wb") as fh:
                for chunk in response.iter_content(chunk_size=1024 * 1024):
                    if chunk:
                        fh.write(chunk)
    return output_path


def load_cards(path: Path) -> list[dict[str, Any]]:
    """Load card list JSON from a local file."""
    if path.suffix == ".gz":
        with gzip.open(path, "rt", encoding="utf-8") as fh:
            payload = json.loads(fh.read())
    else:
        payload = json.loads(path.read_text(encoding="utf-8"))
    if isinstance(payload, list):
        return payload
    if isinstance(payload, dict) and "data" in payload and isinstance(payload["data"], list):
        return payload["data"]
    raise ValueError(f"Unsupported JSON structure for card list: {path}")


def _deep_get(item: dict[str, Any], dotted_path: str) -> Any:
    current: Any = item
    for token in dotted_path.split("."):
        if not isinstance(current, dict):
            return None
        current = current.get(token)
    return current


def filter_cards(
    cards: list[dict[str, Any]],
    *,
    format_name: str | None = None,
    set_code: str | None = None,
    where: list[str] | None = None,
) -> list[dict[str, Any]]:
    """Filter cards by legality, set code, and generic dotted key filters."""
    conditions = where or []
    selected: list[dict[str, Any]] = []

    for card in cards:
        if format_name:
            legalities = card.get("legalities") or {}
            if legalities.get(format_name.lower()) != "legal":
                continue

        if set_code and str(card.get("set", "")).lower() != set_code.lower():
            continue

        failed = False
        for condition in conditions:
            if "=" not in condition:
                raise ValueError(f"Invalid --where condition: '{condition}' (expected key=value)")
            key, expected = condition.split("=", 1)
            actual = _deep_get(card, key)
            if str(actual) != expected:
                failed = True
                break
        if failed:
            continue

        selected.append(card)

    return selected


def search_cards(query: str, max_cards: int = 175) -> list[dict[str, Any]]:
    """Search cards via Scryfall API query syntax."""
    cards: list[dict[str, Any]] = []
    next_url = SCRYFALL_SEARCH_URL
    params: dict[str, Any] | None = {"q": query, "order": "name", "unique": "cards"}

    while next_url and len(cards) < max_cards:
        response = requests.get(next_url, params=params, timeout=30)
        response.raise_for_status()
        payload = response.json()

        cards.extend(payload.get("data", []))

        if payload.get("has_more"):
            next_url = payload.get("next_page")
            params = None
        else:
            next_url = None

    return cards[:max_cards]
