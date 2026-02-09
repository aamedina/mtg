"""CLI for MTG rules/card ontology generation and Qdrant ingestion."""

from __future__ import annotations

import argparse
import json
import os
from pathlib import Path
from typing import Any
from urllib.parse import urlparse

from qdrant_client.http import models

from .card_jsonld import cards_to_jsonld, write_json
from .embeddings import OpenAIEmbedder, OpenAIEmbeddingConfig, resolve_openai_api_key
from .ingest import (
    cards_text_builder,
    cards_payload_builder,
    cards_payload_patch_builder,
    load_jsonld_graph,
    rules_text_builder,
    rules_payload_builder,
    select_card_concepts,
    select_rule_concepts,
)
from .qdrant_snapshot import (
    create_collection_snapshot,
    download_collection_snapshot,
    recover_collection_from_snapshot_file,
)
from .qdrant_store import (
    QdrantConfig,
    create_client,
    ensure_collection,
    ensure_payload_indexes,
    upsert_documents,
)
from .rules_fetch import fetch_latest_rules, fetch_rules_by_token
from .rules_parser import parse_rules_file, write_graph_jsonld
from .scryfall import download_json, filter_cards, get_bulk_reference, load_cards, search_cards
from .validate_rules import validate_rules_jsonld

_DEFAULT_RULES_DIR = Path("resources/MagicCompRules")
_DEFAULT_RULES_JSONLD = Path("resources/mtg/rules.skos.jsonld")
_DEFAULT_SCRYFALL_DIR = Path("resources/scryfall")
_DEFAULT_SNAPSHOT_DIR = Path("resources/qdrant-snapshots")


_RULES_PAYLOAD_INDEXES = {
    # Core identifiers and structure
    "kind": models.PayloadSchemaType.KEYWORD,
    "uri": models.PayloadSchemaType.KEYWORD,
    "label": models.PayloadSchemaType.KEYWORD,
    "notation": models.PayloadSchemaType.KEYWORD,
    "broader": models.PayloadSchemaType.KEYWORD,
    # Array of referenced concept IRIs from dcterms:references (helps agents follow/trace rule citations).
    "references": models.PayloadSchemaType.KEYWORD,
    "scheme": models.PayloadSchemaType.KEYWORD,
    "rules_token": models.PayloadSchemaType.KEYWORD,
}

_CARDS_PAYLOAD_INDEXES = {
    "kind": models.PayloadSchemaType.KEYWORD,
    "uri": models.PayloadSchemaType.KEYWORD,
    "id": models.PayloadSchemaType.KEYWORD,
    "oracle_id": models.PayloadSchemaType.KEYWORD,
    "name": models.PayloadSchemaType.KEYWORD,
    # Full-text version of `name` for fast case-insensitive lookup by tokens.
    "name_ft": models.TextIndexParams(
        type=models.TextIndexType.TEXT,
        tokenizer=models.TokenizerType.WORD,
        lowercase=True,
        on_disk=True,
    ),
    "set": models.PayloadSchemaType.KEYWORD,
    "collector_number": models.PayloadSchemaType.KEYWORD,
    "lang": models.PayloadSchemaType.KEYWORD,
    "layout": models.PayloadSchemaType.KEYWORD,
    "rarity": models.PayloadSchemaType.KEYWORD,
    "cmc": models.PayloadSchemaType.FLOAT,
    # Full-text fields: enable fast lexical filtering (MatchText) and hybrid queries.
    "oracle_text": models.TextIndexParams(
        type=models.TextIndexType.TEXT,
        tokenizer=models.TokenizerType.WORD,
        lowercase=True,
        on_disk=True,
    ),
    "type_line": models.TextIndexParams(
        type=models.TextIndexType.TEXT,
        tokenizer=models.TokenizerType.WORD,
        lowercase=True,
        on_disk=True,
    ),
    # Array fields
    "colors": models.PayloadSchemaType.KEYWORD,
    "color_identity": models.PayloadSchemaType.KEYWORD,
    "keywords": models.PayloadSchemaType.KEYWORD,
}


def _latest_rules_file(directory: Path) -> Path:
    candidates = sorted(directory.glob("*.txt"))
    if not candidates:
        raise RuntimeError(f"No rules text files found in {directory}")
    return candidates[-1]


def _infer_output_filename_from_url(url: str, fallback: str) -> str:
    parsed = urlparse(url)
    name = Path(parsed.path).name
    return name or fallback


