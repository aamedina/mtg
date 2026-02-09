"""Create and download Qdrant collection snapshots."""

from __future__ import annotations

from pathlib import Path

import requests


def create_collection_snapshot(
    *,
    qdrant_url: str,
    collection: str,
    api_key: str | None = None,
    timeout: float = 120.0,
) -> str:
    """Trigger snapshot creation and return snapshot name."""
    headers = {"Content-Type": "application/json"}
    if api_key:
        headers["api-key"] = api_key

    response = requests.post(
        f"{qdrant_url.rstrip('/')}/collections/{collection}/snapshots",
        headers=headers,
        timeout=timeout,
    )
    response.raise_for_status()
    payload = response.json()
    result = payload.get("result", {})
    name = result.get("name")
    if not name:
        raise RuntimeError(f"Snapshot create response missing name: {payload}")
    return str(name)


def download_collection_snapshot(
    *,
    qdrant_url: str,
    collection: str,
    snapshot_name: str,
    output_dir: Path,
    api_key: str | None = None,
    timeout: float = 120.0,
) -> Path:
    """Download a created snapshot to disk."""
    headers: dict[str, str] = {}
    if api_key:
        headers["api-key"] = api_key

    output_dir.mkdir(parents=True, exist_ok=True)
    output_path = output_dir / f"{collection}--{snapshot_name}"

    with requests.get(
        f"{qdrant_url.rstrip('/')}/collections/{collection}/snapshots/{snapshot_name}",
        headers=headers,
        timeout=timeout,
        stream=True,
    ) as response:
        response.raise_for_status()
        with output_path.open("wb") as fh:
            for chunk in response.iter_content(chunk_size=1024 * 1024):
                if chunk:
                    fh.write(chunk)

    return output_path


def recover_collection_from_snapshot_file(
    *,
    qdrant_url: str,
    collection: str,
    snapshot_path: Path,
    api_key: str | None = None,
    timeout: float = 300.0,
    wait: bool = True,
    priority: str | None = None,
    checksum: str | None = None,
) -> dict:
    """Recover (restore) a collection from a local snapshot file.

    This calls Qdrant's `POST /collections/{collection}/snapshots/upload` endpoint, which uploads
    the snapshot and triggers recovery on the server.
    """
    headers: dict[str, str] = {}
    if api_key:
        headers["api-key"] = api_key

    params: dict[str, str] = {"wait": "true" if wait else "false"}
    if priority:
        params["priority"] = priority
    if checksum:
        params["checksum"] = checksum

    with snapshot_path.open("rb") as fh:
        files = {"snapshot": fh}
        response = requests.post(
            f"{qdrant_url.rstrip('/')}/collections/{collection}/snapshots/upload",
            headers=headers,
            params=params,
            files=files,
            timeout=timeout,
        )
        response.raise_for_status()
        payload = response.json()

    if payload.get("status") != "ok":
        raise RuntimeError(f"Snapshot recovery failed: {payload}")

    return payload
