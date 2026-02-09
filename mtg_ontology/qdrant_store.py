"""Qdrant helper utilities."""

from __future__ import annotations

import hashlib
from collections.abc import Callable, Iterable
from dataclasses import dataclass
from typing import Any, Protocol

from qdrant_client import QdrantClient
from qdrant_client.http import models

from .utils import iter_chunks, stable_uuid


class Embedder(Protocol):
    """Embedding protocol expected by upsert_documents."""

    vector_size: int

    def embed(self, texts: list[str]) -> list[list[float]]:
        """Return an embedding for each text in order."""


@dataclass(frozen=True)
class QdrantConfig:
    """Runtime settings for Qdrant connectivity."""

    url: str = "http://localhost:6333"
    api_key: str | None = None
    timeout: float = 30.0
    distance: models.Distance = models.Distance.COSINE


@dataclass(frozen=True)
class UpsertStats:
    """Basic counters returned by upsert_documents."""

    upserted: int
    skipped: int


def create_client(config: QdrantConfig) -> QdrantClient:
    """Instantiate a Qdrant client from config."""
    # Avoid passing empty/falsey keys; qdrant-client warns when an API key is used over http.
    if config.api_key:
        return QdrantClient(url=config.url, api_key=config.api_key, timeout=config.timeout)
    return QdrantClient(url=config.url, timeout=config.timeout)


def ensure_collection(
    client: QdrantClient,
    collection_name: str,
    *,
    vector_size: int,
    distance: models.Distance = models.Distance.COSINE,
    recreate: bool = False,
) -> None:
    """Create or recreate a vector collection."""
    exists = client.collection_exists(collection_name)
    if exists and recreate:
        client.delete_collection(collection_name=collection_name)
        exists = False

    if not exists:
        client.create_collection(
            collection_name=collection_name,
            vectors_config=models.VectorParams(size=vector_size, distance=distance),
            on_disk_payload=True,
        )


def ensure_payload_indexes(
    client: QdrantClient,
    collection_name: str,
    *,
    indexes: dict[str, models.PayloadSchemaType],
    wait: bool = True,
) -> None:
    """Ensure payload indexes exist for the given collection.

    Qdrant can filter without indexes, but indexed fields are much faster and are included
    in collection snapshots.
    """
    info = client.get_collection(collection_name)
    existing = info.payload_schema or {}

    for field_name, schema in indexes.items():
        if field_name in existing:
            continue
        client.create_payload_index(
            collection_name=collection_name,
            field_name=field_name,
            field_schema=schema,
            wait=wait,
        )


def upsert_documents(
    client: QdrantClient,
    collection_name: str,
    docs: Iterable[dict[str, Any]],
    *,
    text_builder: Callable[[dict[str, Any]], str],
    payload_builder: Callable[[dict[str, Any]], dict[str, Any]],
    embedder: Embedder,
    embedding_model: str | None = None,
    batch_size: int = 64,
) -> UpsertStats:
    """Upsert document dicts into Qdrant using provided embedder.

    Notes:
    - Each point ID is deterministic (UUIDv5 of `@id` / `id` / embedding text fallback).
    - Embeddings are computed from `text_builder` only; payload does not affect embeddings.
    """
    total_upserted = 0
    total_skipped = 0

    for doc_batch in iter_chunks(docs, batch_size):
        # Prepare deterministic IDs + payloads up front so we can do incremental checks.
        batch_ids: list[str] = []
        batch_texts: list[str] = []
        batch_payloads: list[dict[str, Any]] = []

        for doc in doc_batch:
            text = text_builder(doc)
            uri = doc.get("@id") or doc.get("id") or ""
            point_id = stable_uuid(uri or text)

            payload = payload_builder(doc)
            if uri:
                payload.setdefault("uri", uri)

            # Store a fingerprint of the embedded text so we can skip re-embedding unchanged docs.
            payload["embedding_text_sha256"] = hashlib.sha256(text.encode("utf-8")).hexdigest()
            if embedding_model:
                payload["embedding_model"] = embedding_model
            payload["embedding_dimensions"] = embedder.vector_size

            batch_ids.append(point_id)
            batch_texts.append(text)
            batch_payloads.append(payload)

        # Determine which docs are missing or have changed embedding text.
        # If the existing point doesn't have our fingerprint fields, treat it as needing re-embedding.
        existing = client.retrieve(
            collection_name=collection_name,
            ids=batch_ids,
            with_payload=["embedding_model", "embedding_text_sha256", "embedding_dimensions"],
            with_vectors=False,
        )
        existing_by_id = {str(record.id): (record.payload or {}) for record in existing}

        embed_ids: list[str] = []
        embed_texts: list[str] = []
        embed_payloads: list[dict[str, Any]] = []

        for point_id, text, payload in zip(batch_ids, batch_texts, batch_payloads):
            prev = existing_by_id.get(point_id)
            if not prev:
                embed_ids.append(point_id)
                embed_texts.append(text)
                embed_payloads.append(payload)
                continue
            if prev.get("embedding_model") != payload.get("embedding_model"):
                embed_ids.append(point_id)
                embed_texts.append(text)
                embed_payloads.append(payload)
                continue
            if prev.get("embedding_dimensions") != payload.get("embedding_dimensions"):
                embed_ids.append(point_id)
                embed_texts.append(text)
                embed_payloads.append(payload)
                continue
            if prev.get("embedding_text_sha256") != payload.get("embedding_text_sha256"):
                embed_ids.append(point_id)
                embed_texts.append(text)
                embed_payloads.append(payload)
                continue
            total_skipped += 1

        if not embed_ids:
            continue

        vectors = embedder.embed(embed_texts)
        if len(vectors) != len(embed_ids):
            raise RuntimeError(f"Embedder output mismatch: {len(vectors)} vectors for {len(embed_ids)} docs")

        points: list[models.PointStruct] = []
        for point_id, payload, vector in zip(embed_ids, embed_payloads, vectors):
            points.append(
                models.PointStruct(
                    id=point_id,
                    vector=vector,
                    payload=payload,
                )
            )

        client.upsert(collection_name=collection_name, points=points, wait=True)
        total_upserted += len(points)

    return UpsertStats(upserted=total_upserted, skipped=total_skipped)