def _qdrant_config_from_args(args: argparse.Namespace) -> QdrantConfig:
    url = args.qdrant_url or os.getenv("QDRANT_URL", "http://localhost:6333")
    # docker-compose often passes empty strings for unset env vars; treat those as missing.
    api_key = args.qdrant_api_key or os.getenv("QDRANT_API_KEY") or None
    # Avoid noisy warnings for the common local-dev setup:
    # - dockerized Qdrant at http://localhost:6333 or http://qdrant:6333
    # - a global QDRANT_API_KEY exported in the shell for some other environment
    #
    # If the API key came from the environment (not an explicit flag) and the URL is local+http,
    # ignore it. If someone really wants an API key over plain HTTP, they can pass --qdrant-api-key.
    if not args.qdrant_api_key and api_key:
        try:
            parsed = urlparse(url)
            if parsed.scheme == "http" and (parsed.hostname or "") in {"localhost", "127.0.0.1", "qdrant"}:
                api_key = None
        except Exception:  # noqa: BLE001
            pass
    timeout = float(args.qdrant_timeout or os.getenv("QDRANT_TIMEOUT", "30"))
    return QdrantConfig(url=url, api_key=api_key, timeout=timeout)


def _openai_embedder_from_args(args: argparse.Namespace) -> OpenAIEmbedder:
    api_key = resolve_openai_api_key(args.openai_api_key)
    model = args.embedding_model or os.getenv("OPENAI_EMBEDDING_MODEL", "text-embedding-3-small")
    base_url = args.openai_base_url or os.getenv("OPENAI_BASE_URL", "https://api.openai.com/v1")
    dimensions = args.embedding_dimensions
    timeout = float(args.embedding_timeout)
    max_retries = int(args.embedding_max_retries)

    config = OpenAIEmbeddingConfig(
        api_key=api_key,
        model=model,
        base_url=base_url,
        dimensions=dimensions,
        timeout=timeout,
        max_retries=max_retries,
    )
    return OpenAIEmbedder(config)


def cmd_rules_fetch_latest(args: argparse.Namespace) -> None:
    download = fetch_latest_rules(Path(args.output_dir))
    print(
        json.dumps(
            {
                "release_token": download.release_token,
                "url": download.url,
                "output_path": str(download.output_path),
            },
            indent=2,
        )
    )


def cmd_rules_parse(args: argparse.Namespace) -> None:
    input_path = Path(args.input_file) if args.input_file else _latest_rules_file(Path(args.rules_dir))
    parsed = parse_rules_file(
        input_path,
        release_token=args.release_token,
        source_url=args.source_url,
    )
    output_path = Path(args.output)
    write_graph_jsonld(parsed.graph_doc, output_path)

    node_count = len(parsed.graph_doc.get("@graph", []))
    print(
        json.dumps(
            {
                "input_file": str(input_path),
                "release_token": parsed.release_token,
                "effective_date": parsed.effective_date,
                "graph_nodes": node_count,
                "output_path": str(output_path),
            },
            indent=2,
        )
    )


def cmd_rules_refresh(args: argparse.Namespace) -> None:
    rules_dir = Path(args.rules_dir)
    if args.release_token:
        download = fetch_rules_by_token(
            rules_dir,
            release_token=str(args.release_token),
            url=args.rules_url,
        )
    else:
        download = fetch_latest_rules(rules_dir)
    parsed = parse_rules_file(download.output_path, release_token=download.release_token, source_url=download.url)
    write_graph_jsonld(parsed.graph_doc, Path(args.output))

    print(
        json.dumps(
            {
                "requested_release_token": args.release_token,
                "release_token": parsed.release_token,
                "effective_date": parsed.effective_date,
                "source_url": download.url,
                "rules_txt": str(download.output_path),
                "jsonld": str(args.output),
            },
            indent=2,
        )
    )


def cmd_rules_upsert_qdrant(args: argparse.Namespace) -> None:
    graph_nodes = load_jsonld_graph(Path(args.input_jsonld))
    concepts = select_rule_concepts(graph_nodes)

    config = _qdrant_config_from_args(args)
    embedder = _openai_embedder_from_args(args)
    client = create_client(config)

    ensure_collection(
        client,
        args.collection,
        vector_size=embedder.vector_size,
        recreate=args.recreate,
    )

    ensure_payload_indexes(client, args.collection, indexes=_RULES_PAYLOAD_INDEXES)

    stats = upsert_documents(
        client,
        args.collection,
        concepts,
        text_builder=rules_text_builder,
        payload_builder=rules_payload_builder,
        embedder=embedder,
        delete_payload_keys=["embedding_model", "embedding_dimensions"],
        batch_size=args.batch_size,
    )

    print(
        json.dumps(
            {
                "collection": args.collection,
                "upserted": stats.upserted,
                "skipped": stats.skipped,
                "payload_patched": stats.payload_patched,
                "payload_cleaned": stats.payload_cleaned,
                "embedding_model": embedder.config.model,
                "embedding_dimensions": embedder.vector_size,
                "qdrant_url": config.url,
            },
            indent=2,
        )
    )


