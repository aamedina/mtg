# Contributing

Thanks for taking a look.

This repo is intentionally small: a Python CLI plus a handful of generated artifacts under `resources/`.

## Development Setup

- Python 3.11+ (3.12 recommended)
- Docker (for Qdrant)

```bash
python3 -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
```

## Local Checks

Parse + validate the pinned rules file:

```bash
python -m mtg_ontology.cli rules parse \
  --input-file resources/MagicCompRules/20260116.txt \
  --release-token 20260116 \
  --source-url "https://media.wizards.com/2026/downloads/MagicCompRules%2020260116.txt" \
  --output /tmp/rules.skos.jsonld

python -m mtg_ontology.cli rules validate --input-jsonld /tmp/rules.skos.jsonld
```

## Data Files

- Keep committed data under `resources/`.
- Avoid committing large uncompressed Scryfall exports. Use gzip (`*.gz`) instead.

