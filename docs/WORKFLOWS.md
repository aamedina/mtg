# Workflows

All commands are run from the repo root.

## Setup

```bash
python3 -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
```

Environment variables:

- `OPENAI_API_KEY` is required for any Qdrant upsert (rules or cards).
- See `.env.example` for the full list.

## Local Test Suite

Smoke test (no OpenAI key required):

```bash
./scripts/test_smoke.sh
```

End-to-end test (requires `OPENAI_API_KEY`; starts Dockerized Qdrant, upserts rules + cards, runs query assertions, snapshots + restores):

```bash
export OPENAI_API_KEY="..."
./scripts/test_e2e.sh
```

Tuning knobs (optional):

- `RULES_TOKEN=20260116` (default)
- `CARDS_QUERY='set:m20 lang:en'` (default)
- `CARDS_MAX=30` (default)
- `PREFIX=local` (default; prefixes collection names)

## Comprehensive Rules -> SKOS JSON-LD

Regenerate from the latest Wizards rules TXT:

```bash
python -m mtg_ontology.cli rules refresh \
  --rules-dir resources/MagicCompRules \
  --output resources/mtg/rules.skos.jsonld
```

Regenerate a specific rules release by date token (used for tagged releases):

```bash
python -m mtg_ontology.cli rules refresh \
  --release-token 20260116 \
  --rules-dir resources/MagicCompRules \
  --output resources/mtg/rules.skos.jsonld
```

Outputs:

- `resources/MagicCompRules/latest.json`
- `resources/MagicCompRules/<YYYYMMDD>.txt`
- `resources/MagicCompRules/<YYYYMMDD>.json` (metadata; when fetching by token)
- `resources/mtg/rules.skos.jsonld`

Validate the generated SKOS (optional):

```bash
python -m mtg_ontology.cli rules validate \
  --input-jsonld resources/mtg/rules.skos.jsonld
```

Treat dangling `dcterms:references` targets as errors:

```bash
python -m mtg_ontology.cli rules validate \
  --strict \
  --input-jsonld resources/mtg/rules.skos.jsonld
```

## Dockerized Qdrant (Local)

```bash
docker compose up -d qdrant
```

Qdrant is exposed on `http://localhost:6333`.

Notes:

- `docker-compose.yml` sets the Qdrant container `nofile` ulimit to avoid "Too many open files" errors when payload indexes are enabled.

To wipe local Qdrant storage:

```bash
docker compose down -v
```

## Dockerized End-to-End (Recommended)

Use the `mtg-ontology` service in `docker-compose.yml` to run the same commands CI runs:

```bash
docker compose up -d qdrant

docker compose run --rm mtg-ontology rules refresh \
  --rules-dir resources/MagicCompRules \
  --output resources/mtg/rules.skos.jsonld

docker compose run --rm mtg-ontology rules validate \
  --input-jsonld resources/mtg/rules.skos.jsonld
```

If you see permission issues on Linux (files owned by `root`), set `UID`/`GID` in your environment
or create a `.env` from `.env.example`.

## Upsert Rules Into Qdrant (OpenAI Embeddings)

```bash
export OPENAI_API_KEY="..."
python -m mtg_ontology.cli rules upsert-qdrant \
  --input-jsonld resources/mtg/rules.skos.jsonld \
  --collection mtg_rules
```

Docker equivalent:

```bash
export OPENAI_API_KEY="..."
docker compose run --rm mtg-ontology rules upsert-qdrant \
  --input-jsonld resources/mtg/rules.skos.jsonld \
  --collection mtg_rules
```

Notes:

- The collection vector size is determined by the embedding model.
- Default embedding model is `text-embedding-3-small` (1536 dimensions).
- Embeddings are generated via the OpenAI API. Upserting large card sets can be expensive.
- The CLI automatically creates payload indexes for common filter fields; those indexes are included in Qdrant snapshots.

## Snapshot A Qdrant Collection

```bash
python -m mtg_ontology.cli qdrant snapshot-collection \
  --collection mtg_rules \
  --output-dir resources/qdrant-snapshots
```

Docker equivalent:

```bash
docker compose run --rm mtg-ontology qdrant snapshot-collection \
  --collection mtg_rules \
  --output-dir resources/qdrant-snapshots
```

## Recover From A Snapshot

If you downloaded a snapshot file from a GitHub release, you can restore it into a running Qdrant:

```bash
docker compose up -d qdrant

docker compose run --rm mtg-ontology qdrant recover-snapshot \
  --collection mtg_rules_20260116 \
  --snapshot-path resources/qdrant-snapshots/mtg_rules_20260116--<snapshot_name>.snapshot
```

## Scryfall Bulk (Pinned URL) -> JSON-LD

Raw download:

```bash
python -m mtg_ontology.cli scryfall download-bulk \
  --bulk-url "https://data.scryfall.io/oracle-cards/oracle-cards-20260208220524.json" \
  --output-dir resources/scryfall \
  --output-file resources/scryfall/oracle-cards-20260208220524.json.gz
```

Convert to JSON-LD:

```bash
python -m mtg_ontology.cli scryfall export-jsonld \
  --input-json resources/scryfall/oracle-cards-20260208220524.json.gz \
  --output resources/scryfall/oracle-cards-20260208220524.jsonld.gz
```

JSON-LD modeling notes:

- The converter preserves the Scryfall JSON structure 1:1 (no key renaming).
- The JSON-LD `@id` prefers `card["uri"]` (Scryfall API URI) when present.

## Build A Filtered Card Collection And Upsert

Example: only cards legal in Commander, from the pinned bulk file:

```bash
export OPENAI_API_KEY="..."
python -m mtg_ontology.cli cards build-collection \
  --input-json resources/scryfall/oracle-cards-20260208220524.json.gz \
  --format-name commander \
  --collection mtg_cards_commander \
  --output-jsonld resources/scryfall/commander.cards.jsonld.gz
```

Example: all cards from a specific set code:

```bash
export OPENAI_API_KEY="..."
python -m mtg_ontology.cli cards build-collection \
  --input-json resources/scryfall/oracle-cards-20260208220524.json.gz \
  --set-code mh3 \
  --collection mtg_cards_mh3 \
  --output-jsonld resources/scryfall/mh3.cards.jsonld.gz
```

Example: ad-hoc filters with `--where` (dotted key paths):

```bash
export OPENAI_API_KEY="..."
python -m mtg_ontology.cli cards build-collection \
  --input-json resources/scryfall/oracle-cards-20260208220524.json.gz \
  --where "legalities.modern=legal" \
  --where "lang=en" \
  --collection mtg_cards_modern_en \
  --output-jsonld resources/scryfall/modern-en.cards.jsonld.gz
```

## Using Existing Qdrant

All Qdrant commands accept:

- `--qdrant-url` (or env `QDRANT_URL`)
- `--qdrant-api-key` (or env `QDRANT_API_KEY`)

Example:

```bash
export OPENAI_API_KEY="..."
python -m mtg_ontology.cli rules upsert-qdrant \
  --qdrant-url "https://YOUR-QDRANT" \
  --qdrant-api-key "..." \
  --collection mtg_rules \
  --input-jsonld resources/mtg/rules.skos.jsonld
```

## GitHub Releases (Tagged)

Tagging `rules-YYYYMMDD` triggers `.github/workflows/release.yml`:

- regenerates rules SKOS for that `YYYYMMDD`
- upserts into a dockerized Qdrant using OpenAI embeddings
- snapshots the collection and uploads it as a release artifact

Example:

```bash
git tag rules-20260116
git push origin rules-20260116
```