def cmd_rules_validate(args: argparse.Namespace) -> None:
    result = validate_rules_jsonld(Path(args.input_jsonld), strict=bool(args.strict))
    print(json.dumps(result, indent=2))
    if result.get("error_count", 0) > 0:
        raise SystemExit(1)


def cmd_scryfall_download_oracle(args: argparse.Namespace) -> None:
    output_dir = Path(args.output_dir)
    output_dir.mkdir(parents=True, exist_ok=True)

    url = args.bulk_url
    metadata: dict[str, Any] = {}
    if not url:
        reference = get_bulk_reference(bulk_type=args.bulk_type)
        url = reference.download_uri
        metadata = {
            "bulk_id": reference.id,
            "bulk_type": reference.type,
            "bulk_name": reference.name,
            "updated_at": reference.updated_at,
        }

    if args.output_file:
        output_path = Path(args.output_file)
    else:
        # Default to gzip to keep the repo and local disk usage manageable.
        output_path = output_dir / _infer_output_filename_from_url(url, "oracle-cards.json.gz")
        if output_path.suffix == ".json":
            output_path = output_path.with_suffix(".json.gz")

    download_json(url, output_path)

    metadata.update(
        {
            "download_url": url,
            "output_path": str(output_path),
        }
    )
    (output_dir / "latest-oracle.json").write_text(json.dumps(metadata, indent=2), encoding="utf-8")
    print(json.dumps(metadata, indent=2))


def _get_cards_from_args(args: argparse.Namespace) -> tuple[list[dict[str, Any]], str]:
    if args.query:
        cards = search_cards(args.query, max_cards=args.max_cards)
        return cards, f"scryfall-search:{args.query}"

    if args.input_json:
        path = Path(args.input_json)
        cards = load_cards(path)
        return cards, str(path)

    raise RuntimeError("Provide either --input-json or --query")


def _apply_card_filters(args: argparse.Namespace, cards: list[dict[str, Any]]) -> list[dict[str, Any]]:
    return filter_cards(
        cards,
        format_name=args.format_name,
        set_code=args.set_code,
        where=args.where,
    )


def cmd_scryfall_export_jsonld(args: argparse.Namespace) -> None:
    cards, source = _get_cards_from_args(args)
    filtered = _apply_card_filters(args, cards)

    doc = cards_to_jsonld(filtered)

    output_path = Path(args.output)
    write_json(output_path, doc)

    print(
        json.dumps(
            {
                "source": source,
                "cards_input": len(cards),
                "cards_exported": len(filtered),
                "output": str(output_path),
            },
            indent=2,
        )
    )


def cmd_cards_collection(args: argparse.Namespace) -> None:
    cards, source = _get_cards_from_args(args)
    filtered = _apply_card_filters(args, cards)

    doc = cards_to_jsonld(filtered)

    output_path = Path(args.output_jsonld)
    write_json(output_path, doc)

    nodes = select_card_concepts(doc.get("@graph", []))

    config = _qdrant_config_from_args(args)
    embedder = _openai_embedder_from_args(args)
    client = create_client(config)
    ensure_collection(
        client,
        args.collection,
        vector_size=embedder.vector_size,
        recreate=args.recreate,
    )

    ensure_payload_indexes(client, args.collection, indexes=_CARDS_PAYLOAD_INDEXES)

    stats = upsert_documents(
        client,
        args.collection,
        nodes,
        text_builder=cards_text_builder,
        payload_builder=cards_payload_builder,
        embedder=embedder,
        payload_patch_builder=cards_payload_patch_builder,
        inspect_payload_keys=["name_ft", "oracle_text"],
        delete_payload_keys=["embedding_model", "embedding_dimensions"],
        batch_size=args.batch_size,
    )

    print(
        json.dumps(
            {
                "collection": args.collection,
                "source": source,
                "cards_input": len(cards),
                "cards_selected": len(filtered),
                "upserted": stats.upserted,
                "skipped": stats.skipped,
                "payload_patched": stats.payload_patched,
                "payload_cleaned": stats.payload_cleaned,
                "jsonld": str(output_path),
                "embedding_model": embedder.config.model,
                "embedding_dimensions": embedder.vector_size,
                "qdrant_url": config.url,
            },
            indent=2,
        )
    )


