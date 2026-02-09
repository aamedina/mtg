#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$(dirname "${BASH_SOURCE[0]}")")"

if [[ -z "${OPENAI_API_KEY:-}" ]]; then
  echo "OPENAI_API_KEY is required for the end-to-end test (embeddings + Qdrant upserts)." >&2
  echo "Tip: run ./scripts/test_smoke.sh for the offline smoke suite." >&2
  exit 2
fi

RULES_TOKEN="${RULES_TOKEN:-20260116}"
CARDS_QUERY="${CARDS_QUERY:-set:m20 lang:en}"
CARDS_MAX="${CARDS_MAX:-30}"
PREFIX="${PREFIX:-local}"

TS="$(date +%Y%m%d%H%M%S)"
RULES_COLLECTION="${PREFIX}_rules_${RULES_TOKEN}_${TS}"
CARDS_COLLECTION="${PREFIX}_cards_${TS}"
RESTORED_COLLECTION="${RULES_COLLECTION}_restored"
CARDS_SAMPLE_COLLECTION="${CARDS_COLLECTION}_sample"
CARDS_SAMPLE_RESTORED_COLLECTION="${CARDS_SAMPLE_COLLECTION}_restored"

echo "[e2e] start qdrant"
docker compose up -d qdrant >/dev/null
# After Qdrant upgrades, the first start may need extra time for internal migrations.
./scripts/wait_for_qdrant.sh "http://localhost:6333" 180 >/dev/null

echo "[e2e] build docker image"
docker compose build mtg-ontology >/dev/null

echo "[e2e] rules parse + validate + determinism check + qdrant upsert: ${RULES_COLLECTION}"
docker compose run --rm --no-deps \
  -e "RULES_COLLECTION=${RULES_COLLECTION}" \
  --entrypoint bash mtg-ontology -lc "
    set -euo pipefail
    token='${RULES_TOKEN}'
    src_url=\"https://media.wizards.com/\${token:0:4}/downloads/MagicCompRules%20\${token}.txt\"
    # Parse directly from the pinned fixture
    python -m mtg_ontology.cli rules parse \
      --input-file \"resources/MagicCompRules/\${token}.txt\" \
      --release-token \"\${token}\" \
      --source-url \"\${src_url}\" \
      --output /tmp/rules.parse.jsonld
    python -m mtg_ontology.cli rules validate --input-jsonld /tmp/rules.parse.jsonld

    # Also exercise the \"refresh\" workflow (what GitHub release tags run), but in a temp dir
    tmp_rules_dir=\$(mktemp -d)
    cp -f \"resources/MagicCompRules/\${token}.txt\" \"\${tmp_rules_dir}/\${token}.txt\"
    python -m mtg_ontology.cli rules refresh \
      --release-token \"\${token}\" \
      --rules-dir \"\${tmp_rules_dir}\" \
      --output /tmp/rules.refresh.jsonld
    python -m mtg_ontology.cli rules validate --input-jsonld /tmp/rules.refresh.jsonld

    # \"parse\" and \"refresh\" should be identical for the same input file.
    python - <<'PY'
import hashlib
from pathlib import Path
def sha256(path: str) -> str:
    return hashlib.sha256(Path(path).read_bytes()).hexdigest()
a = sha256('/tmp/rules.parse.jsonld')
b = sha256('/tmp/rules.refresh.jsonld')
if a != b:
    raise SystemExit('rules parse != rules refresh for the same input/token; expected identical JSON-LD')
print('parse==refresh: ok')
PY

    # Ensure the committed artifact is deterministic for this token.
    python - <<'PY'
import hashlib
from pathlib import Path
def sha256(path: str) -> str:
    return hashlib.sha256(Path(path).read_bytes()).hexdigest()
a = sha256('/tmp/rules.refresh.jsonld')
b = sha256('resources/mtg/rules.skos.jsonld')
if a != b:
    raise SystemExit('rules.skos.jsonld is not deterministic for this token; generated output differs from committed file')
print('deterministic: ok')
PY
    python -m mtg_ontology.cli rules upsert-qdrant \
      --input-jsonld /tmp/rules.refresh.jsonld \
      --collection \"\${RULES_COLLECTION}\" \
      --embedding-model text-embedding-3-small \
      --recreate
  " >/dev/null

