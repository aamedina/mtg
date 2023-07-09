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
   [net.wikipunk.mop :as mop :refer [isa? descendants parents ancestors]]
   [net.wikipunk.openai :as openai]
   [net.wikipunk.rdf :as rdf :refer [doc]]
   [net.wikipunk.qdrant :as qdrant]
   [zprint.core :as zprint]
   [net.wikipunk.mtg :as mtg]
   [xtdb.api :as xt]
   [net.wikipunk.punk.db :as db]
   [net.wikipunk.mtg.boot :as boot]
   [datomic.client.api :as d])
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

(def powers
  (delay
    (update-vals (group-by #(:rdfs/label (datafy %)) (descendants :mtg/Power)) first)))

(def loyalties
  (delay
    (update-vals (group-by #(:rdfs/label (datafy %)) (descendants :mtg/Loyalty)) first)))

(def toughnesses
  (delay
    (update-vals (group-by #(:rdfs/label (datafy %)) (descendants :mtg/Toughness)) first)))

(def mtg-colors
  (delay
    (merge (update-vals (group-by #(:rdfs/label (datafy %)) (descendants :mtg/Color)) first)
           {"W" :mtg/White "B" :mtg/Black "R" :mtg/Red "G" :mtg/Green "U" :mtg/Blue})))

(def types
  (delay
    (update-vals (group-by #(:rdfs/label (datafy %)) (descendants :mtg/CardType)) first)))

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
                     :rdf/type (into [:scryfall/Card]
                                     (keep (fn [typename] (get @types typename)))
                                     (str/split type_line #" — "))}
                    (assoc :scryfall/name name)
                    (assoc :scryfall/uri scryfall_uri)
                    (assoc :scryfall/set set)
                    (assoc :scryfall/rarity rarity)
                    (assoc :scryfall/layout layout)
                    (assoc :scryfall/color (mapv @mtg-colors colors))
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
          (assoc :scryfall/power (get @powers power))

          toughness
          (assoc :scryfall/toughness (get @toughnesses toughness))

          loyalty
          (assoc :scryfall/loyalty (get @loyalties loyalty))

          card_faces
          (assoc :scryfall/face (into []
                                      (comp (map parse-card)
                                            (map #(dissoc % :db/ident)))
                                      card_faces))

          component
          (assoc :scryfall/component component)

          (empty? keywords)
          (dissoc :scryfall/keyword)

          (empty? colors)
          (dissoc :scryfall/color)

          (empty? related_uris)
          (dissoc :scryfall/related)

          (seq all_parts)
          (assoc :scryfall/related (into [] (comp (map parse-card)
                                                  (remove #(identical? (:db/ident %) (oracle-ident name)))
                                                  (map #(dissoc % :db/ident)))
                                         all_parts)))))

(defn parse-cards
  "Given a file containing cards from the Scryfall API describing a card, parse the data into a Clojure map representing a :scryfall/Card."
  ([] (parse-cards "https://data.scryfall.io/oracle-cards/oracle-cards-20230709090306.json"))
  ([file]
   (with-open [r (io/reader file)]
     (let [cards (json/read r)]
       (for [card  cards
             :when (get card "name")
             :when (= (get-in card ["legalities" "modern"]) "legal")]
         (parse-card card))))))

(def oracle-cards
  (delay (parse-cards)))

(defn oracle-doc
  [{:scryfall/keys [name manaCost power toughness loyalty oracleText typeLine flavorText face] :as card}]
  (str/trim
    (str/join "\n "
              (filter some?
                      (into [name
                             
                             (when manaCost
                               (str manaCost
                                    (when (or power toughness)
                                      (str " — "
                                           (:rdfs/label (datafy power))
                                           "/"
                                           (:rdfs/label (datafy toughness))))
                                    (when loyalty
                                      (str " — " (:rdfs/label (datafy loyalty))))))
                             
                             typeLine

                             (when oracleText
                               (str/replace oracleText #"\n" "\n "))
                             (when flavorText
                               (str \u201c (str/replace flavorText #"\n" "") \u201d))]
                            (comp (map oracle-doc)
                                  (interpose "//"))
                            face)))))

(comment
  (run! (fn [card]
          (spit "src/cljc/net/wikipunk/rdf/oracle.cljc"
                (with-out-str
                  (zprint/zprint (list 'def (rdf/unmunge (:db/ident card))
                                       (oracle-doc card)
                                       (assoc card :rdfs/comment (oracle-doc card)))
                                 {:map    {:justify?      true
                                           :nl-separator? false
                                           :hang?         true
                                           :indent        0
                                           :sort-in-code? true
                                           :force-nl?     true}
                                  :vector {:wrap? false}})
                  (newline))
                :append true))
        (sort-by :db/ident @oracle-cards)))

