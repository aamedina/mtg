(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application.
  Call `(reset)` to reload modified code and (re)start the system.
  The system under development is `system`, referred from
  `com.stuartsierra.component.repl/system`.
  See also https://github.com/stuartsierra/component.repl"
  (:require
   [arachne.aristotle :as a]
   [camel-snake-kebab.core :as csk]
   [clj-http.client :as http]
   [clojure.data.json :as json]
   [clojure.datafy :refer [datafy]]
   [clojure.data.priority-map :as pm]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint pp]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.tools.namespace.repl :refer [refresh refresh-all clear]]
   [com.stuartsierra.component :as com]
   [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
   [com.walmartlabs.schematic :as sc]
   [net.cgrand.enlive-html :as html]
   [net.wikipunk.boot]
   [net.wikipunk.ext]
   [net.wikipunk.chat :as chat]
   [net.wikipunk.mop :as mop :refer [isa? descendants parents ancestors]]
   [net.wikipunk.openai :as openai]
   [net.wikipunk.rdf :as rdf :refer [doc]]
   [net.wikipunk.temple :as temple]
   [zprint.core :as zprint]
   [net.wikipunk.mtg :as mtg]
   [xtdb.api :as xt])
  (:refer-clojure :exclude [isa? descendants parents ancestors]))

(set-init
  (fn [_]
    (set! *print-namespace-maps* false)
    (if-let [r (io/resource "system.edn")]
      (-> (slurp r)
          (edn/read-string)
          (sc/assemble-system))
      (throw (ex-info "system.edn is not on classpath" {})))))

(defmacro inspect
  "Evaluate forms in an implicit do and inspect the value of the last
  expression using Reveal."
  [& body]
  `(do (@user/reveal (do ~@body))
       true))

(comment
  (binding [rdf/*recurse* 0]
    (rdf/emit boot/mtg nil)
    (rdf/emit boot/rules nil)
    (rdf/emit boot/scryfall nil)
    (reset)))

(defn pr-subclasses
  [kind xs]
  (doseq [subtype xs
          :let    [n (csk/->PascalCase subtype)]]
    (println "#" subtype "is a" kind)
    (println (str "mtg:" n) "a" "owl:Class" ";")
    (println "  " "rdfs:subClassOf" kind ";")
    (println "  " "rdfs:label" (pr-str subtype) ".")))

(defn pr-individuals
  [kind xs]
  (doseq [subtype xs
          :let    [n (csk/->PascalCase subtype)]]
    (println "#" subtype "is a" kind)
    (println (str "mtg:" n) "a" "owl:NamedIndividual," kind ";")
    (println "  " "rdfs:label" (pr-str subtype) ".")))

(comment
  (set/difference (descendants :mtg/PhyrexianMana)
                  (descendants :mtg/Red)
                  (descendants :mtg/Blue)
                  (descendants :mtg/Black)
                  (descendants :mtg/Green)))

(def gameplay-keys
  ["cmc"
   "color_indicator"
   "colors"
   "keywords"
   "layout"
   "loyalty"
   "mana_cost"
   "name"
   "oracle_text"
   "power"
   "produced_mana"   
   "toughness"
   "type_line"])

(def core-keys
  [#_"id"
   "oracle_id"
   #_"rulings_uri"
   "scryfall_uri"
   "uri"])

(def print-keys
  [#_"artist"
   "flavor_text"
   "prices"
   "rarity"
   "related_uris"
   "released_at"
   "set"
   "set_name"
   "set_uri"])

(defn parse-mana-cost
  [s]
  (:body (http/get "https://api.scryfall.com/symbology/parse-mana"
                   {:as           :json
                    :query-params {:cost s}})))

(defn oracle-ident
  [name]
  (keyword "oracle" (str/replace (csk/->PascalCase name) #"['\s\(\)!,@\"\\~`^;/]" "")))

