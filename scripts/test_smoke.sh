#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$(dirname "${BASH_SOURCE[0]}")")"

echo "[smoke] build docker image"
docker compose build mtg-ontology >/dev/null

echo "[smoke] rules parse + validate (pinned fixture)"
docker compose run --rm --no-deps --entrypoint bash mtg-ontology -lc '
  set -euo pipefail
  python -m mtg_ontology.cli rules parse \
    --input-file resources/MagicCompRules/20260116.txt \
    --release-token 20260116 \
    --source-url "https://media.wizards.com/2026/downloads/MagicCompRules%2020260116.txt" \
    --output /tmp/rules.skos.jsonld
  python -m mtg_ontology.cli rules validate --input-jsonld /tmp/rules.skos.jsonld
'

echo "[smoke] scryfall json -> json-ld (small fixture, gzip output)"
docker compose run --rm --no-deps --entrypoint bash mtg-ontology -lc '
  set -euo pipefail
  python -m mtg_ontology.cli scryfall export-jsonld \
    --input-json resources/scryfall/sample-cards.json \
    --output /tmp/sample-cards.jsonld.gz
  python - <<PY
import gzip, json
doc = json.loads(gzip.open("/tmp/sample-cards.jsonld.gz", "rt", encoding="utf-8").read())
assert "@context" in doc
assert isinstance(doc.get("@graph"), list) and len(doc["@graph"]) == 2
assert all(node.get("@id") for node in doc["@graph"])
print("ok")
PY
'

echo "[smoke] ok"

