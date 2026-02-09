# Scryfall Bulk Data (Pinned)

This folder contains a pinned snapshot of Scryfall's `oracle-cards` bulk dataset, stored as gzip
to keep the repo size manageable.

Pinned snapshot:

- `oracle-cards-20260208220524.json.gz`: raw Scryfall bulk JSON
- `oracle-cards-20260208220524.jsonld.gz`: the same data exported as JSON-LD (1:1 mapping; see `docs/SCRYFALL_JSONLD_MODEL.md`)

Notes:

- The uncompressed `oracle-cards-*.json` / `oracle-cards-*.jsonld` files are intentionally gitignored.
- `sample-cards.json` is a tiny fixture used by CI.
- You can regenerate the JSON-LD from the pinned JSON with:

```bash
python -m mtg_ontology.cli scryfall export-jsonld \
  --input-json resources/scryfall/oracle-cards-20260208220524.json.gz \
  --output resources/scryfall/oracle-cards-20260208220524.jsonld.gz
```