echo "[e2e] upsert small sample cards (offline fixture): ${CARDS_COLLECTION}_sample"
docker compose run --rm --entrypoint bash mtg-ontology -lc "
  set -euo pipefail
  mkdir -p dist
  python -m mtg_ontology.cli cards build-collection \
    --input-json resources/scryfall/sample-cards.json \
    --collection \"${CARDS_SAMPLE_COLLECTION}\" \
    --output-jsonld dist/e2e-sample.cards.jsonld.gz \
    --embedding-model text-embedding-3-small \
    --recreate >/dev/null

  # Simulate upgrading an older snapshot:
  # - legacy per-point embedding metadata fields (to be cleaned)
  # - missing derived lookup field name_ft (to be backfilled)
  python - <<'PY'
from qdrant_client import QdrantClient
from qdrant_client.http import models

client = QdrantClient(url='http://qdrant:6333', timeout=30)
collection = '${CARDS_SAMPLE_COLLECTION}'

points, _ = client.scroll(collection_name=collection, limit=10, with_payload=False, with_vectors=False)
ids = [p.id for p in points]
assert len(ids) == 2, ids

client.set_payload(
    collection_name=collection,
    payload={'embedding_model': 'legacy', 'embedding_dimensions': 1234},
    points=ids,
    wait=True,
)
client.delete_payload(collection_name=collection, keys=['name_ft'], points=ids, wait=True)

# Ensure the test preconditions actually hold.
p = client.retrieve(collection_name=collection, ids=ids, with_payload=True, with_vectors=False)
assert any('embedding_model' in (pt.payload or {}) for pt in p)
assert all('name_ft' not in (pt.payload or {}) for pt in p)
print('preconditions: ok')
PY

  # Incremental check: running the same upsert again should skip re-embedding unchanged cards.
  python -m mtg_ontology.cli cards upsert-jsonld \
    --input-jsonld dist/e2e-sample.cards.jsonld.gz \
    --collection \"${CARDS_SAMPLE_COLLECTION}\" \
    --embedding-model text-embedding-3-small >/tmp/e2e.sample.upsert.json

  python - <<'PY'
import json
from pathlib import Path
data = json.loads(Path('/tmp/e2e.sample.upsert.json').read_text(encoding='utf-8'))
assert data['upserted'] == 0, data
assert data['skipped'] == 2, data
assert data['payload_patched'] == 2, data
assert data['payload_cleaned'] == 2, data
print('incremental: ok')
PY

  python - <<'PY'
from qdrant_client import QdrantClient

client = QdrantClient(url='http://qdrant:6333', timeout=30)
collection = '${CARDS_SAMPLE_COLLECTION}'
points, _ = client.scroll(collection_name=collection, limit=10, with_payload=True, with_vectors=False)
assert len(points) == 2
for pt in points:
    payload = pt.payload or {}
    assert payload.get('name_ft'), payload
    assert 'embedding_model' not in payload, payload
    assert 'embedding_dimensions' not in payload, payload
print('payload backfill+cleanup: ok')
PY
" >/dev/null

echo "[e2e] upsert real cards from scryfall api: ${CARDS_COLLECTION}"
docker compose run --rm mtg-ontology cards build-collection \
  --query "${CARDS_QUERY}" \
  --max-cards "${CARDS_MAX}" \
  --collection "${CARDS_COLLECTION}" \
  --output-jsonld /tmp/cards.cards.jsonld.gz \
  --embedding-model text-embedding-3-small \
  --recreate >/dev/null

echo "[e2e] run agent-style queries (vector + filters + exact lookups)"
docker compose run --rm --no-deps \
  -e "RULES_COLLECTION=${RULES_COLLECTION}" \
  -e "CARDS_COLLECTION=${CARDS_COLLECTION}" \
  -e "CARDS_SAMPLE_COLLECTION=${CARDS_SAMPLE_COLLECTION}" \
  --entrypoint bash mtg-ontology -lc "python - <<'PY'
import os
from qdrant_client import QdrantClient
from qdrant_client.http import models

from mtg_ontology.embeddings import OpenAIEmbedder, OpenAIEmbeddingConfig, resolve_openai_api_key

rules_collection = os.environ['RULES_COLLECTION']
cards_collection = os.environ['CARDS_COLLECTION']
cards_sample_collection = os.environ['CARDS_SAMPLE_COLLECTION']

