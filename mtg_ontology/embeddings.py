"""OpenAI embedding client used for Qdrant upserts."""

from __future__ import annotations

import os
import time
from dataclasses import dataclass
from typing import Any

import requests

_DEFAULT_BASE_URL = "https://api.openai.com/v1"
_MODEL_VECTOR_SIZES = {
    "text-embedding-3-small": 1536,
    "text-embedding-3-large": 3072,
}


@dataclass(frozen=True)
class OpenAIEmbeddingConfig:
    """Settings for OpenAI embeddings API calls."""

    api_key: str
    model: str = "text-embedding-3-small"
    base_url: str = _DEFAULT_BASE_URL
    dimensions: int | None = None
    timeout: float = 60.0
    max_retries: int = 4


class OpenAIEmbedder:
    """Minimal embeddings API client using HTTP requests."""

    def __init__(self, config: OpenAIEmbeddingConfig):
        self.config = config
        self.vector_size = config.dimensions or _MODEL_VECTOR_SIZES.get(config.model)
        if self.vector_size is None:
            raise ValueError(
                "Could not infer vector size for model "
                f"'{config.model}'. Provide --embedding-dimensions explicitly."
            )

    def embed(self, texts: list[str]) -> list[list[float]]:
        """Embed a batch of texts using the configured OpenAI model."""
        if not texts:
            return []

        payload: dict[str, Any] = {
            "input": texts,
            "model": self.config.model,
        }
        if self.config.dimensions is not None:
            payload["dimensions"] = self.config.dimensions

        headers = {
            "Authorization": f"Bearer {self.config.api_key}",
            "Content-Type": "application/json",
        }

        last_error: Exception | None = None
        for attempt in range(self.config.max_retries + 1):
            try:
                response = requests.post(
                    f"{self.config.base_url.rstrip('/')}/embeddings",
                    headers=headers,
                    json=payload,
                    timeout=self.config.timeout,
                )
                if response.status_code in (429, 500, 502, 503, 504):
                    raise RuntimeError(
                        f"Transient embeddings API error ({response.status_code}): {response.text[:300]}"
                    )
                response.raise_for_status()
                body = response.json()
                data = sorted(body.get("data", []), key=lambda item: item.get("index", 0))
                vectors = [item.get("embedding", []) for item in data]
                if len(vectors) != len(texts):
                    raise RuntimeError(
                        f"Embeddings response count mismatch: expected {len(texts)}, got {len(vectors)}"
                    )
                if vectors and len(vectors[0]) != self.vector_size:
                    raise RuntimeError(
                        "Embeddings dimension mismatch: expected "
                        f"{self.vector_size}, got {len(vectors[0])}"
                    )
                return vectors
            except Exception as exc:  # noqa: BLE001
                last_error = exc
                if attempt >= self.config.max_retries:
                    break
                time.sleep(min(2**attempt, 10))

        raise RuntimeError(f"Failed to fetch OpenAI embeddings: {last_error}") from last_error


def resolve_openai_api_key(value: str | None = None) -> str:
    """Resolve API key from explicit value or OPENAI_API_KEY env var."""
    api_key = value or os.getenv("OPENAI_API_KEY")
    if not api_key:
        raise RuntimeError("OpenAI API key is required. Set OPENAI_API_KEY or --openai-api-key.")
    return api_key
