---
name: mtg-qdrant-expert
description: "Answer Magic: The Gathering rules and card questions by retrieving grounded context from this repo's Qdrant collections (Comprehensive Rules SKOS + Scryfall oracle-cards JSON-LD). Use when you need to: (1) cite exact Comprehensive Rules numbers and quote relevant definitions, (2) find and evaluate cards using semantic search plus fast payload filters, (3) build/refresh local Qdrant collections via the provided Dockerized pipeline, or (4) guide an agent to query these collections efficiently and safely (no oracle-text hallucinations)."
---

# MTG Qdrant Expert

## Working Contract

- Retrieve from Qdrant before answering.
- Quote exact rule text from payload `definition` and cite the rule `notation`.
- Quote exact card oracle text from payload `oracle_text`. Do not paraphrase oracle text unless you also show the exact text.
- For multi-faced cards, `oracle_text` may contain multiple face texts joined with `//`. If you need per-face precision, pull `jsonld.card_faces[*].oracle_text`.
- If retrieval is weak, tighten filters and rerun instead of guessing.

## Quick Setup (Local)

Prefer restoring snapshots from a release (fast, no embedding cost):

```bash
./scripts/bootstrap_qdrant_from_release.sh rules-20260116
```

Then set environment variables (examples):

```bash
export QDRANT_URL="http://localhost:6333"
export MTG_RULES_COLLECTION="mtg_rules_20260116"
export MTG_CARDS_COLLECTION="mtg_oracle_cards_20260208220524"
export OPENAI_API_KEY="..."
```

## Retrieval Workflow

### 1) Decide What To Search

- If the user asks about timing/priority/zones/keywords/game actions: search **rules**.
- If the user asks for a card, a category of cards, upgrades/cuts, or comparisons: search **cards**.
- If the user asks about an interaction involving named cards: search **cards** for oracle text, then search **rules** for the governing mechanics.

### 2) Retrieve Context

Use the repo helper (prints JSON you can reason over):

```bash
. .venv/bin/activate
./scripts/mtg_retrieve.py "how does ward work?"
```

Notes:

- The script uses `MTG_RULES_COLLECTION` and `MTG_CARDS_COLLECTION` if set.
- For card queries, it infers a few high-signal filters (mana value, color identity, type line, and sometimes oracle-text tokens like `damage`).

If you need more control, query Qdrant directly (see `docs/QDRANT_QUERY_GUIDE.md`).

### 3) Answer With Receipts

Rules answers:

- State the rule number(s) you are relying on.
- Quote the relevant `definition` text.
- If multiple rules apply, list them in a small “Rules Cited” block.

Card answers:

- Provide `name`, `mana_cost`, `type_line`.
- Quote `oracle_text` exactly (or the relevant excerpt).
- If recommending cards, give 3-8 options and explain tradeoffs (mana value, speed, card type, color identity, commander legality).

## High-Value Query Patterns

### Rules: Narrow To Numbered Rules Only

Filter `kind=rule` to avoid the results being diluted by chapters/sections/glossary.

### Rules: Exact Lookup By Notation

If the user gives a rule number like `614.1a`, do an exact `notation` lookup (scroll filter) instead of vector search.

### Cards: Hybrid Retrieval (Vector + Filters)

Use vector search for semantics, then constrain with payload filters for:

- `cmc` (mana value)
- `color_identity`
- `type_line` (full-text)
- `oracle_text` (full-text)
- `set` and `collector_number` (exact printing lookups)

### Cards: Lookup A Named Card Reliably

Use the `name_ft` full-text index for case-insensitive token matching on card names.

## Failure Modes (Avoid These)

- Do not invent oracle text. If you didn’t retrieve it, say so and retrieve again.
- Do not “rule-lawyer” from memory. Always cite the Comprehensive Rules when answering rules questions.
- Do not over-filter early. If you get zero hits, relax filters and rerun.
