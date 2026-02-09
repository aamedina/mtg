# Qdrant Query Guide

This repo stores *rules* and *cards* as embedded vectors in Qdrant, with enough payload fields
to support fast filtering and retrieval.

If you are building an agent, the basic pattern is:

1. Embed the user query with the same OpenAI embedding model used at ingest time.
2. Run a vector search in Qdrant.
3. Use payload filters to narrow the search space (format/set/kind/etc).
4. Request a small payload projection for speed, then fetch full JSON-LD only when needed.

## Payload Fields (Rules)

Rules upserts (from `resources/mtg/rules.skos.jsonld`) store each `skos:Concept` as a Qdrant point.

Payload keys you can rely on:

- `kind`: one of `rule`, `glossary_term`, `section`, `chapter`, `rules_root`, `glossary_root`
- `uri`: the concept IRI (same as JSON-LD `@id`)
- `label`: English `skos:prefLabel` (string)
- `definition`: English `skos:definition` (string)
- `notation`: rule/section/chapter numbering when present (string)
- `broader`: parent concept IRI (string; empty if not present)
- `references`: list of referenced concept IRIs (from `dcterms:references`)
- `scheme`: scheme IRI (`.../scheme/<YYYYMMDD>`)
- `rules_token`: `YYYYMMDD` extracted from `scheme`
- `jsonld`: the full JSON-LD node (verbatim)

Embedding metadata (useful for incremental re-embeds):

- `embedding_text_sha256`: SHA-256 of the exact text that was embedded (built by `rules_text_builder`)

Indexes created automatically by the CLI:

- keyword indexes: `kind`, `uri`, `label`, `notation`, `broader`, `references`, `scheme`, `rules_token`

## Payload Fields (Cards)

Card upserts store each Scryfall card JSON object (with `@id`) as a Qdrant point.

Payload keys you can rely on:

- `kind`: always `card`
- `uri`: the Scryfall API URI (also JSON-LD `@id`)
- `id`, `oracle_id`
- `name`, `set`, `collector_number`, `lang`, `layout`, `rarity`
- `mana_cost`, `type_line`, `oracle_text`
- `cmc` (number), `colors` (array), `color_identity` (array), `keywords` (array)
- `name_ft`: derived from `name` (full-text indexed, case-insensitive token lookup)
- `jsonld`: the full 1:1 Scryfall JSON-LD node

What gets embedded (important):

- Embeddings are computed from a small, semantic text built from `name`, `type_line`, `oracle_text`, and `keywords`.
- The full `jsonld` payload is stored for retrieval, but it is **not** what gets embedded.

Notes:

- For multi-faced cards (transform/adventure/split), Scryfall often omits top-level `oracle_text`.
  In Qdrant payload, `oracle_text` is filled from `card_faces[*].oracle_text` when needed, joined with `//`.
  The original face structure is always preserved under `jsonld.card_faces`.

Embedding metadata (useful for incremental re-embeds):

- `embedding_text_sha256` (built by `cards_text_builder`)

Indexes created automatically by the CLI:

- keyword indexes: `kind`, `uri`, `id`, `oracle_id`, `name`, `set`, `collector_number`, `lang`, `layout`, `rarity`, `colors`, `color_identity`, `keywords`
- float index: `cmc`
- full-text indexes (on disk): `oracle_text`, `type_line`
- full-text indexes (on disk): `name_ft` (a case-insensitive, tokenized copy of `name`)

### Why These Indexes?

- **Keyword indexes** make exact filters fast (`set=mh3`, `color_identity` contains `U`, etc.).
- **Float index** on `cmc` makes mana value range filters fast.
- **Full-text indexes** make token-based constraints fast (especially important for `oracle_text` on a large collection).
  - These are set to `on_disk=true` to keep RAM usage predictable. If you run Qdrant with plenty of memory and want
    faster text filters, you can switch them to in-memory indexes by setting `on_disk=false` and reindexing.

## Python Examples (qdrant-client)

These examples assume:

- `OPENAI_API_KEY` is set
- `QDRANT_URL` is set (or default `http://localhost:6333`)
- you used the same embedding model for querying as for ingestion (default: `text-embedding-3-small`)

```python
import os
from qdrant_client import QdrantClient
from qdrant_client.http import models

from mtg_ontology.embeddings import OpenAIEmbedder, OpenAIEmbeddingConfig, resolve_openai_api_key

client = QdrantClient(url=os.getenv("QDRANT_URL", "http://localhost:6333"), timeout=30)
embedder = OpenAIEmbedder(OpenAIEmbeddingConfig(api_key=resolve_openai_api_key()))

def vsearch(collection: str, query: str, *, flt: models.Filter | None = None, limit: int = 10):
    vec = embedder.embed([query])[0]
    resp = client.query_points(
        collection_name=collection,
        query=vec,
        query_filter=flt,
        limit=limit,
        # Avoid returning the full JSON-LD unless you really need it.
        with_payload=models.PayloadSelectorInclude(
            include=[
                "uri",
                "kind",
                # Rules
                "label",
                "notation",
                "definition",
                # Cards
                "name",
                "set",
                "collector_number",
                "mana_cost",
                "type_line",
                "oracle_text",
            ]
        ),
        with_vectors=False,
    )
    return resp.points
```

