#!/usr/bin/env python3
"""Download the most recent oracle-cards Qdrant snapshot from GitHub releases.

Intended for GitHub Actions: restores previously embedded card vectors so we don't re-embed
the entire oracle dataset on every rules-tagged release.
"""

from __future__ import annotations

import argparse
import json
import os
import re
import sys
import urllib.request
from pathlib import Path


_DEFAULT_PATTERN = r"^mtg_oracle_cards_\d{14}--.*\.snapshot$"


def _github_api_get(url: str, *, token: str) -> dict | list:
    req = urllib.request.Request(
        url,
        headers={
            "Accept": "application/vnd.github+json",
            "Authorization": f"Bearer {token}",
            "X-GitHub-Api-Version": "2022-11-28",
        },
    )
    with urllib.request.urlopen(req, timeout=30) as resp:  # noqa: S310
        return json.loads(resp.read().decode("utf-8"))


def _download_asset(*, url: str, token: str, output_path: Path) -> None:
    req = urllib.request.Request(
        url,
        headers={
            "Accept": "application/octet-stream",
            "Authorization": f"Bearer {token}",
            "X-GitHub-Api-Version": "2022-11-28",
        },
    )
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with urllib.request.urlopen(req, timeout=600) as resp, output_path.open("wb") as f:  # noqa: S310
        while True:
            chunk = resp.read(1024 * 1024)
            if not chunk:
                break
            f.write(chunk)


def main(argv: list[str] | None = None) -> int:
    ap = argparse.ArgumentParser()
    ap.add_argument("--output-dir", default="resources/qdrant-snapshots")
    ap.add_argument("--pattern", default=_DEFAULT_PATTERN)
    args = ap.parse_args(argv)

    repo = os.environ.get("GITHUB_REPOSITORY")
    token = os.environ.get("GITHUB_TOKEN")
    # Note: we intentionally do not require the current tag name. In Actions this script
    # is typically used from a tag workflow, but it can also be used from other contexts.
    if not repo or not token:
        print("", end="")
        return 0

    pattern_value = str(args.pattern)
    # If this workflow is running with a pinned oracle dataset token, prefer restoring
    # a snapshot for that exact collection. This avoids accidentally restoring an older
    # token's snapshot after the pinned oracle dataset changes.
    if pattern_value == _DEFAULT_PATTERN:
        oracle_collection = os.environ.get("ORACLE_COLLECTION") or ""
        oracle_token = os.environ.get("ORACLE_TOKEN") or ""
        if oracle_collection:
            pattern_value = rf"^{re.escape(oracle_collection)}--.*\.snapshot$"
        elif oracle_token:
            pattern_value = rf"^mtg_oracle_cards_{re.escape(oracle_token)}--.*\.snapshot$"

    pattern = re.compile(pattern_value)
    releases = _github_api_get(f"https://api.github.com/repos/{repo}/releases?per_page=50", token=token)
    if not isinstance(releases, list):
        print("", end="")
        return 0

    chosen = None
    for rel in releases:
        if not isinstance(rel, dict):
            continue
        for asset in rel.get("assets", []) or []:
            if not isinstance(asset, dict):
                continue
            name = asset.get("name") or ""
            if pattern.match(str(name)):
                chosen = asset
                break
        if chosen:
            break

    if not chosen:
        print("", end="")
        return 0

    asset_url = str(chosen.get("url") or "")
    asset_name = str(chosen.get("name") or "")
    if not asset_url or not asset_name:
        print("", end="")
        return 0

    output_path = Path(args.output_dir) / asset_name
    _download_asset(url=asset_url, token=token, output_path=output_path)
    print(str(output_path))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
