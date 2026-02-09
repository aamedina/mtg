"""Shared constants for ontology generation."""

RULES_BASE_IRI = "https://wikipunk.net/mtg/rules/"
SCRYFALL_VOCAB_IRI = "https://wikipunk.net/scryfall/"
SCRYFALL_API_BASE_IRI = "https://api.scryfall.com/"

RULES_JSONLD_CONTEXT = {
    "@version": 1.1,
    "dcterms": "http://purl.org/dc/terms/",
    "owl": "http://www.w3.org/2002/07/owl#",
    "prov": "http://www.w3.org/ns/prov#",
    "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
    "rdfs": "http://www.w3.org/2000/01/rdf-schema#",
    "skos": "http://www.w3.org/2004/02/skos/core#",
    "xsd": "http://www.w3.org/2001/XMLSchema#",
    "label": "skos:prefLabel",
    "definition": "skos:definition",
    "broader": {"@id": "skos:broader", "@type": "@id"},
    "related": {"@id": "skos:related", "@type": "@id"},
    "inScheme": {"@id": "skos:inScheme", "@type": "@id"},
    "topConceptOf": {"@id": "skos:topConceptOf", "@type": "@id"},
    "hasTopConcept": {"@id": "skos:hasTopConcept", "@type": "@id"},
    "notation": "skos:notation",
}

SCRYFALL_JSONLD_CONTEXT = {
    "@version": 1.1,
    # Map all JSON keys 1:1 to IRIs under this vocabulary by default.
    "@vocab": SCRYFALL_VOCAB_IRI,
    "xsd": "http://www.w3.org/2001/XMLSchema#",
    # Common Scryfall URI fields.
    "uri": {"@type": "@id"},
    "scryfall_uri": {"@type": "@id"},
    "rulings_uri": {"@type": "@id"},
    "prints_search_uri": {"@type": "@id"},
    "set_search_uri": {"@type": "@id"},
    # Common date fields.
    "released_at": {"@type": "xsd:date"},
}
