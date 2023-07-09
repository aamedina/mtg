(ns net.wikipunk.mtg
  (:require
   [clojure.data.priority-map :as pm]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [com.stuartsierra.component :as com]
   [net.wikipunk.boot]
   [net.wikipunk.ext]
   [net.wikipunk.mtg.boot :as boot]
   [net.wikipunk.rdf :as rdf]
   [net.wikipunk.rdf.as]
   [net.wikipunk.rdf.dc11]
   [net.wikipunk.rdf.dcterms]
   [net.wikipunk.rdf.event]
   [net.wikipunk.rdf.foaf]
   [net.wikipunk.rdf.greg]
   [net.wikipunk.rdf.oa]
   [net.wikipunk.rdf.org]
   [net.wikipunk.rdf.prov]
   [net.wikipunk.rdf.rel]
   [net.wikipunk.rdf.schema]
   [net.wikipunk.rdf.skos]
   [net.wikipunk.rdf.time]
   [net.wikipunk.rdf.vann]
   [net.wikipunk.rdf.void]
   [net.wikipunk.rdf.vs]
   [net.wikipunk.rdf.mtg]
   [net.wikipunk.rdf.mtg.rules]
   [net.wikipunk.rdf.scryfall]
   [net.wikipunk.rdf.oracle]
   [xtdb.api :as xt]))

(defn- system
  []
  @(requiring-resolve 'com.stuartsierra.component.repl/system))

(defrecord MTG []
  com/Lifecycle
  (start [this]
    this)
  (stop [this]
    this)

  rdf/NamespaceSpitter
  (emit [_ arg-map]
    (rdf/emit [boot/mtg boot/rules boot/scryfall] arg-map)))

(defn text-search
  ([query]
   (text-search (:vocab (system)) query))
  ([vocab query]
   (->> (xt/q vocab
              '{:find     [?e ?s]
                :in       [?q]
                :where    [[(wildcard-text-search ?q) [[?e ?a ?v ?s]]]
                           [?e :xt/id]
                           [(namespace ?e) ?ns]
                           (or [(clojure.string/starts-with? ?ns "mtg")]
                               [(clojure.string/starts-with? ?ns "oracle")]
                               [(clojure.string/starts-with? ?ns "scryfall")])]
                :order-by [[?s :desc]]}
              query)
        (map first)
        (distinct))))
