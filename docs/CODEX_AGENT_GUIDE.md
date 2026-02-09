# Codex Agent Guide (Rules + Cards)

This repo is set up so you can:

1. run a local Qdrant (Docker)
2. populate it with embedded *Comprehensive Rules* + *Scryfall oracle-cards*
3. have Codex (or any agent) retrieve grounded context and answer with rule citations and exact oracle text

The fastest path is to restore prebuilt Qdrant snapshots from a GitHub release tag.

## Prereqs

- Docker (for Qdrant)
- Python 3.11+ (recommended) and `pip`
- `OPENAI_API_KEY` in your environment

## 1) Clone + Python Setup

```bash
python3 -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
```

## 2) Start Qdrant

```bash
docker compose up -d qdrant
```

Qdrant should be reachable at `http://localhost:6333`.

## 3) Populate Qdrant

### Option A: Restore From A Release (Recommended)

This avoids embedding the full oracle-cards dataset locally.

```bash
./scripts/bootstrap_qdrant_from_release.sh rules-20260116
```

If you hit GitHub API rate limits while downloading release assets, set a token:

```bash
export GITHUB_TOKEN="..."
```

That script prints the exact `MTG_RULES_COLLECTION` and `MTG_CARDS_COLLECTION` names to export.

### Option B: Build Locally (Costs OpenAI Embeddings)

Rules:

```bash
export OPENAI_API_KEY="..."
docker compose run --rm mtg-ontology rules upsert-qdrant \
  --input-jsonld resources/mtg/rules.skos.jsonld \
  --collection mtg_rules_20260116
```

Cards (pinned oracle dataset):

```bash
export OPENAI_API_KEY="..."
docker compose run --rm mtg-ontology cards upsert-jsonld \
  --input-jsonld resources/scryfall/oracle-cards-20260208220524.jsonld.gz \
  --collection mtg_oracle_cards_20260208220524
```

## 4) Use The Repo Skill In Codex (No Install)

This repo includes a Codex skill checked in at:

- `.agents/skills/mtg-qdrant-expert/`

Codex discovers repo-local skills automatically when you launch Codex inside the repository.
If you don't see the skill after pulling updates, restart Codex.

## 5) Retrieval (What The Agent Should Do)

The skill is “retrieve-first”. You can also run retrieval manually to see what the agent will see:

```bash
. .venv/bin/activate
export QDRANT_URL="http://localhost:6333"
export MTG_RULES_COLLECTION="mtg_rules_20260116"
export MTG_CARDS_COLLECTION="mtg_oracle_cards_20260208220524"

./scripts/mtg_retrieve.py "how does ward work?"
./scripts/mtg_retrieve.py "one mana red instant deal 3 damage"
./scripts/mtg_retrieve.py "CR 614.1a"
```

The script prints JSON containing:

- `rules[]`: candidate rule/glossary hits (with `notation` and `definition`)
- `cards[]`: candidate card hits (with `name`, `type_line`, and `oracle_text`)

## Prompts To Test (Good Coverage)

Rules-only:

- “What are replacement effects? Cite the rule.”
- “When do state-based actions happen? Cite rules.”
- “Explain layers in MTG and how they work. Cite rules.”

Cards-only:

- “Find a one-mana red instant that deals 3 damage. Quote oracle text.”
- “Show me efficient 2-mana counterspells. Quote oracle text.”
- “Find cards that say ‘Whenever you cast an artifact spell’.”

Interaction (cards + rules):

- “If I cast Stomp targeting a creature with ward {2}, what happens? Quote Stomp and cite ward rules.”
- “How do replacement effects interact with damage prevention? Cite rules and give an example card.”

## How To Query Qdrant Directly

See `docs/QDRANT_QUERY_GUIDE.md` for agent-friendly query patterns, including:

- exact rule lookup by `notation`
- semantic search + filter patterns for cards
- `MatchText` lexical constraints on `oracle_text`, `type_line`, and `name_ft`

## Using The Skill Explicitly

If you want to force Codex to use this skill, mention it in your prompt:

- `$mtg-qdrant-expert`
