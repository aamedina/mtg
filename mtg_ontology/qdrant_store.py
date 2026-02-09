"""Qdrant helper utilities."""

from __future__ import annotations

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


def create_client(config: QdrantConfig) -> QdrantClient:
    """Instantiate a Qdrant client from config."""
    return QdrantClient(url=config.url, api_key=config.api_key, timeout=config.timeout)


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
    batch_size: int = 64,
) -> int:
    """Upsert document dicts into Qdrant using provided embedder."""
    total = 0

    for doc_batch in iter_chunks(docs, batch_size):
        texts = [text_builder(doc) for doc in doc_batch]
        vectors = embedder.embed(texts)
        if len(vectors) != len(doc_batch):
            raise RuntimeError(
                f"Embedder output mismatch: {len(vectors)} vectors for {len(doc_batch)} docs"
            )

        points: list[models.PointStruct] = []
        for doc, text, vector in zip(doc_batch, texts, vectors):
            uri = doc.get("@id") or doc.get("id") or ""
            payload = payload_builder(doc)
            if uri:
                payload.setdefault("uri", uri)

            points.append(
                models.PointStruct(
                    id=stable_uuid(uri or text),
                    vector=vector,
                    payload=payload,
                )
            )

        client.upsert(collection_name=collection_name, points=points, wait=True)
        total += len(points)

    return total
