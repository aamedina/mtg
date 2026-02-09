#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$(dirname "${BASH_SOURCE[0]}")")"

tag="${1:-rules-20260116}"
repo="${REPO:-aamedina/mtg}"
out_dir="${OUT_DIR:-resources/qdrant-snapshots}"

echo "[bootstrap] start qdrant"
docker compose up -d qdrant >/dev/null
./scripts/wait_for_qdrant.sh "http://localhost:6333" 60 >/dev/null

echo "[bootstrap] download snapshots for ${repo}@${tag}"
payload="$(python scripts/download_release_snapshots.py --repo "${repo}" --tag "${tag}" --output-dir "${out_dir}")"

echo "${payload}" | python -c 'import json,sys; p=json.load(sys.stdin); print("[bootstrap] downloads:", len(p.get("downloads",[])))'

echo "[bootstrap] recover snapshots into qdrant"
echo "${payload}" | python -c 'import json,sys; p=json.load(sys.stdin); [print(str(d.get("collection")) + "\t" + str(d.get("path"))) for d in p.get("downloads",[])]' | while IFS=$'\t' read -r collection path; do
  docker compose run --rm mtg-ontology qdrant recover-snapshot \
    --collection "${collection}" \
    --snapshot-path "${path}" >/dev/null
done

rules_collection="$(echo "${payload}" | python -c 'import json,sys; p=json.load(sys.stdin); print(next((d.get("collection") for d in p.get("downloads",[]) if d.get("kind")=="rules"), ""))')"
cards_collection="$(echo "${payload}" | python -c 'import json,sys; p=json.load(sys.stdin); print(next((d.get("collection") for d in p.get("downloads",[]) if d.get("kind")=="oracle_cards"), ""))')"

echo "[bootstrap] ensure payload indexes"
docker compose run --rm mtg-ontology qdrant ensure-indexes --collection "${rules_collection}" --schema rules >/dev/null
docker compose run --rm mtg-ontology qdrant ensure-indexes --collection "${cards_collection}" --schema cards >/dev/null

cat <<EOF

[bootstrap] done

Next:

  export MTG_RULES_COLLECTION="${rules_collection}"
  export MTG_CARDS_COLLECTION="${cards_collection}"
  export QDRANT_URL="http://localhost:6333"
  export OPENAI_API_KEY="..."

Try:

  . .venv/bin/activate
  ./scripts/mtg_retrieve.py "how does ward work?"
  ./scripts/mtg_retrieve.py "two mana counterspell"
  ./scripts/mtg_retrieve.py "one mana red instant deal 3 damage"

EOF
