#!/usr/bin/env python3
"""Download Qdrant snapshot assets from a GitHub release tag.

This is intended for users who want a fast, reproducible local setup:

1) download prebuilt Qdrant snapshots from a release (no embedding cost)
2) recover them into a dockerized Qdrant

The script prints a JSON object describing what it downloaded.
"""

from __future__ import annotations

import argparse
import json
import re
import sys
import urllib.request
from dataclasses import dataclass
from datetime import datetime, timezone
from pathlib import Path
from typing import Any


@dataclass(frozen=True)
class Asset:
    name: str
    url: str  # browser_download_url
    updated_at: datetime
    size: int


def _parse_dt(value: str) -> datetime:
    # GitHub returns ISO8601 like "2026-02-09T17:50:47Z"
    dt = datetime.strptime(value, "%Y-%m-%dT%H:%M:%SZ")
    return dt.replace(tzinfo=timezone.utc)


def _http_get_json(url: str) -> Any:
    req = urllib.request.Request(url, headers={"Accept": "application/vnd.github+json"})
    with urllib.request.urlopen(req, timeout=30) as resp:  # noqa: S310
        return json.loads(resp.read().decode("utf-8"))


def _download(url: str, output_path: Path) -> None:
    output_path.parent.mkdir(parents=True, exist_ok=True)
    req = urllib.request.Request(url, headers={"Accept": "application/octet-stream"})
    with urllib.request.urlopen(req, timeout=600) as resp, output_path.open("wb") as f:  # noqa: S310
        while True:
            chunk = resp.read(1024 * 1024)
            if not chunk:
                break
            f.write(chunk)


def _select_latest(assets: list[Asset]) -> Asset | None:
    if not assets:
        return None
    return sorted(assets, key=lambda a: (a.updated_at, a.size), reverse=True)[0]


def main(argv: list[str] | None = None) -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--repo", default="aamedina/mtg", help="GitHub repo in owner/name form")
    ap.add_argument("--tag", required=True, help="Release tag name, e.g. rules-20260116")
    ap.add_argument("--output-dir", default="resources/qdrant-snapshots")
    args = ap.parse_args(argv)

    release = _http_get_json(f"https://api.github.com/repos/{args.repo}/releases/tags/{args.tag}")
    if not isinstance(release, dict):
        raise SystemExit("Unexpected GitHub API response for release.")

    raw_assets = release.get("assets") or []
    if not isinstance(raw_assets, list):
        raw_assets = []

    rules_assets: list[Asset] = []
    oracle_assets: list[Asset] = []

    rules_re = re.compile(r"^mtg_rules_\d{8}--.*\.snapshot$")
    oracle_re = re.compile(r"^mtg_oracle_cards_\d{14}--.*\.snapshot$")

    for item in raw_assets:
        if not isinstance(item, dict):
            continue
        name = str(item.get("name") or "")
        url = str(item.get("browser_download_url") or "")
        updated = str(item.get("updated_at") or "")
        size = int(item.get("size") or 0)
        if not name or not url or not updated:
            continue
        asset = Asset(name=name, url=url, updated_at=_parse_dt(updated), size=size)
        if rules_re.match(name):
            rules_assets.append(asset)
        elif oracle_re.match(name):
            oracle_assets.append(asset)

    rules = _select_latest(rules_assets)
    oracle = _select_latest(oracle_assets)

    output_dir = Path(args.output_dir)
    result: dict[str, Any] = {"repo": args.repo, "tag": args.tag, "downloads": []}

    for chosen in [rules, oracle]:
        if not chosen:
            continue
        out_path = output_dir / chosen.name
        if not out_path.exists() or out_path.stat().st_size != chosen.size:
            _download(chosen.url, out_path)
        kind = "rules" if chosen is rules else "oracle_cards"
        collection = chosen.name.split("--", 1)[0]
        result["downloads"].append(
            {
                "kind": kind,
                "collection": collection,
                "asset": chosen.name,
                "path": str(out_path),
                "updated_at": chosen.updated_at.isoformat(),
                "size": chosen.size,
            }
        )

    if not result["downloads"]:
        print(json.dumps({"error": "no snapshot assets found for tag", "repo": args.repo, "tag": args.tag}, indent=2))
        return 2

    print(json.dumps(result, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