def cmd_cards_upsert_jsonld(args: argparse.Namespace) -> None:
    nodes = load_jsonld_graph(Path(args.input_jsonld))
    cards = select_card_concepts(nodes)

    config = _qdrant_config_from_args(args)
    embedder = _openai_embedder_from_args(args)
    client = create_client(config)
    ensure_collection(
        client,
        args.collection,
        vector_size=embedder.vector_size,
        recreate=args.recreate,
    )

    ensure_payload_indexes(client, args.collection, indexes=_CARDS_PAYLOAD_INDEXES)

    stats = upsert_documents(
        client,
        args.collection,
        cards,
        text_builder=cards_text_builder,
        payload_builder=cards_payload_builder,
        embedder=embedder,
        payload_patch_builder=cards_payload_patch_builder,
        inspect_payload_keys=["name_ft", "oracle_text"],
        delete_payload_keys=["embedding_model", "embedding_dimensions"],
        batch_size=args.batch_size,
    )

    print(
        json.dumps(
            {
                "collection": args.collection,
                "cards": len(cards),
                "upserted": stats.upserted,
                "skipped": stats.skipped,
                "payload_patched": stats.payload_patched,
                "payload_cleaned": stats.payload_cleaned,
                "embedding_model": embedder.config.model,
                "embedding_dimensions": embedder.vector_size,
                "qdrant_url": config.url,
            },
            indent=2,
        )
    )


def cmd_qdrant_snapshot(args: argparse.Namespace) -> None:
    config = _qdrant_config_from_args(args)
    snapshot_name = create_collection_snapshot(
        qdrant_url=config.url,
        collection=args.collection,
        api_key=config.api_key,
        timeout=float(args.snapshot_timeout),
    )
    output = download_collection_snapshot(
        qdrant_url=config.url,
        collection=args.collection,
        snapshot_name=snapshot_name,
        output_dir=Path(args.output_dir),
        api_key=config.api_key,
        timeout=float(args.snapshot_timeout),
    )

    print(
        json.dumps(
            {
                "collection": args.collection,
                "snapshot_name": snapshot_name,
                "snapshot_path": str(output),
                "qdrant_url": config.url,
            },
            indent=2,
        )
    )


def cmd_qdrant_recover(args: argparse.Namespace) -> None:
    config = _qdrant_config_from_args(args)
    payload = recover_collection_from_snapshot_file(
        qdrant_url=config.url,
        collection=args.collection,
        snapshot_path=Path(args.snapshot_path),
        api_key=config.api_key,
        timeout=float(args.snapshot_timeout),
        wait=bool(args.wait),
        priority=args.priority,
        checksum=args.checksum,
    )

    print(
        json.dumps(
            {
                "collection": args.collection,
                "snapshot_path": str(args.snapshot_path),
                "qdrant_url": config.url,
                "result": payload.get("result"),
                "status": payload.get("status"),
            },
            indent=2,
        )
    )


