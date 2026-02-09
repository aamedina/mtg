"""General helper functions."""

from __future__ import annotations

import re
import unicodedata
import uuid
from collections.abc import Iterable
from typing import Any


def normalize_text(value: str) -> str:
    """Normalize CRLF input and trim excess blank lines."""
    value = value.replace("\r\n", "\n").replace("\r", "\n")
    lines = [line.rstrip() for line in value.split("\n")]
    return "\n".join(lines).strip() + "\n"


def slugify(value: str) -> str:
    """Create a stable ASCII slug for URIs."""
    normalized = unicodedata.normalize("NFKD", value)
    ascii_value = normalized.encode("ascii", "ignore").decode("ascii")
    ascii_value = ascii_value.lower()
    ascii_value = re.sub(r"[^a-z0-9]+", "-", ascii_value)
    return ascii_value.strip("-") or "item"


def compact_ws(value: str) -> str:
    """Collapse all whitespace to single spaces."""
    return re.sub(r"\s+", " ", value).strip()


def iter_chunks(items: Iterable[Any], chunk_size: int) -> Iterable[list[Any]]:
    """Yield fixed-size chunks from an iterable."""
    batch: list[Any] = []
    for item in items:
        batch.append(item)
        if len(batch) >= chunk_size:
            yield batch
            batch = []
    if batch:
        yield batch


def stable_uuid(value: str) -> str:
    """Generate deterministic UUIDv5 from an identifier string."""
    return str(uuid.uuid5(uuid.NAMESPACE_URL, value))
