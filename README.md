# mtg-ontology

This repo turns two MTG data sources into JSON-LD you can reuse in other projects:

- The *Magic: The Gathering Comprehensive Rules* as a SKOS concept scheme (one concept per rule/subrule and glossary term)
- Scryfall card JSON as JSON-LD, keeping the Scryfall schema 1:1

It also includes a small CLI that can embed those JSON-LD nodes with OpenAI and upsert them into Qdrant,
then snapshot the collection for distribution.

`deps.edn` is kept on purpose so a Clojure project can depend on this repo just to get the `resources/` artifacts on its classpath.

## Read First

- `docs/REPO_MAP.md` (what files exist and why)
- `docs/WORKFLOWS.md` (copy/paste commands)
- `docs/QDRANT_QUERY_GUIDE.md` (agent-friendly query patterns)
- `docs/CODEX_AGENT_GUIDE.md` (Codex skill setup + prompts to test)
- `docs/RULES_SKOS_MODEL.md` (rules SKOS modeling)
- `docs/SCRYFALL_JSONLD_MODEL.md` (Scryfall JSON-LD mapping)

## Outputs (All Under `resources/`)

- Comprehensive Rules TXT cache: `resources/MagicCompRules/*.txt`
- Latest rules metadata: `resources/MagicCompRules/latest.json`
- Rules SKOS JSON-LD: `resources/mtg/rules.skos.jsonld`
- Rules JSON-LD context (standalone): `resources/mtg/rules.context.jsonld`
- Scryfall bulk + exports (compressed): `resources/scryfall/`
- Scryfall JSON-LD context (standalone): `resources/scryfall/context.jsonld`
- Qdrant snapshots (generated): `resources/qdrant-snapshots/` (gitignored)

## Quickstart

### 1) Start Qdrant (Docker)

```bash
docker compose up -d qdrant
```

### 2) (Recommended) Restore Prebuilt Qdrant Snapshots From A Release

This avoids re-embedding the full oracle-cards dataset.

```bash
./scripts/bootstrap_qdrant_from_release.sh rules-20260116
```

If you hit GitHub API rate limits while downloading release assets, set `GITHUB_TOKEN` and rerun:

```bash
export GITHUB_TOKEN="..."
```

### 3) Ask Questions (Retrieval Helper)

Once Qdrant is populated (either by restoring snapshots or embedding locally), retrieve grounded
context for an agent with:

```bash
. .venv/bin/activate
export MTG_RULES_COLLECTION="mtg_rules_20260116"
export MTG_CARDS_COLLECTION="mtg_oracle_cards_20260208220524"
./scripts/mtg_retrieve.py "how does ward work?"
```

For a full Codex setup (repo-local skill + prompts to test), see `docs/CODEX_AGENT_GUIDE.md`.

### 4) Run The Local Test Suite (Recommended)

Smoke (no OpenAI key required):

```bash
./scripts/test_smoke.sh
```

End-to-end (requires `OPENAI_API_KEY`):

```bash
export OPENAI_API_KEY="..."
./scripts/test_e2e.sh
```

### 2) Generate rules SKOS JSON-LD

Fetch the latest rules and regenerate `resources/mtg/rules.skos.jsonld`:

```bash
docker compose run --rm mtg-ontology rules refresh \
  --rules-dir resources/MagicCompRules \
  --output resources/mtg/rules.skos.jsonld
```

### 3) Upsert rules into Qdrant (OpenAI embeddings)

```bash
export OPENAI_API_KEY="..."
docker compose run --rm mtg-ontology rules upsert-qdrant \
  --input-jsonld resources/mtg/rules.skos.jsonld \
  --collection mtg_rules
```

### 4) Snapshot the collection (optional)

```bash
docker compose run --rm mtg-ontology qdrant snapshot-collection \
  --collection mtg_rules \
  --output-dir resources/qdrant-snapshots
```

## Python (No Docker)

If you prefer running the CLI directly:

```bash
python3 -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
```

Then run the same commands shown above, but without `docker compose run`:

```bash
python -m mtg_ontology.cli rules refresh \
  --rules-dir resources/MagicCompRules \
  --output resources/mtg/rules.skos.jsonld
```

## Scryfall -> JSON-LD

A pinned bulk file is kept under `resources/scryfall/` as gzip (`*.gz`) to keep the repo manageable.
The uncompressed `oracle-cards-*.json` / `oracle-cards-*.jsonld` files are intentionally gitignored.

Convert it to JSON-LD:

```bash
python -m mtg_ontology.cli scryfall export-jsonld \
  --input-json resources/scryfall/oracle-cards-20260208220524.json.gz \
  --output resources/scryfall/oracle-cards-20260208220524.jsonld.gz
```

## Targeting An Existing Qdrant

All Qdrant commands accept:

- `--qdrant-url` (or `QDRANT_URL`, default `http://localhost:6333`)
- `--qdrant-api-key` (or `QDRANT_API_KEY`)

## Release Artifacts (Tags)

Pushing a tag like `rules-20260116` runs `.github/workflows/release.yml`:

1. Fetch + regenerate `resources/mtg/rules.skos.jsonld` for the tagged `YYYYMMDD` rules release
2. Upsert into a dockerized Qdrant using `secrets.OPENAI_API_KEY` (required)
3. Snapshot the tag-specific collection `mtg_rules_<YYYYMMDD>`
4. Upsert the pinned Scryfall oracle-cards JSON-LD into `mtg_oracle_cards_<ORACLE_TOKEN>`
5. Snapshot the oracle-cards collection
6. Upload the JSON-LD + snapshot files as GitHub release assets

Oracle-cards embeddings are incremental: the release workflow will restore the most recent oracle-cards snapshot (when available) and only embed cards whose embedding text is missing or has changed.

## Data Sources

- Comprehensive Rules TXT files are fetched from Wizards and cached under `resources/MagicCompRules/`.
- The pinned Scryfall oracle-cards bulk file lives under `resources/scryfall/` as gzip.

If you redistribute data from either source, make sure you follow the terms that apply to it.

## Clojure Consumption (Resources Only)

This repo ships `resources/` on the classpath via `deps.edn`. Example:

```clojure
(slurp (clojure.java.io/resource "mtg/rules.skos.jsonld"))
```

## License

See `LICENSE`.

## Disclaimer

This is an unofficial community project. It is not produced by, endorsed by, or affiliated with Wizards of the Coast or Scryfall.
Magic: The Gathering and related trademarks are the property of Wizards of the Coast.