def cmd_qdrant_ensure_indexes(args: argparse.Namespace) -> None:
    config = _qdrant_config_from_args(args)
    client = create_client(config)

    if args.schema == "rules":
        indexes = _RULES_PAYLOAD_INDEXES
    elif args.schema == "cards":
        indexes = _CARDS_PAYLOAD_INDEXES
    else:
        raise RuntimeError(f"Unknown schema: {args.schema}")

    ensure_payload_indexes(client, args.collection, indexes=indexes, wait=bool(args.wait))

    info = client.get_collection(args.collection)
    keys = sorted((info.payload_schema or {}).keys())
    print(
        json.dumps(
            {
                "collection": args.collection,
                "schema": args.schema,
                "indexed_fields": keys,
                "qdrant_url": config.url,
            },
            indent=2,
        )
    )


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="MTG ontology + Qdrant CLI")
    sub = parser.add_subparsers(dest="command", required=True)

    rules = sub.add_parser("rules", help="Comprehensive Rules workflows")
    rules_sub = rules.add_subparsers(dest="rules_cmd", required=True)

    rules_fetch = rules_sub.add_parser("fetch-latest", help="Download latest rules TXT")
    rules_fetch.add_argument("--output-dir", default=str(_DEFAULT_RULES_DIR))
    rules_fetch.set_defaults(func=cmd_rules_fetch_latest)

    rules_parse = rules_sub.add_parser("parse", help="Parse rules TXT into SKOS JSON-LD")
    rules_parse.add_argument("--input-file", default=None)
    rules_parse.add_argument("--rules-dir", default=str(_DEFAULT_RULES_DIR))
    rules_parse.add_argument("--release-token", default=None)
    rules_parse.add_argument("--source-url", default=None)
    rules_parse.add_argument("--output", default=str(_DEFAULT_RULES_JSONLD))
    rules_parse.set_defaults(func=cmd_rules_parse)

    rules_refresh = rules_sub.add_parser("refresh", help="Fetch latest rules and regenerate SKOS")
    rules_refresh.add_argument("--rules-dir", default=str(_DEFAULT_RULES_DIR))
    rules_refresh.add_argument(
        "--release-token",
        default=None,
        help="Fetch a specific rules release by YYYYMMDD token (default: discover latest).",
    )
    rules_refresh.add_argument(
        "--rules-url",
        default=None,
        help="Override the rules TXT URL (advanced; default is derived from the token/year).",
    )
    rules_refresh.add_argument("--output", default=str(_DEFAULT_RULES_JSONLD))
    rules_refresh.set_defaults(func=cmd_rules_refresh)

    rules_qdrant = rules_sub.add_parser("upsert-qdrant", help="Upsert rules JSON-LD into Qdrant")
    rules_qdrant.add_argument("--input-jsonld", default=str(_DEFAULT_RULES_JSONLD))
    rules_qdrant.add_argument("--collection", default="mtg_rules")
    _add_qdrant_and_embedding_args(rules_qdrant)
    rules_qdrant.set_defaults(func=cmd_rules_upsert_qdrant)

    rules_validate = rules_sub.add_parser("validate", help="Validate rules SKOS JSON-LD")
    rules_validate.add_argument("--input-jsonld", default=str(_DEFAULT_RULES_JSONLD))
    rules_validate.add_argument(
        "--strict",
        action="store_true",
        help="Treat dangling dcterms:references targets as errors (default: warnings)",
    )
    rules_validate.set_defaults(func=cmd_rules_validate)

    scryfall = sub.add_parser("scryfall", help="Scryfall data workflows")
    scry_sub = scryfall.add_subparsers(dest="scry_cmd", required=True)

    scry_download = scry_sub.add_parser("download-bulk", help="Download Scryfall bulk JSON")
    scry_download.add_argument("--bulk-type", default="oracle_cards")
    scry_download.add_argument("--bulk-url", default=None)
    scry_download.add_argument("--output-dir", default=str(_DEFAULT_SCRYFALL_DIR))
    scry_download.add_argument("--output-file", default=None)
    scry_download.set_defaults(func=cmd_scryfall_download_oracle)

    scry_export = scry_sub.add_parser("export-jsonld", help="Convert cards JSON to JSON-LD")
    _add_card_source_args(scry_export)
    _add_card_filter_args(scry_export)
    scry_export.add_argument("--output", required=True)
    scry_export.set_defaults(func=cmd_scryfall_export_jsonld)

    cards = sub.add_parser("cards", help="Card collection and Qdrant workflows")
    cards_sub = cards.add_subparsers(dest="cards_cmd", required=True)

    cards_collection = cards_sub.add_parser(
        "build-collection",
        help="Filter cards, export JSON-LD, and upsert a Qdrant collection",
    )
    _add_card_source_args(cards_collection)
    _add_card_filter_args(cards_collection)
    cards_collection.add_argument("--collection", required=True)
    cards_collection.add_argument(
        "--output-jsonld",
        default="resources/scryfall/collection.jsonld.gz",
        help="Path to write the filtered card JSON-LD",
    )
    _add_qdrant_and_embedding_args(cards_collection)
    cards_collection.set_defaults(func=cmd_cards_collection)

    cards_upsert = cards_sub.add_parser("upsert-jsonld", help="Upsert existing card JSON-LD into Qdrant")
    cards_upsert.add_argument("--input-jsonld", required=True)
    cards_upsert.add_argument("--collection", required=True)
    _add_qdrant_and_embedding_args(cards_upsert)
    cards_upsert.set_defaults(func=cmd_cards_upsert_jsonld)

    qdrant = sub.add_parser("qdrant", help="Qdrant operations")
    qdrant_sub = qdrant.add_subparsers(dest="qdrant_cmd", required=True)

    qdrant_snapshot = qdrant_sub.add_parser(
        "snapshot-collection",
        help="Create and download a Qdrant collection snapshot",
    )
    qdrant_snapshot.add_argument("--collection", required=True)
    qdrant_snapshot.add_argument("--output-dir", default=str(_DEFAULT_SNAPSHOT_DIR))
    qdrant_snapshot.add_argument("--snapshot-timeout", type=float, default=180.0)
    qdrant_snapshot.add_argument("--qdrant-url", default=None)
    qdrant_snapshot.add_argument("--qdrant-api-key", default=None)
    qdrant_snapshot.add_argument("--qdrant-timeout", default=None)
    qdrant_snapshot.set_defaults(func=cmd_qdrant_snapshot)

    qdrant_recover = qdrant_sub.add_parser(
        "recover-snapshot",
        help="Recover (restore) a collection from a local Qdrant snapshot file",
    )
    qdrant_recover.add_argument("--collection", required=True)
    qdrant_recover.add_argument("--snapshot-path", required=True)
    qdrant_recover.add_argument(
        "--no-wait",
        dest="wait",
        action="store_false",
        default=True,
        help="Do not wait for Qdrant to finish recovery (default: wait).",
    )
    qdrant_recover.add_argument(
        "--priority",
        default=None,
        help="Snapshot priority (replica|no_sync|snapshot). Default: server default.",
    )
    qdrant_recover.add_argument("--checksum", default=None, help="Optional snapshot checksum (advanced).")
    qdrant_recover.add_argument("--snapshot-timeout", type=float, default=600.0)
    qdrant_recover.add_argument("--qdrant-url", default=None)
    qdrant_recover.add_argument("--qdrant-api-key", default=None)
    qdrant_recover.add_argument("--qdrant-timeout", default=None)
    qdrant_recover.set_defaults(func=cmd_qdrant_recover)

    qdrant_indexes = qdrant_sub.add_parser(
        "ensure-indexes",
        help="Ensure payload indexes exist for a collection (rules or cards schema)",
    )
    qdrant_indexes.add_argument("--collection", required=True)
    qdrant_indexes.add_argument("--schema", choices=["rules", "cards"], required=True)
    qdrant_indexes.add_argument(
        "--no-wait",
        dest="wait",
        action="store_false",
        default=True,
        help="Do not wait for index creation to finish (default: wait).",
    )
    qdrant_indexes.add_argument("--qdrant-url", default=None)
    qdrant_indexes.add_argument("--qdrant-api-key", default=None)
    qdrant_indexes.add_argument("--qdrant-timeout", default=None)
    qdrant_indexes.set_defaults(func=cmd_qdrant_ensure_indexes)

    return parser


