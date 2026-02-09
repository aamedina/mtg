# Rules SKOS Model

This repo models the *Magic: The Gathering Comprehensive Rules* as a SKOS concept scheme.

The primary output is:

- `resources/mtg/rules.skos.jsonld`

## Vocabularies Used

The rules export uses only widely-adopted RDF vocabularies:

- **SKOS**: `http://www.w3.org/2004/02/skos/core#`
- **Dublin Core Terms (dcterms)**: `http://purl.org/dc/terms/`
- **PROV-O**: `http://www.w3.org/ns/prov#`
- **OWL**: `http://www.w3.org/2002/07/owl#`
- **RDF/RDFS/XSD** for basic typing and datatypes

No custom MTG-specific classes/properties are introduced for the rules export.

## Identity / URIs

All URIs are deterministic and derived from the rules structure.

- Concept scheme (one per rules effective date token):
  - `https://wikipunk.net/mtg/rules/scheme/<YYYYMMDD>`
- Top concepts used to separate the two major parts of the document:
  - `https://wikipunk.net/mtg/rules/concept/rules`
  - `https://wikipunk.net/mtg/rules/concept/glossary`
- Chapter headings:
  - `https://wikipunk.net/mtg/rules/chapter/<n>`
- Section headings:
  - `https://wikipunk.net/mtg/rules/section/<nnn>`
- Numbered rules and subrules:
  - `https://wikipunk.net/mtg/rules/rule/<notation>` with `.` replaced by `_`
  - Example: `100.1a` -> `https://wikipunk.net/mtg/rules/rule/100_1a`
- Glossary terms:
  - `https://wikipunk.net/mtg/rules/glossary/<slug>`

## Core SKOS Patterns

All concepts are `skos:Concept` and are attached to the release scheme with `skos:inScheme`.

### Design Rationale (SKOS Best Practices)

The Comprehensive Rules is a document with a stable numbering system and a glossary of defined terms.
This repo uses SKOS as a lightweight, interoperable way to:

- give every rule and term a stable IRI,
- preserve the document hierarchy (chapter/section/rule/subrule),
- attach the authoritative text as a definition,
- record explicit cross-references found in the text.

`skos:broader` is used to represent the document's numbering hierarchy (more general container -> more specific entry).
We do not claim this is an OWL-style logical subsumption hierarchy.

### Labels

- `skos:prefLabel` is language-tagged (`@language: "en"`).
- Chapters/sections use the document heading text including their numbering.
  - Example section label: `"100. General"`
- Rules use the rule notation as the label.
  - Example: `"100.1"`
- Glossary terms use the term itself as the label.
  - Example: `"Abandon"`

### Notation

Numbered items use `skos:notation`:

- Chapters: `"1"`, `"2"`, ...
- Sections: `"100"`, `"601"`, ...
- Rules/subrules: `"100.1"`, `"100.1a"`, ...

Glossary terms typically do not have `skos:notation`.

### Definitions

- Rules: `skos:definition` contains the full rule text.
- Glossary terms: `skos:definition` contains the glossary definition.

Definitions preserve line breaks from the source text (they are not whitespace-collapsed).

### Examples

Rule lines beginning with `Example:` are extracted to:

- `skos:example` (language-tagged, `en`)

Those `Example:` lines are removed from `skos:definition` so the definition remains the normative rule text.

### Hierarchy

The numbering structure is represented with `skos:broader`:

- Chapter -> broader `.../concept/rules`
- Section -> broader its chapter
- Rule -> broader its section
- Subrule (e.g. `100.1a`) -> broader its parent rule (`100.1`) when the parent exists
- Glossary term -> broader `.../concept/glossary`

This export does not emit `skos:narrower` explicitly; it can be inferred as the inverse of `skos:broader`.

## Cross-References

When a rule or glossary definition contains textual references like `See rule 701.33`, those are extracted and recorded as:

- `dcterms:references` -> the referenced rule concept URI

When a glossary definition is of the form `See <Other Term>.`, the referenced glossary term (when present) is also recorded in `dcterms:references`.

Notes:

- References are extracted from the source text on a best-effort basis. If the source text contains a typo or refers to a rule number that does not exist in that release, the export may contain a dangling `dcterms:references` target.
- `python -m mtg_ontology.cli rules validate` reports these as warnings by default.
- As of the `20260116` rules release, the glossary entry "Map" references `110.10`, which does not exist in that release, so it shows up as a warning.

## Scheme Metadata

Each release scheme includes:

- `dcterms:identifier`: the `<YYYYMMDD>` token
- `dcterms:issued`: parsed from the "effective as of" date line (when available)
- `prov:wasDerivedFrom`: the Wizards source URL (when available)
- `skos:hasTopConcept`: links to the two top concepts (`.../concept/rules` and `.../concept/glossary`)

## JSON-LD Context

The rules JSON-LD is written with an embedded context. A standalone copy also exists at:

- `resources/mtg/rules.context.jsonld`