client = QdrantClient(url=os.getenv('QDRANT_URL', 'http://qdrant:6333'), timeout=30)
embedder = OpenAIEmbedder(OpenAIEmbeddingConfig(api_key=resolve_openai_api_key(), model=os.getenv('OPENAI_EMBEDDING_MODEL','text-embedding-3-small')))

def embed(q: str):
    return embedder.embed([q])[0]

def qpoints(collection: str, q: str, flt=None, limit=5, include=None):
    resp = client.query_points(
        collection_name=collection,
        query=embed(q),
        query_filter=flt,
        limit=limit,
        with_payload=models.PayloadSelectorInclude(include=include or ['uri','kind','label','notation','name','set','collector_number','mana_cost','type_line','oracle_text']),
        with_vectors=False,
    )
    return resp.points

# Ensure payload indexes exist (schema shows indexed fields)
rules_info = client.get_collection(rules_collection)
cards_info = client.get_collection(cards_collection)
cards_sample_info = client.get_collection(cards_sample_collection)
for key in ['kind','uri','label','notation','broader','references','scheme','rules_token']:
    assert key in (rules_info.payload_schema or {}), f\"missing rules payload index: {key}\"
for key in ['kind','uri','id','oracle_id','name','name_ft','set','collector_number','lang','layout','rarity','cmc','colors','color_identity','keywords']:
    assert key in (cards_info.payload_schema or {}), f\"missing cards payload index: {key}\"
for key in ['oracle_text','type_line']:
    assert key in (cards_info.payload_schema or {}), f\"missing cards full-text payload index: {key}\"
for key in ['oracle_text','type_line','name_ft']:
    assert key in (cards_sample_info.payload_schema or {}), f\"missing sample cards text index: {key}\"

# Rules semantic search (kind=rule)
flt = models.Filter(must=[models.FieldCondition(key='kind', match=models.MatchValue(value='rule'))])
hits = qpoints(rules_collection, 'replacement effect instead', flt=flt, limit=5, include=['notation','label','uri'])
assert hits, 'expected semantic rule hits'
assert any(h.payload.get('notation') for h in hits), 'expected notation in rule hits'

# Rules exact lookup by notation
records, _ = client.scroll(
    collection_name=rules_collection,
    scroll_filter=models.Filter(must=[models.FieldCondition(key='notation', match=models.MatchValue(value='614.1a'))]),
    limit=2,
    with_payload=models.PayloadSelectorInclude(include=['notation','definition','uri']),
    with_vectors=False,
)
assert len(records) == 1, f\"expected 1 record for notation=614.1a, got {len(records)}\"

# Rules cross-reference filter: find rules that reference 601.2b
target_uri = 'https://wikipunk.net/mtg/rules/rule/601_2b'
refd, _ = client.scroll(
    collection_name=rules_collection,
    scroll_filter=models.Filter(
        must=[
            models.FieldCondition(key='kind', match=models.MatchValue(value='rule')),
            models.FieldCondition(key='references', match=models.MatchAny(any=[target_uri])),
        ]
    ),
    limit=5,
    with_payload=models.PayloadSelectorInclude(include=['notation','references']),
    with_vectors=False,
)
assert refd, 'expected at least one rule that references 601.2b'

# Cards semantic search
hits = qpoints(cards_collection, 'counter target spell', limit=5, include=['name','set','collector_number','oracle_text','uri'])
assert hits, 'expected semantic card hits'

# Cards exact lookup by id (get one card, then query it back)
sample, _ = client.scroll(
    collection_name=cards_collection,
    limit=1,
    with_payload=models.PayloadSelectorInclude(include=['id','name']),
    with_vectors=False,
)
assert sample and sample[0].payload.get('id'), 'expected at least one card with id'
card_id = sample[0].payload['id']
back, _ = client.scroll(
    collection_name=cards_collection,
    scroll_filter=models.Filter(must=[models.FieldCondition(key='id', match=models.MatchValue(value=card_id))]),
    limit=5,
    with_payload=models.PayloadSelectorInclude(include=['id','name','uri']),
    with_vectors=False,
)
assert len(back) >= 1 and back[0].payload.get('id') == card_id

# Cards lexical lookup (sample collection): MatchText on name_ft + oracle_text
records, _ = client.scroll(
    collection_name=cards_sample_collection,
    scroll_filter=models.Filter(must=[models.FieldCondition(key='name_ft', match=models.MatchText(text='sample'))]),
    limit=10,
    with_payload=models.PayloadSelectorInclude(include=['name']),
    with_vectors=False,
)
assert len(records) == 2, f\"expected 2 sample cards via name_ft, got {len(records)}\"

