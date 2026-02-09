"""Parse MTG Comprehensive Rules text into SKOS-flavored JSON-LD graph nodes."""

from __future__ import annotations

import json
import re
from dataclasses import dataclass
from datetime import datetime
from pathlib import Path
from typing import Any

from .constants import RULES_BASE_IRI, RULES_JSONLD_CONTEXT
from .utils import normalize_text, slugify

_CHAPTER_RE = re.compile(r"^([1-9])\.\s+(.+)$")
_SECTION_RE = re.compile(r"^(\d{3})\.\s+(.+)$")
_RULE_RE = re.compile(r"^(\d{3}\.\d+[a-z]?)(\.)?\s+(.*)$")
_EFFECTIVE_DATE_RE = re.compile(r"These rules are effective as of\s+(.+?)\.", re.IGNORECASE)
_RULE_REF_RE = re.compile(r"rule(?:s)?\s+(\d{3}\.\d+[a-z]?)", re.IGNORECASE)
_SEE_GLOSSARY_RE = re.compile(r"^See\s+(.+?)\.?$", re.IGNORECASE)
_EXAMPLE_RE = re.compile(r"^Example:\s*(.+)$", re.IGNORECASE)


@dataclass(frozen=True)
class ParsedRules:
    """Container for parsed rules graph and metadata."""

    graph_doc: dict[str, Any]
    release_token: str
    effective_date: str


def _chapter_uri(number: str) -> str:
    return f"{RULES_BASE_IRI}chapter/{number}"


def _section_uri(number: str) -> str:
    return f"{RULES_BASE_IRI}section/{number}"


def _rule_uri(notation: str) -> str:
    encoded = notation.replace(".", "_")
    return f"{RULES_BASE_IRI}rule/{encoded}"


def _glossary_uri(label: str) -> str:
    return f"{RULES_BASE_IRI}glossary/{slugify(label)}"


def _looks_structural(line: str) -> bool:
    if not line:
        return False
    return bool(_CHAPTER_RE.match(line) or _SECTION_RE.match(line) or _RULE_RE.match(line))


def _first_non_empty_index(lines: list[str], start: int) -> int | None:
    for idx in range(start, len(lines)):
        if lines[idx].strip():
            return idx
    return None


def _find_rule_body_bounds(lines: list[str]) -> tuple[int, int, int]:
    """Return (rules_start_idx, glossary_idx, credits_after_glossary_idx)."""
    credits_indices = [idx for idx, line in enumerate(lines) if line.strip() == "Credits"]
    if not credits_indices:
        raise RuntimeError("Could not locate 'Credits' marker in rules text")

    rules_start: int | None = None
    for idx in credits_indices:
        next_idx = _first_non_empty_index(lines, idx + 1)
        if next_idx is None:
            continue
        if lines[next_idx].strip() == "1. Game Concepts":
            rules_start = next_idx
            break

    if rules_start is None:
        chapter_indices = [
            idx for idx, line in enumerate(lines) if line.strip() == "1. Game Concepts"
        ]
        if len(chapter_indices) < 2:
            raise RuntimeError("Could not identify beginning of numbered rules body")
        rules_start = chapter_indices[1]

    glossary_idx: int | None = None
    for idx in range(rules_start + 1, len(lines)):
        if lines[idx].strip() == "Glossary":
            glossary_idx = idx
            break

    if glossary_idx is None:
        raise RuntimeError("Could not locate glossary marker after rules body")

    credits_after_glossary: int | None = None
    for idx in range(glossary_idx + 1, len(lines)):
        if lines[idx].strip() == "Credits":
            credits_after_glossary = idx
            break

    if credits_after_glossary is None:
        raise RuntimeError("Could not locate trailing credits marker after glossary")

    return rules_start, glossary_idx, credits_after_glossary


def _normalize_iri_list(values: list[str]) -> list[dict[str, str]]:
    seen: set[str] = set()
    result: list[dict[str, str]] = []
    for value in values:
        if value in seen:
            continue
        seen.add(value)
        result.append({"@id": value})
    return result


def _lang_string(value: str, language: str = "en") -> dict[str, str]:
    return {"@value": value, "@language": language}