def _add_qdrant_and_embedding_args(parser: argparse.ArgumentParser) -> None:
    parser.add_argument("--qdrant-url", default=None)
    parser.add_argument("--qdrant-api-key", default=None)
    parser.add_argument("--qdrant-timeout", default=None)
    parser.add_argument("--batch-size", type=int, default=64)
    parser.add_argument("--recreate", action="store_true")

    parser.add_argument("--openai-api-key", default=None)
    parser.add_argument("--openai-base-url", default=None)
    # Default to env var OPENAI_EMBEDDING_MODEL if set; otherwise text-embedding-3-small.
    parser.add_argument("--embedding-model", default=None)
    parser.add_argument("--embedding-dimensions", type=int, default=None)
    parser.add_argument("--embedding-timeout", type=float, default=60.0)
    parser.add_argument("--embedding-max-retries", type=int, default=4)


def _add_card_source_args(parser: argparse.ArgumentParser) -> None:
    parser.add_argument("--input-json", default=None, help="Path to Scryfall JSON list/object")
    parser.add_argument("--query", default=None, help="Scryfall search query")
    parser.add_argument("--max-cards", type=int, default=5000)


def _add_card_filter_args(parser: argparse.ArgumentParser) -> None:
    parser.add_argument("--format-name", default=None, help="Legality filter, e.g. commander")
    parser.add_argument("--set-code", default=None, help="Set filter, e.g. neo")
    parser.add_argument(
        "--where",
        action="append",
        default=[],
        help="Additional filter key=value using dotted paths, e.g. legalities.modern=legal",
    )


def main(argv: list[str] | None = None) -> None:
    parser = build_parser()
    args = parser.parse_args(argv)
    args.func(args)


if __name__ == "__main__":
    main()
