"""Fetch and cache MTG Comprehensive Rules text files."""

from __future__ import annotations

import json
import re
from dataclasses import dataclass
from datetime import datetime, timezone
from pathlib import Path
from urllib.parse import unquote, urljoin

import requests

RULES_INDEX_URL = "https://magic.wizards.com/en/rules"
MEDIA_RULES_URL_TEMPLATE = "https://media.wizards.com/{year}/downloads/MagicCompRules%20{token}.txt"

_ABS_TXT_RE = re.compile(
    r"https?://[^\"'<>\s]*MagicCompRules(?:%20|\s)?(\d{8})\.txt",
    re.IGNORECASE,
)
_REL_HREF_RE = re.compile(r"href=[\"']([^\"']*MagicCompRules[^\"']*\.txt)[\"']", re.IGNORECASE)
_FILENAME_DATE_RE = re.compile(r"MagicCompRules\s*(\d{8})\.txt", re.IGNORECASE)


@dataclass(frozen=True)
class RulesDownload:
    """Metadata for a downloaded rules file."""

    url: str
    release_token: str
    output_path: Path


def _clean_candidate_url(url: str) -> str:
    return url.replace(" ", "%20")


def construct_rules_url(release_token: str) -> str:
    """Construct the canonical media.wizards.com rules TXT URL for a YYYYMMDD token."""
    token = str(release_token or "")
    if not re.fullmatch(r"\d{8}", token):
        raise ValueError(f"Invalid release token (expected YYYYMMDD): '{release_token}'")
    year = token[:4]
    return MEDIA_RULES_URL_TEMPLATE.format(year=year, token=token)


def discover_latest_rules_url(session: requests.Session | None = None) -> tuple[str, str]:
    """Discover the latest rules TXT URL and its date token (YYYYMMDD)."""
    http = session or requests.Session()
    response = http.get(RULES_INDEX_URL, timeout=30)
    response.raise_for_status()
    html = response.text

    candidates: list[tuple[str, str]] = []

    for match in _ABS_TXT_RE.finditer(html):
        url = _clean_candidate_url(match.group(0))
        date_token = match.group(1)
        candidates.append((date_token, url))

    for match in _REL_HREF_RE.finditer(html):
        href = _clean_candidate_url(match.group(1))
        # Percent-encoding can create bogus 8-digit runs like %20 + YYYYMMDD -> 2020YYYY...,
        # so unquote before extracting the YYYYMMDD token from the filename itself.
        href_unquoted = unquote(href)
        date_match = _FILENAME_DATE_RE.search(href_unquoted)
        if not date_match:
            continue
        date_token = date_match.group(1)
        url = urljoin(RULES_INDEX_URL, href)
        candidates.append((date_token, url))

    deduped: dict[str, tuple[str, str]] = {}
    for date_token, url in candidates:
        deduped[url] = (date_token, url)

    if not deduped:
        raise RuntimeError(
            "Could not discover a comprehensive rules TXT link from https://magic.wizards.com/en/rules"
        )

    latest = max(deduped.values(), key=lambda item: item[0])
    return latest[1], latest[0]


def download_rules_text(url: str, output_path: Path, session: requests.Session | None = None) -> Path:
    """Download a rules text file to disk."""
    output_path.parent.mkdir(parents=True, exist_ok=True)
    http = session or requests.Session()
    with http.get(url, timeout=60, stream=True) as response:
        response.raise_for_status()
        with output_path.open("wb") as fh:
            for chunk in response.iter_content(chunk_size=1024 * 1024):
                if chunk:
                    fh.write(chunk)
    return output_path


def fetch_rules_by_token(output_dir: Path, *, release_token: str, url: str | None = None) -> RulesDownload:
    """Download a specific rules TXT into output_dir."""
    output_dir.mkdir(parents=True, exist_ok=True)
    token = str(release_token)
    resolved_url = _clean_candidate_url(url or construct_rules_url(token))
    target_path = output_dir / f"{token}.txt"

    if not target_path.exists():
        download_rules_text(resolved_url, target_path)

    metadata = {
        "fetched_at": datetime.now(timezone.utc).isoformat(),
        "url": resolved_url,
        "release_token": token,
        "local_path": str(target_path),
    }
    (output_dir / f"{token}.json").write_text(json.dumps(metadata, indent=2), encoding="utf-8")

    return RulesDownload(url=resolved_url, release_token=token, output_path=target_path)


def fetch_latest_rules(output_dir: Path) -> RulesDownload:
    """Discover and download latest rules text into output_dir."""
    output_dir.mkdir(parents=True, exist_ok=True)
    url, release_token = discover_latest_rules_url()
    target_path = output_dir / f"{release_token}.txt"

    if not target_path.exists():
        download_rules_text(url, target_path)

    metadata = {
        "fetched_at": datetime.now(timezone.utc).isoformat(),
        "url": url,
        "release_token": release_token,
        "local_path": str(target_path),
    }
    (output_dir / "latest.json").write_text(json.dumps(metadata, indent=2), encoding="utf-8")

    return RulesDownload(url=url, release_token=release_token, output_path=target_path)