(defn parse-card
  [{:strs [uri set rarity layout colors keywords type_line mana_cost cmc
           oracle_text flavor_text released_at
           oracle_id scryfall_uri prices name related_uris
           card_faces power toughness loyalty card_faces all_parts
           component]
    :as   card}]
  (into {}
        (filter (comp some? val))
        (cond-> (-> {:db/ident (oracle-ident name)
                     :rdf/type :scryfall/Card}
                    (assoc :scryfall/name name)
                    (assoc :scryfall/uri scryfall_uri)
                    (assoc :scryfall/set set)
                    (assoc :scryfall/rarity rarity)
                    (assoc :scryfall/layout layout)
                    (assoc :scryfall/color colors)
                    (assoc :scryfall/keyword keywords)
                    (assoc :scryfall/typeLine type_line)
                    (assoc :scryfall/oracleID oracle_id)                    
                    (assoc :scryfall/flavorText flavor_text)
                    (assoc :scryfall/releaseDate (when-let [date released_at]
                                                   (clojure.instant/read-instant-date date)))            
                    (assoc :scryfall/cmc cmc))
          (not (str/blank? oracle_text))
          (assoc :scryfall/oracleText oracle_text)
          
          (not (str/blank? mana_cost))
          (assoc :scryfall/manaCost mana_cost)
          
          (get prices "usd")
          (assoc :scryfall/priceUSD (Double/parseDouble (get prices "usd")))

          (get prices "usd_foil")
          (assoc :scryfall/priceUSDFoil (Double/parseDouble (get prices "usd_foil")))

          (seq related_uris)
          (assoc :rdfs/seeAlso (conj (vec (vals related_uris)) uri))

          power
          (assoc :scryfall/power (try (Long/parseLong power)
                                      (catch Throwable _ power)))

          toughness
          (assoc :scryfall/toughness (try (Long/parseLong toughness)
                                          (catch Throwable _ toughness)))

          loyalty
          (assoc :scryfall/loyalty (try (Long/parseLong loyalty)
                                        (catch Throwable _ loyalty)))

          card_faces
          (assoc :scryfall/face (into []
                                      (comp (map parse-card)
                                            (map #(dissoc % :db/ident)))
                                      card_faces))

          component
          (assoc :scryfall/component component)

          (seq all_parts)
          (assoc :scryfall/related (into [] (comp (map parse-card)
                                                  (remove #(identical? (:db/ident %) (oracle-ident name)))
                                                  (map #(dissoc % :db/ident)))
                                         all_parts)))))

(defn parse-cards
  "Given a file containing cards from the Scryfall API describing a card, parse the data into a Clojure map representing a :scryfall/Card."
  ([] (parse-cards "https://data.scryfall.io/oracle-cards/oracle-cards-20230416090233.json"))
  ([file]
   (with-open [r (io/reader file)]
     (let [cards (json/read r)]
       (for [card  cards
             :when (get card "name")
             :when (= (get-in card ["legalities" "standard"]) "legal")]
         (parse-card card))))))

(def oracle-cards
  (parse-cards))

(defn oracle-doc
  [{:scryfall/keys [name manaCost power toughness loyalty defense oracleText typeLine flavorText face] :as card}]
  (str/trim
    (str/join "\n\n  "
              (filter some?
                      (into [name
                             
                             (when manaCost
                               (str manaCost
                                    (when (or power toughness)
                                      (str " — " power "/" toughness))
                                    (when loyalty
                                      (str " — " loyalty))
                                    (when defense
                                      (str " — " defense))))
                             
                             typeLine

                             (when oracleText
                               (str/replace oracleText #"\n" "\n  "))
                             (when flavorText
                               (str \u201c (str/replace flavorText #"\n" "") \u201d))]
                            (comp (map oracle-doc)
                                  (interpose "//"))
                            face)))))

(comment
  (run! (fn [card]
          (zprint/zprint (list 'def (symbol (str/replace (name (:db/ident card)) #"['\s\(\)!,@\"\\~`^;/]" ""))
                               (oracle-doc card)
                               card)
                         {:map    {:justify?      true
                                   :nl-separator? false
                                   :hang?         true
                                   :indent        0
                                   :sort-in-code? true
                                   :force-nl?     true}
                          :vector {:wrap? false}})
          (newline))
        (sort-by :db/ident oracle-cards)))
