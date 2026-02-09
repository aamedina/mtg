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
from urllib.error import HTTPError, URLError
from datetime import datetime
from dataclasses import dataclass
from datetime import timezone
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


def _http_get_json(url: str, *, token: str | None = None) -> Any:
    headers = {
        "Accept": "application/vnd.github+json",
        # GitHub API requires a UA for some clients, and it helps debug traffic.
        "User-Agent": "mtg-ontology-snapshots",
    }
    if token:
        headers["Authorization"] = f"Bearer {token}"
        headers["X-GitHub-Api-Version"] = "2022-11-28"
    req = urllib.request.Request(url, headers=headers)
    with urllib.request.urlopen(req, timeout=30) as resp:  # noqa: S310
        return json.loads(resp.read().decode("utf-8"))


def _download(url: str, output_path: Path, *, token: str | None = None) -> None:
    output_path.parent.mkdir(parents=True, exist_ok=True)
    headers = {
        "Accept": "application/octet-stream",
        "User-Agent": "mtg-ontology-snapshots",
    }
    if token:
        headers["Authorization"] = f"Bearer {token}"
    req = urllib.request.Request(url, headers=headers)
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
    ap.add_argument(
        "--github-token",
        default=None,
        help="Optional GitHub token to avoid rate limits (or set env GITHUB_TOKEN).",
    )
    args = ap.parse_args(argv)

    import os

    token = args.github_token or os.environ.get("GITHUB_TOKEN") or None

    output_dir = Path(args.output_dir)

    try:
        release = _http_get_json(
            f"https://api.github.com/repos/{args.repo}/releases/tags/{args.tag}",
            token=token,
        )
    except HTTPError as e:
        # Fallback: if the GitHub API is unavailable (common for unauthenticated rate limits),
        # use whatever snapshot files already exist locally.
        if e.code != 403:
            raise
        body = ""
        try:
            body = e.read().decode("utf-8", errors="replace")
        except Exception:
            body = ""
        if "rate limit" not in body.lower():
            raise

        rules_re = re.compile(r"^mtg_rules_\d{8}--.*\.snapshot$")
        oracle_re = re.compile(r"^mtg_oracle_cards_\d{14}--.*\.snapshot$")

        rules_files = [p for p in output_dir.glob("mtg_rules_*.snapshot") if rules_re.match(p.name)]
        oracle_files = [p for p in output_dir.glob("mtg_oracle_cards_*.snapshot") if oracle_re.match(p.name)]

        def choose(paths: list[Path]) -> Path | None:
            if not paths:
                return None
            return sorted(paths, key=lambda p: (p.stat().st_mtime, p.stat().st_size), reverse=True)[0]

        rules_path = choose(rules_files)
        oracle_path = choose(oracle_files)

        result: dict[str, Any] = {
            "repo": args.repo,
            "tag": args.tag,
            "warning": "github_api_unavailable_using_local_snapshots",
            "downloads": [],
        }
        for path, kind in [(rules_path, "rules"), (oracle_path, "oracle_cards")]:
            if not path:
                continue
            collection = path.name.split("--", 1)[0]
            updated_at = datetime.fromtimestamp(path.stat().st_mtime, tz=timezone.utc).isoformat()
            result["downloads"].append(
                {
                    "kind": kind,
                    "collection": collection,
                    "asset": path.name,
                    "path": str(path),
                    "updated_at": updated_at,
                    "size": path.stat().st_size,
                }
            )

        if not result["downloads"]:
            print(
                json.dumps(
                    {
                        "error": "github api rate limit exceeded and no local snapshots found",
                        "hint": "Set GITHUB_TOKEN (classic PAT with public_repo scope is enough for public repos).",
                        "output_dir": str(output_dir),
                    },
                    indent=2,
                )
            )
            return 2

        print(json.dumps(result, indent=2))
        return 0
    except URLError:
        # No network / DNS / TLS etc. If the user has already downloaded snapshots, use them.
        rules_re = re.compile(r"^mtg_rules_\d{8}--.*\.snapshot$")
        oracle_re = re.compile(r"^mtg_oracle_cards_\d{14}--.*\.snapshot$")

        rules_files = [p for p in output_dir.glob("mtg_rules_*.snapshot") if rules_re.match(p.name)]
        oracle_files = [p for p in output_dir.glob("mtg_oracle_cards_*.snapshot") if oracle_re.match(p.name)]

        def choose(paths: list[Path]) -> Path | None:
            if not paths:
                return None
            return sorted(paths, key=lambda p: (p.stat().st_mtime, p.stat().st_size), reverse=True)[0]

        rules_path = choose(rules_files)
        oracle_path = choose(oracle_files)

        if not rules_path and not oracle_path:
            print(
                json.dumps(
                    {
                        "error": "network unavailable and no local snapshots found",
                        "output_dir": str(output_dir),
                    },
                    indent=2,
                )
            )
            return 2

        result: dict[str, Any] = {
            "repo": args.repo,
            "tag": args.tag,
            "warning": "network_unavailable_using_local_snapshots",
            "downloads": [],
        }
        for path, kind in [(rules_path, "rules"), (oracle_path, "oracle_cards")]:
            if not path:
                continue
            collection = path.name.split("--", 1)[0]
            updated_at = datetime.fromtimestamp(path.stat().st_mtime, tz=timezone.utc).isoformat()
            result["downloads"].append(
                {
                    "kind": kind,
                    "collection": collection,
                    "asset": path.name,
                    "path": str(path),
                    "updated_at": updated_at,
                    "size": path.stat().st_size,
                }
            )

        print(json.dumps(result, indent=2))
        return 0

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

    result: dict[str, Any] = {"repo": args.repo, "tag": args.tag, "downloads": []}

    for chosen in [rules, oracle]:
        if not chosen:
            continue
        out_path = output_dir / chosen.name
        if not out_path.exists() or out_path.stat().st_size != chosen.size:
            _download(chosen.url, out_path, token=token)
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