records, _ = client.scroll(
    collection_name=cards_sample_collection,
    scroll_filter=models.Filter(must=[models.FieldCondition(key='oracle_text', match=models.MatchText(text='fixture'))]),
    limit=10,
    with_payload=models.PayloadSelectorInclude(include=['name','oracle_text']),
    with_vectors=False,
)
assert records, \"expected at least one sample card via oracle_text MatchText\"

print('ok')
PY" >/dev/null

echo "[e2e] snapshot rules collection (indexes must be included)"
docker compose run --rm mtg-ontology qdrant snapshot-collection \
  --collection "${RULES_COLLECTION}" \
  --output-dir resources/qdrant-snapshots >/dev/null

snap="$(ls -1t resources/qdrant-snapshots/${RULES_COLLECTION}--*.snapshot | head -n 1)"
if [[ -z "${snap}" ]]; then
  echo "snapshot not found for ${RULES_COLLECTION}" >&2
  exit 1
fi

echo "[e2e] recover snapshot into ${RESTORED_COLLECTION}"
docker compose run --rm mtg-ontology qdrant recover-snapshot \
  --collection "${RESTORED_COLLECTION}" \
  --snapshot-path "${snap}" >/dev/null

echo "[e2e] verify restored collection has points + payload indexes"
docker compose run --rm --no-deps \
  -e "RULES_COLLECTION=${RULES_COLLECTION}" \
  -e "RESTORED_COLLECTION=${RESTORED_COLLECTION}" \
  --entrypoint bash mtg-ontology -lc "python - <<'PY'
import os
from qdrant_client import QdrantClient

client = QdrantClient(url=os.getenv('QDRANT_URL','http://qdrant:6333'), timeout=30)
orig = os.environ['RULES_COLLECTION']
restored = os.environ['RESTORED_COLLECTION']

o = client.get_collection(orig)
r = client.get_collection(restored)
assert o.points_count == r.points_count, (o.points_count, r.points_count)
for key in ['kind','uri','label','notation','broader','references','scheme','rules_token']:
    assert key in (r.payload_schema or {}), f\"missing restored payload index: {key}\"
print('ok')
PY" >/dev/null

echo "[e2e] snapshot sample cards collection (indexes must be included)"
docker compose run --rm mtg-ontology qdrant snapshot-collection \
  --collection "${CARDS_SAMPLE_COLLECTION}" \
  --output-dir resources/qdrant-snapshots >/dev/null

cards_snap="$(ls -1t resources/qdrant-snapshots/${CARDS_SAMPLE_COLLECTION}--*.snapshot | head -n 1)"
if [[ -z "${cards_snap}" ]]; then
  echo "snapshot not found for ${CARDS_SAMPLE_COLLECTION}" >&2
  exit 1
fi

echo "[e2e] recover sample cards snapshot into ${CARDS_SAMPLE_RESTORED_COLLECTION}"
docker compose run --rm mtg-ontology qdrant recover-snapshot \
  --collection "${CARDS_SAMPLE_RESTORED_COLLECTION}" \
  --snapshot-path "${cards_snap}" >/dev/null

echo "[e2e] verify restored sample cards collection has points + payload indexes"
docker compose run --rm --no-deps \
  -e "CARDS_SAMPLE_COLLECTION=${CARDS_SAMPLE_COLLECTION}" \
  -e "CARDS_SAMPLE_RESTORED_COLLECTION=${CARDS_SAMPLE_RESTORED_COLLECTION}" \
  --entrypoint bash mtg-ontology -lc "python - <<'PY'
import os
from qdrant_client import QdrantClient

client = QdrantClient(url=os.getenv('QDRANT_URL','http://qdrant:6333'), timeout=30)
orig = os.environ['CARDS_SAMPLE_COLLECTION']
restored = os.environ['CARDS_SAMPLE_RESTORED_COLLECTION']

o = client.get_collection(orig)
r = client.get_collection(restored)
assert o.points_count == r.points_count, (o.points_count, r.points_count)
for key in ['kind','uri','id','oracle_id','name','name_ft','set','collector_number','lang','layout','rarity','cmc','colors','color_identity','keywords']:
    assert key in (r.payload_schema or {}), f\"missing restored payload index: {key}\"
print('ok')
PY" >/dev/null

echo "[e2e] ok"
