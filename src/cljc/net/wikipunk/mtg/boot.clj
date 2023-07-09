(ns net.wikipunk.mtg.boot
  {:rdf/type :jsonld/Context})

(def mtg
  {:dcat/downloadURL "resources/mtg.ttl"
   :rdf/type         :rdfa/PrefixMapping
   :rdfa/uri         "https://wikipunk.net/mtg/"
   :rdfa/prefix      "mtg"})

(def rules
  {:dcat/downloadURL "resources/mtg/rules.ttl"
   :rdf/type         :rdfa/PrefixMapping
   :rdfa/uri         "https://wikipunk.net/mtg/rules/"
   :rdfa/prefix      "mtg.rules"})

(def scryfall
  {:dcat/downloadURL "resources/scryfall.ttl"
   :rdf/type         :rdfa/PrefixMapping
   :rdfa/uri         "https://wikipunk.net/scryfall/"
   :rdfa/prefix      "scryfall"})

(def oracle
  {:dcat/downloadURL "resources/oracle.ttl"
   :rdf/type         :rdfa/PrefixMapping
   :rdfa/uri         "https://wikipunk.net/oracle/"
   :rdfa/prefix      "oracle"})
