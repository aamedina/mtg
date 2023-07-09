(ns net.wikipunk.rdf.scryfall
  "Ontology for Scryfall data"
  {:dcat/downloadURL    "resources/scryfall.ttl",
   :prov/wasDerivedFrom "https://scryfall.com/docs/api",
   :rdf/ns-prefix-map   {"db" "https://wikipunk.net/db/",
                         "db.cardinality"
                         "https://wikipunk.net/db/cardinality/",
                         "db.type" "https://wikipunk.net/db/type/",
                         "db.unique" "https://wikipunk.net/db/unique/",
                         "dcterms" "http://purl.org/dc/terms/",
                         "jsonschema"
                         "https://www.w3.org/2019/wot/json-schema#",
                         "mtg" "https://wikipunk.net/mtg/",
                         "owl" "http://www.w3.org/2002/07/owl#",
                         "prov" "http://www.w3.org/ns/prov#",
                         "rdf" "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
                         "rdfs" "http://www.w3.org/2000/01/rdf-schema#",
                         "scryfall" "https://wikipunk.net/scryfall/",
                         "xsd" "http://www.w3.org/2001/XMLSchema#"},
   :rdf/type            :owl/Ontology,
   :rdfa/prefix         "scryfall",
   :rdfa/uri            "https://wikipunk.net/scryfall/",
   :rdfs/comment        "Ontology for Scryfall data",
   :rdfs/label          "Scryfall Ontology"}
  (:refer-clojure :exclude [keyword name set]))

(def Card
  "A card object represents a Magic card in Scryfall’s database."
  {:db/ident :scryfall/Card,
   :rdf/type :owl/Class,
   :rdfs/comment
   "A card object represents a Magic card in Scryfall’s database.",
   :rdfs/label "Card"})

(def JsonSchema
  {:db/ident :scryfall/JsonSchema,
   :jsonschema/properties
   [{:jsonschema/items {:jsonschema/propertyType :scryfall/keyword,
                        :rdf/type :jsonschema/ObjectSchema},
     :jsonschema/propertyName "keywords",
     :jsonschema/required false,
     :rdf/type :jsonschema/ArraySchema}
    {:jsonschema/propertyName "cmc",
     :jsonschema/propertyType :scryfall/cmc,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "rarity",
     :jsonschema/propertyType :scryfall/rarity,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "type_line",
     :jsonschema/propertyType :scryfall/typeLine,
     :jsonschema/required true,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "toughness",
     :jsonschema/propertyType :scryfall/toughness,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "power",
     :jsonschema/propertyType :scryfall/power,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/items    {:jsonschema/propertyType :scryfall/color,
                           :rdf/type :jsonschema/ObjectSchema},
     :jsonschema/maxItems 5,
     :jsonschema/minItems 0,
     :jsonschema/propertyName "colors",
     :jsonschema/required false,
     :rdf/type            :jsonschema/ArraySchema}
    {:jsonschema/propertyName "layout",
     :jsonschema/propertyType :scryfall/layout,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "mana_cost",
     :jsonschema/propertyType :scryfall/manaCost,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "released_at",
     :jsonschema/propertyType :scryfall/releaseDate,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "flavor_text",
     :jsonschema/propertyType :scryfall/flavorText,
     :jsonschema/required false,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "set",
     :jsonschema/propertyType :scryfall/set,
     :jsonschema/required true,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "oracle_text",
     :jsonschema/propertyType :scryfall/oracleText,
     :jsonschema/required true,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "oracle_id",
     :jsonschema/propertyType :scryfall/oracleID,
     :jsonschema/required true,
     :rdf/type :jsonschema/ObjectSchema}
    {:jsonschema/propertyName "uri",
     :jsonschema/propertyType :scryfall/uri,
     :jsonschema/required true,
     :rdf/type :jsonschema/ObjectSchema}],
   :rdf/type :jsonschema/ObjectSchema})

(def cmc
  "CMC"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/cmc,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "CMC",
   :rdfs/range     :xsd/float})

(def color
  "Color"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :scryfall/color,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Color",
   :rdfs/range     :xsd/string})

(def component
  "Card Component"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/component,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Card Component",
   :rdfs/range     :xsd/string})

(def face
  "Card Face"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :scryfall/face,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Card Face",
   :rdfs/range     :scryfall/Card})

(def flavorText
  "Flavor Text"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/flavorText,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Flavor Text",
   :rdfs/range     :xsd/string})

(def keyword
  "Keyword"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :scryfall/keyword,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Keyword",
   :rdfs/range     :xsd/string})

(def layout
  "Layout"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/layout,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Layout",
   :rdfs/range     :xsd/string})

(def manaCost
  "Mana Cost"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/manaCost,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Mana Cost",
   :rdfs/range     :xsd/string})

(def name
  "Name"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/name,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Name",
   :rdfs/range     :xsd/string})

(def oracleID
  "Oracle ID"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/oracleID,
   :db/unique      :db.unique/identity,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Oracle ID",
   :rdfs/range     :xsd/string})

(def oracleText
  "Oracle Text"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/oracleText,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Oracle Text",
   :rdfs/range     :xsd/string})

(def power
  "Power"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/power,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Power",
   :rdfs/range     :xsd/string})

(def priceUSD
  "Price USD"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/priceUSD,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Price USD",
   :rdfs/range     :xsd/float})

(def priceUSDFoil
  "Price USD Foil"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/priceUSDFoil,
   :db/valueType   :db.type/double,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Price USD Foil",
   :rdfs/range     :xsd/float})

(def rarity
  "Rarity"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/rarity,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Rarity",
   :rdfs/range     :xsd/string})

(def related
  "Related card, token, meld part, etc."
  {:db/cardinality :db.cardinality/many,
   :db/ident       :scryfall/related,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Related card, token, meld part, etc.",
   :rdfs/range     :scryfall/Card})

(def releaseDate
  "Release Date"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/releaseDate,
   :db/valueType   :db.type/instant,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Release Date",
   :rdfs/range     :xsd/date})

(def set
  "Set"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/set,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Set",
   :rdfs/range     :xsd/string})

(def toughness
  "Toughness"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/toughness,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Toughness",
   :rdfs/range     :xsd/string})

(def typeLine
  "Type Line"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/typeLine,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "Type Line",
   :rdfs/range     :xsd/string})

(def uri
  "URI"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :scryfall/uri,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :scryfall/Card,
   :rdfs/label     "URI",
   :rdfs/range     :xsd/anyURI})