def parse_rules_text(
    source_text: str,
    release_token: str,
    source_url: str | None = None,
) -> ParsedRules:
    """Parse rules text into a JSON-LD SKOS graph."""
    normalized = normalize_text(source_text)
    lines = normalized.split("\n")

    effective_date_match = _EFFECTIVE_DATE_RE.search(normalized)
    effective_date = effective_date_match.group(1) if effective_date_match else "Unknown"

    issued_iso = None
    try:
        issued_iso = datetime.strptime(effective_date, "%B %d, %Y").date().isoformat()
    except ValueError:
        issued_iso = None

    rules_start, glossary_idx, credits_after_glossary = _find_rule_body_bounds(lines)

    scheme_uri = f"{RULES_BASE_IRI}scheme/{release_token}"
    rules_root_uri = f"{RULES_BASE_IRI}concept/rules"
    glossary_root_uri = f"{RULES_BASE_IRI}concept/glossary"

    graph: list[dict[str, Any]] = []

    scheme_node: dict[str, Any] = {
        "@id": scheme_uri,
        "@type": ["skos:ConceptScheme", "owl:Ontology"],
        "dcterms:title": _lang_string("Magic: The Gathering Comprehensive Rules"),
        "dcterms:identifier": release_token,
        "dcterms:description": _lang_string("Comprehensive Rules parsed into SKOS concepts"),
        "rdfs:label": _lang_string("Magic: The Gathering Comprehensive Rules"),
    }
    if source_url:
        scheme_node["prov:wasDerivedFrom"] = source_url
    if issued_iso:
        scheme_node["dcterms:issued"] = {"@value": issued_iso, "@type": "xsd:date"}

    graph.append(scheme_node)

    rules_root_node: dict[str, Any] = {
        "@id": rules_root_uri,
        "@type": ["skos:Concept"],
        "skos:prefLabel": _lang_string("Numbered Rules"),
        "skos:definition": _lang_string(
            "All numbered sections and subrules from the Comprehensive Rules."
        ),
        "skos:inScheme": {"@id": scheme_uri},
        "skos:topConceptOf": {"@id": scheme_uri},
    }
    glossary_root_node: dict[str, Any] = {
        "@id": glossary_root_uri,
        "@type": ["skos:Concept"],
        "skos:prefLabel": _lang_string("Glossary"),
        "skos:definition": _lang_string("Defined game terms from the Comprehensive Rules glossary."),
        "skos:inScheme": {"@id": scheme_uri},
        "skos:topConceptOf": {"@id": scheme_uri},
    }
    graph.extend([rules_root_node, glossary_root_node])

    scheme_node["skos:hasTopConcept"] = [{"@id": rules_root_uri}, {"@id": glossary_root_uri}]

    section_uris: dict[str, str] = {}
    rule_uris: dict[str, str] = {}

    current_chapter_uri = ""
    current_section_uri = ""

    idx = rules_start
    while idx < glossary_idx:
        raw_line = lines[idx]
        line = raw_line.strip()
        if not line:
            idx += 1
            continue

        chapter_match = _CHAPTER_RE.match(line)
        section_match = _SECTION_RE.match(line)
        rule_match = _RULE_RE.match(line)

        if chapter_match:
            chapter_num, chapter_label = chapter_match.groups()
            chapter_uri = _chapter_uri(chapter_num)
            current_chapter_uri = chapter_uri

            graph.append(
                {
                    "@id": chapter_uri,
                    "@type": ["skos:Concept"],
                    "skos:prefLabel": _lang_string(f"{chapter_num}. {chapter_label}"),
                    "skos:notation": chapter_num,
                    "skos:broader": {"@id": rules_root_uri},
                    "skos:inScheme": {"@id": scheme_uri},
                }
            )
            idx += 1
            continue

        if section_match:
            section_num, section_label = section_match.groups()
            section_uri = _section_uri(section_num)
            current_section_uri = section_uri
            section_uris[section_num] = section_uri

            graph.append(
                {
                    "@id": section_uri,
                    "@type": ["skos:Concept"],
                    "skos:prefLabel": _lang_string(f"{section_num}. {section_label}"),
                    "skos:notation": section_num,
                    "skos:broader": {"@id": current_chapter_uri or rules_root_uri},
                    "skos:inScheme": {"@id": scheme_uri},
                }
            )
            idx += 1
            continue

        if rule_match:
            notation = rule_match.group(1)
            body_start = rule_match.group(3).rstrip()
            text_lines = [body_start] if body_start else []

            next_idx = idx + 1
            while next_idx < glossary_idx:
                raw_next = lines[next_idx]
                candidate = raw_next.strip()
                if _looks_structural(candidate):
                    break
                text_lines.append(raw_next.rstrip())
                next_idx += 1

            examples: list[str] = []
            definition_lines: list[str] = []
            for line in text_lines:
                match = _EXAMPLE_RE.match(line.strip())
                if match:
                    examples.append(match.group(1).strip())
                    continue
                definition_lines.append(line)
            # Preserve line breaks; downstream embedding text builders can normalize whitespace.
            rule_text = "\n".join(definition_lines).strip()
            uri = _rule_uri(notation)
            rule_uris[notation] = uri

            broader_uri = current_section_uri or rules_root_uri
            if notation[-1].isalpha():
                parent_notation = notation[:-1]
                parent_uri = rule_uris.get(parent_notation)
                if parent_uri:
                    broader_uri = parent_uri

            referenced_rule_uris = [_rule_uri(ref) for ref in _RULE_REF_RE.findall(rule_text)]

            node: dict[str, Any] = {
                "@id": uri,
                "@type": ["skos:Concept"],
                "skos:prefLabel": _lang_string(notation),
                "skos:notation": notation,
                "skos:definition": _lang_string(rule_text),
                "skos:broader": {"@id": broader_uri},
                "skos:inScheme": {"@id": scheme_uri},
            }
            if examples:
                node["skos:example"] = [_lang_string(example) for example in examples]
            if referenced_rule_uris:
                node["dcterms:references"] = _normalize_iri_list(referenced_rule_uris)

            graph.append(node)
            idx = next_idx
            continue

        idx += 1

    glossary_entries: list[tuple[str, str]] = []
    current_term: str | None = None
    definition_lines: list[str] = []

    for raw_line in lines[glossary_idx + 1 : credits_after_glossary]:
        line = raw_line.strip()
        if not line:
            if current_term and definition_lines:
                glossary_entries.append((current_term, "\n".join(definition_lines).strip()))
                current_term = None
                definition_lines = []
            continue

        if current_term is None:
            current_term = line
            definition_lines = []
        else:
            definition_lines.append(line)

    if current_term and definition_lines:
        glossary_entries.append((current_term, "\n".join(definition_lines).strip()))

    glossary_label_to_uri: dict[str, str] = {
        label.lower(): _glossary_uri(label) for label, _ in glossary_entries
    }
    for label, definition in glossary_entries:
        uri = _glossary_uri(label)

        node: dict[str, Any] = {
            "@id": uri,
            "@type": ["skos:Concept"],
            "skos:prefLabel": _lang_string(label),
            "skos:definition": _lang_string(definition),
            "skos:broader": {"@id": glossary_root_uri},
            "skos:inScheme": {"@id": scheme_uri},
        }

        referenced_rule_uris = [_rule_uri(ref) for ref in _RULE_REF_RE.findall(definition)]
        see_match = _SEE_GLOSSARY_RE.match(definition)
        if see_match:
            term = see_match.group(1).strip().rstrip(".")
            term_uri = glossary_label_to_uri.get(term.lower())
            if term_uri:
                referenced_rule_uris.append(term_uri)

        if referenced_rule_uris:
            node["dcterms:references"] = _normalize_iri_list(referenced_rule_uris)

        graph.append(node)

    graph_doc = {
        "@context": RULES_JSONLD_CONTEXT,
        "@graph": graph,
    }

    return ParsedRules(
        graph_doc=graph_doc,
        release_token=release_token,
        effective_date=effective_date,
    )


def parse_rules_file(
    source_path: Path,
    release_token: str | None = None,
    source_url: str | None = None,
) -> ParsedRules:
    """Read a text file from disk and parse it."""
    token = release_token or source_path.stem
    text = source_path.read_text(encoding="utf-8-sig")
    return parse_rules_text(text, release_token=token, source_url=source_url)


def write_graph_jsonld(graph_doc: dict[str, Any], output_path: Path) -> Path:
    """Write a JSON-LD document to disk."""
    output_path.parent.mkdir(parents=True, exist_ok=True)
    output_path.write_text(json.dumps(graph_doc, indent=2, ensure_ascii=False), encoding="utf-8")
    return output_path
