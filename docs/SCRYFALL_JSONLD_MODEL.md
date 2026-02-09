# Scryfall JSON-LD Model

This repo exports Scryfall card JSON as JSON-LD with a **1:1 mapping** to the Scryfall JSON structure.

Primary outputs:

- Raw bulk file (pinned example, gzip): `resources/scryfall/oracle-cards-20260208220524.json.gz`
- JSON-LD export (gzip): `resources/scryfall/oracle-cards-20260208220524.jsonld.gz`

## Goals

- Preserve Scryfall JSON keys and nesting 1:1 (no renaming like `scryfall:name`).
- Provide JSON-LD semantics via a single vocabulary base (`@vocab`).
- Add only a stable JSON-LD identity (`@id`) per card.

## Vocabulary / IRIs

The JSON-LD export sets:

- `@vocab = "https://wikipunk.net/scryfall/"`

That means each JSON key maps to an RDF predicate IRI by simple concatenation.

Example mappings:

- `name` -> `https://wikipunk.net/scryfall/name`
- `oracle_text` -> `https://wikipunk.net/scryfall/oracle_text`
- `legalities.standard` -> `https://wikipunk.net/scryfall/legalities` (object) and
  `https://wikipunk.net/scryfall/standard` (nested key)

## Card Identity (`@id`)

Each card object is copied as-is, plus an `@id` is added.

- If `uri` is present in the card JSON, `@id = uri`.
- Otherwise, if `id` is present, `@id = https://api.scryfall.com/cards/<id>`.

No `@type` is added. You can identify cards using the existing Scryfall field:

- `object == "card"`

## Datatypes

The embedded JSON-LD context provides a few common datatype hints:

- `released_at` -> `xsd:date`
- `uri`, `scryfall_uri`, `rulings_uri`, `prints_search_uri`, `set_search_uri` -> `@id`

The full context is embedded in exported JSON-LD. A standalone copy also exists at:

- `resources/scryfall/context.jsonld`

## Storage Note (Why Gzip?)

The pinned oracle-cards bulk export is large. This repo stores it as `*.gz` and the CLI supports
reading/writing gzipped JSON/JSON-LD directly.

## Why No Extra MTG Ontology Terms?

Scryfall already defines a stable JSON schema with broad coverage of card variants.
This repo keeps card exports aligned to that schema, and keeps any future modeling extensions
explicitly scoped to the Scryfall vocabulary/context.