### Rules: semantic search in numbered rules only

```python
flt = models.Filter(
    must=[
        models.FieldCondition(key="kind", match=models.MatchValue(value="rule")),
    ]
)
hits = vsearch("mtg_rules_20260116", "replacement effect for damage prevention", flt=flt, limit=5)
```

### Rules: get a rule by notation (exact)

```python
records, _ = client.scroll(
    collection_name="mtg_rules_20260116",
    scroll_filter=models.Filter(
        must=[models.FieldCondition(key="notation", match=models.MatchValue(value="614.1a"))]
    ),
    limit=10,
    with_payload=models.PayloadSelectorInclude(include=["uri", "label", "definition", "notation"]),
    with_vectors=False,
)
```

### Rules: list glossary terms (paged)

```python
offset = None
while True:
    batch, offset = client.scroll(
        collection_name="mtg_rules_20260116",
        scroll_filter=models.Filter(
            must=[models.FieldCondition(key="kind", match=models.MatchValue(value="glossary_term"))]
        ),
        limit=256,
        offset=offset,
        with_payload=models.PayloadSelectorInclude(include=["label", "uri"]),
        with_vectors=False,
    )
    if not batch:
        break
    for r in batch:
        print(r.payload["label"], r.payload["uri"])
```

### Rules: find rules that reference another rule

This uses the `references` payload field (extracted from `dcterms:references`).

```python
# 1) Resolve the referenced rule URI (notation -> uri)
records, _ = client.scroll(
    collection_name="mtg_rules_20260116",
    scroll_filter=models.Filter(
        must=[models.FieldCondition(key="notation", match=models.MatchValue(value="601.2b"))]
    ),
    limit=1,
    with_payload=models.PayloadSelectorInclude(include=["uri"]),
    with_vectors=False,
)
target_uri = records[0].payload["uri"]

# 2) Find rules that cite it
flt = models.Filter(
    must=[
        models.FieldCondition(key="kind", match=models.MatchValue(value="rule")),
        models.FieldCondition(key="references", match=models.MatchAny(any=[target_uri])),
    ]
)
hits = vsearch("mtg_rules_20260116", "cast a spell", flt=flt, limit=10)
```

### Cards: semantic search within a set

```python
flt = models.Filter(
    must=[
        models.FieldCondition(key="set", match=models.MatchValue(value="mh3")),
    ]
)
hits = vsearch("mtg_cards_mh3", "search your library for a land", flt=flt, limit=10)
```

### Cards: lexical filter on oracle text (full-text index)

This uses Qdrant's text index to filter by tokens in `oracle_text`. It is best used as a *constraint*
for vector search (hybrid), not as a standalone ranking method.

```python
flt = models.Filter(
    must=[
        models.FieldCondition(key="oracle_text", match=models.MatchText(text="counter target spell")),
    ]
)
hits = vsearch("mtg_oracle_cards_20260208220524", "two mana counterspell", flt=flt, limit=10)
```

Note: `MatchText` is token-based. If you need an exact phrase match, post-filter on the returned
payload (`"Counter target spell" in oracle_text`) after retrieval.

### Cards: lookup by (approximate) name (full-text index)

If you have a user-provided card name that might not match Scryfall casing/punctuation exactly,
use `name_ft`:

```python
flt = models.Filter(
    must=[
        models.FieldCondition(key="name_ft", match=models.MatchText(text="panharmonicon")),
    ]
)
records, _ = client.scroll(
    collection_name="mtg_oracle_cards_20260208220524",
    scroll_filter=flt,
    limit=5,
    with_payload=models.PayloadSelectorInclude(include=["name", "uri", "oracle_text"]),
    with_vectors=False,
)
```

### Cards: exact lookup by set + collector number

```python
flt = models.Filter(
    must=[
        models.FieldCondition(key="set", match=models.MatchValue(value="m20")),
        models.FieldCondition(key="collector_number", match=models.MatchValue(value="132")),
    ]
)
records, _ = client.scroll(
    collection_name="mtg_cards_m20",
    scroll_filter=flt,
    limit=5,
    with_payload=models.PayloadSelectorInclude(include=["name", "uri", "set", "collector_number"]),
    with_vectors=False,
)
```

### Cards: filter by mana value (cmc) and color identity

```python
flt = models.Filter(
    must=[
        models.FieldCondition(key="cmc", range=models.Range(lte=2.0)),
        models.FieldCondition(key="color_identity", match=models.MatchAny(any=["U"])),
    ]
)
hits = vsearch("mtg_cards_commander", "counter target spell", flt=flt, limit=10)
```

## Notes For Agent Builders

- Keep `with_payload` small. The stored `jsonld` payload can be large, especially for cards.
- If you need the full JSON-LD, request it explicitly:
  - `with_payload=models.PayloadSelectorInclude(include=["jsonld"])`
- If you change the embedding model, you must ingest and query with the same model (vector size must match).
