# Repo Map

## Top Level

- `mtg_ontology/`: Python package (the implementation)
- `scripts/`: local test suite and GitHub Actions helpers
- `skills/`: optional Codex skills (agent instructions for querying rules + cards in Qdrant)
- `resources/`: committed artifacts + generated outputs intended for reuse (also the only Clojure classpath path)
- `docker-compose.yml`: local reproducible Qdrant + a Dockerized CLI runner (`mtg-ontology`)
- `.github/workflows/ci.yml`: smoke checks for PRs/pushes (no OpenAI key required)
- `.github/workflows/release.yml`: tag-driven release workflow (upsert + snapshot + upload assets)
- `deps.edn`: keeps this repo consumable as a Clojure dependency via `resources/`
- `docs/RULES_SKOS_MODEL.md`: rules SKOS modeling documentation
- `docs/SCRYFALL_JSONLD_MODEL.md`: Scryfall JSON-LD modeling documentation
- `docs/QDRANT_QUERY_GUIDE.md`: query patterns + payload fields for agents using Qdrant
- `docs/CODEX_AGENT_GUIDE.md`: how to use this repo with Codex (skill + local Qdrant)
- `.env.example`: environment variables used by the CLI and docker-compose

## `resources/` Layout

### Comprehensive Rules

- `resources/MagicCompRules/latest.json`: discovery/download metadata for the most recent rules
- `resources/MagicCompRules/<YYYYMMDD>.txt`: cached rules text files (downloaded from Wizards)
- `resources/mtg/rules.skos.jsonld`: SKOS-flavored JSON-LD parsed from the latest rules text
- `resources/mtg/rules.context.jsonld`: standalone JSON-LD context for the rules export

### Scryfall

- `resources/scryfall/oracle-cards-20260208220524.json.gz`: pinned Scryfall bulk oracle-cards JSON (raw, gzip)
- `resources/scryfall/oracle-cards-20260208220524.jsonld.gz`: the same data converted to JSON-LD (gzip; preserves all Scryfall fields)
- `resources/scryfall/latest-oracle.json`: metadata about the latest bulk download performed by the CLI
- `resources/scryfall/context.jsonld`: standalone JSON-LD context used by the Scryfall export

Note: the uncompressed `oracle-cards-*.json` / `oracle-cards-*.jsonld` files are intentionally gitignored.

### Qdrant

- `resources/qdrant-snapshots/`: snapshots downloaded via CLI (gitignored)

## `mtg_ontology/` Modules

- `mtg_ontology/cli.py`: CLI entrypoint (`python -m mtg_ontology.cli ...`)
- `mtg_ontology/rules_fetch.py`: discovers latest Wizards rules TXT and downloads it
- `mtg_ontology/rules_parser.py`: parses rules + glossary into SKOS JSON-LD
- `mtg_ontology/ingest.py`: JSON-LD ingestion helpers (select nodes + build embedding text/payload)
- `mtg_ontology/scryfall.py`: Scryfall bulk download helpers + filtering
- `mtg_ontology/card_jsonld.py`: converts Scryfall card JSON to JSON-LD (keeps full Scryfall schema)
- `mtg_ontology/qdrant_store.py`: create collection + upsert points using embeddings
- `mtg_ontology/embeddings.py`: OpenAI embeddings client (requires `OPENAI_API_KEY`)
- `mtg_ontology/qdrant_snapshot.py`: create/download Qdrant snapshots
- `mtg_ontology/validate_rules.py`: rules JSON-LD validation (e.g., dangling references)
