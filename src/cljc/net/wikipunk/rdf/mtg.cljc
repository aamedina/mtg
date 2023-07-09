(ns net.wikipunk.rdf.mtg
  "Magic: The Gathering Ontology"
  {:dcat/downloadURL "resources/mtg.ttl",
   :prov/wasDerivedFrom
   {:rdfa/uri
    "https://media.wizards.com/2023/downloads/MagicCompRules%2020230616.txt"},
   :rdf/ns-prefix-map {"db" "https://wikipunk.net/db/",
                       "db.cardinality" "https://wikipunk.net/db/cardinality/",
                       "db.type" "https://wikipunk.net/db/type/",
                       "db.unique" "https://wikipunk.net/db/unique/",
                       "mtg" "https://wikipunk.net/mtg/",
                       "mtg.rules" "https://wikipunk.net/mtg/rules/",
                       "owl" "http://www.w3.org/2002/07/owl#",
                       "prov" "http://www.w3.org/ns/prov#",
                       "rdf" "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
                       "rdfs" "http://www.w3.org/2000/01/rdf-schema#",
                       "schema" "http://schema.org/",
                       "skos" "http://www.w3.org/2004/02/skos/core#",
                       "xsd" "http://www.w3.org/2001/XMLSchema#"},
   :rdf/type :owl/Ontology,
   :rdfa/prefix "mtg",
   :rdfa/uri "https://wikipunk.net/mtg/",
   :rdfs/label "Magic: The Gathering Ontology",
   :rdfs/seeAlso
   {:rdfa/uri
    "https://magic.wizards.com/en/game-info/gameplay/rules-and-formats/rules"}}
  (:refer-clojure :exclude [name set type]))

(def Abandon
  "Abandon"
  {:db/ident   :mtg/Abandon,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Abandon"})

(def Abian
  "Abian"
  {:db/ident   :mtg/Abian,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Abian"})

(def AbilityWord
  "Ability Word"
  {:db/ident   :mtg/AbilityWord,
   :prov/wasDerivedFrom :mtg.rules/AbilityWord,
   :rdf/type   :owl/Class,
   :rdfs/label "Ability Word"})

(def Absorb
  "Absorb"
  {:db/ident   :mtg/Absorb,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Absorb"})

(def Activate
  "Activate"
  {:db/ident   :mtg/Activate,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Activate"})

(def Adamant
  "Adamant"
  {:db/ident   :mtg/Adamant,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Adamant"})

(def Adapt
  "Adapt"
  {:db/ident   :mtg/Adapt,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Adapt"})

(def Addendum
  "Addendum"
  {:db/ident   :mtg/Addendum,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Addendum"})

(def Adventure
  "Adventure"
  {:db/ident   :mtg/Adventure,
   :rdf/type   [:mtg/Spell :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Adventure"})

(def Advisor
  "Advisor"
  {:db/ident   :mtg/Advisor,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Advisor"})

(def Aetherborn
  "Aetherborn"
  {:db/ident   :mtg/Aetherborn,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Aetherborn"})

(def Affinity
  "Affinity"
  {:db/ident   :mtg/Affinity,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Affinity"})

(def Afflict
  "Afflict"
  {:db/ident   :mtg/Afflict,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Afflict"})

(def Afterlife
  "Afterlife"
  {:db/ident   :mtg/Afterlife,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Afterlife"})

(def Aftermath
  "Aftermath"
  {:db/ident   :mtg/Aftermath,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Aftermath"})

(def Ajani
  "Ajani"
  {:db/ident   :mtg/Ajani,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ajani"})

(def Alara
  "Alara"
  {:db/ident   :mtg/Alara,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Alara"})

(def Alien
  "Alien"
  {:db/ident   :mtg/Alien,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Alien"})

(def Alliance
  "Alliance"
  {:db/ident   :mtg/Alliance,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Alliance"})

(def Ally
  "Ally"
  {:db/ident   :mtg/Ally,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ally"})

(def Amass
  "Amass"
  {:db/ident   :mtg/Amass,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Amass"})

(def Aminatou
  "Aminatou"
  {:db/ident   :mtg/Aminatou,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Aminatou"})

(def Amonkhet
  "Amonkhet"
  {:db/ident   :mtg/Amonkhet,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Amonkhet"})

(def Amplify
  "Amplify"
  {:db/ident   :mtg/Amplify,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Amplify"})

(def Angel
  "Angel"
  {:db/ident   :mtg/Angel,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Angel"})

(def Angrath
  "Angrath"
  {:db/ident   :mtg/Angrath,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Angrath"})

(def Annihilator
  "Annihilator"
  {:db/ident   :mtg/Annihilator,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Annihilator"})

(def Antausia
  "Antausia"
  {:db/ident   :mtg/Antausia,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Antausia"})

(def Antelope
  "Antelope"
  {:db/ident   :mtg/Antelope,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Antelope"})

(def Ape
  "Ape"
  {:db/ident   :mtg/Ape,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ape"})

(def Arcane
  "Arcane"
  {:db/ident   :mtg/Arcane,
   :rdf/type   [:mtg/Spell :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Arcane"})

(def Arcavios
  "Arcavios"
  {:db/ident   :mtg/Arcavios,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Arcavios"})

(def Archer
  "Archer"
  {:db/ident   :mtg/Archer,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Archer"})

(def Archon
  "Archon"
  {:db/ident   :mtg/Archon,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Archon"})

(def Arkhos
  "Arkhos"
  {:db/ident   :mtg/Arkhos,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Arkhos"})

(def Arlinn
  "Arlinn"
  {:db/ident   :mtg/Arlinn,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Arlinn"})

(def Army
  "Army"
  {:db/ident   :mtg/Army,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Army"})

(def Artifact
  "Artifact"
  {:db/ident            :mtg/Artifact,
   :prov/wasDerivedFrom :mtg.rules/Artifacts,
   :rdf/type            :owl/Class,
   :rdfs/label          "Artifact",
   :rdfs/subClassOf     :mtg/CardType})

(def Artificer
  "Artificer"
  {:db/ident   :mtg/Artificer,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Artificer"})

(def Ascend
  "Ascend"
  {:db/ident   :mtg/Ascend,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Ascend"})

(def Ashiok
  "Ashiok"
  {:db/ident   :mtg/Ashiok,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ashiok"})

(def Assassin
  "Assassin"
  {:db/ident   :mtg/Assassin,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Assassin"})

(def Assemble
  "Assemble"
  {:db/ident   :mtg/Assemble,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Assemble"})

(def AssemblyWorker
  "Assembly-Worker"
  {:db/ident   :mtg/AssemblyWorker,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Assembly-Worker"})

(def Assist
  "Assist"
  {:db/ident   :mtg/Assist,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Assist"})

(def Astartes
  "Astartes"
  {:db/ident   :mtg/Astartes,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Astartes"})

(def Atog
  "Atog"
  {:db/ident   :mtg/Atog,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Atog"})

(def Attach
  "Attach"
  {:db/ident   :mtg/Attach,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Attach"})

(def Attraction
  "Attraction"
  {:db/ident   :mtg/Attraction,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Attraction"})

(def Augment
  "Augment"
  {:db/ident   :mtg/Augment,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Augment"})

(def Aura
  "Aura"
  {:db/ident   :mtg/Aura,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Aura"})

(def AuraSwap
  "Aura Swap"
  {:db/ident   :mtg/AuraSwap,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Aura Swap"})

(def Aurochs
  "Aurochs"
  {:db/ident   :mtg/Aurochs,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Aurochs"})

(def Avatar
  "Avatar"
  {:db/ident   :mtg/Avatar,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Avatar"})

(def Awaken
  "Awaken"
  {:db/ident   :mtg/Awaken,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Awaken"})

(def Azgol
  "Azgol"
  {:db/ident   :mtg/Azgol,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Azgol"})

(def Azra
  "Azra"
  {:db/ident   :mtg/Azra,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Azra"})

(def BOB
  "B.O.B."
  {:db/ident   :mtg/BOB,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "B.O.B."})

(def Background
  "Background"
  {:db/ident   :mtg/Background,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Background"})

(def Badger
  "Badger"
  {:db/ident   :mtg/Badger,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Badger"})

(def Bahamut
  "Bahamut"
  {:db/ident   :mtg/Bahamut,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bahamut"})

(def Balloon
  "Balloon"
  {:db/ident   :mtg/Balloon,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Balloon"})

(def Banding
  "Banding"
  {:db/ident   :mtg/Banding,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Banding"})

(def Barbarian
  "Barbarian"
  {:db/ident   :mtg/Barbarian,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Barbarian"})

(def Bard
  "Bard"
  {:db/ident   :mtg/Bard,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bard"})

(def Basic
  "Basic"
  {:db/ident            :mtg/Basic,
   :prov/wasDerivedFrom :mtg.rules/|205_4c|,
   :rdf/type            :owl/Class,
   :rdfs/label          "Basic",
   :rdfs/subClassOf     :mtg/Supertype})

(def BasicLandcycling
  "Basic landcycling"
  {:db/ident   :mtg/BasicLandcycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Basic landcycling"})

(def Basilisk
  "Basilisk"
  {:db/ident   :mtg/Basilisk,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Basilisk"})

(def Basri
  "Basri"
  {:db/ident   :mtg/Basri,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Basri"})

(def Bat
  "Bat"
  {:db/ident   :mtg/Bat,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bat"})

(def Battalion
  "Battalion"
  {:db/ident   :mtg/Battalion,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Battalion"})

(def Battle
  "Battle"
  {:db/ident            :mtg/Battle,
   :prov/wasDerivedFrom :mtg.rules/Battles,
   :rdf/type            :owl/Class,
   :rdfs/label          "Battle",
   :rdfs/subClassOf     :mtg/CardType})

(def BattleCry
  "Battle Cry"
  {:db/ident   :mtg/BattleCry,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Battle Cry"})

(def Bear
  "Bear"
  {:db/ident   :mtg/Bear,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bear"})

(def Beast
  "Beast"
  {:db/ident   :mtg/Beast,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Beast"})

(def Beeble
  "Beeble"
  {:db/ident   :mtg/Beeble,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Beeble"})

(def Beholder
  "Beholder"
  {:db/ident   :mtg/Beholder,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Beholder"})

(def Belenon
  "Belenon"
  {:db/ident   :mtg/Belenon,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Belenon"})

(def Berserker
  "Berserker"
  {:db/ident   :mtg/Berserker,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Berserker"})

(def Bestow
  "Bestow"
  {:db/ident   :mtg/Bestow,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Bestow"})

(def Bird
  "Bird"
  {:db/ident   :mtg/Bird,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bird"})

(def Black
  "Black"
  {:db/ident   :mtg/Black,
   :rdf/type   [:mtg/Color :owl/NamedIndividual],
   :rdfs/label "Black"})

(def BlackGreenMana
  "{B/G}"
  {:db/ident   :mtg/BlackGreenMana,
   :rdf/type   [:mtg/Mana :mtg/Green :mtg/Black :owl/NamedIndividual],
   :rdfs/label "{B/G}"})

(def BlackGreenPhyrexianMana
  "{B/G/P}"
  {:db/ident   :mtg/BlackGreenPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Green :mtg/Black :owl/NamedIndividual],
   :rdfs/label "{B/G/P}"})

(def BlackHybridMana
  "{2/B}"
  {:db/ident   :mtg/BlackHybridMana,
   :rdf/type   [:mtg/Mana :mtg/Black :owl/NamedIndividual],
   :rdfs/label "{2/B}"})

(def BlackMana
  "{B}"
  {:db/ident   :mtg/BlackMana,
   :rdf/type   [:mtg/Black :mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{B}"})

(def BlackPhyrexianMana
  "{B/P}"
  {:db/ident   :mtg/BlackPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Black :owl/NamedIndividual],
   :rdfs/label "{B/P}"})

(def BlackRedMana
  "{B/R}"
  {:db/ident   :mtg/BlackRedMana,
   :rdf/type   [:mtg/Mana :mtg/Red :mtg/Black :owl/NamedIndividual],
   :rdfs/label "{B/R}"})

(def BlackRedPhyrexianMana
  "{B/R/P}"
  {:db/ident   :mtg/BlackRedPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Red :mtg/Black :owl/NamedIndividual],
   :rdfs/label "{B/R/P}"})

(def Blinkmoth
  "Blinkmoth"
  {:db/ident   :mtg/Blinkmoth,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Blinkmoth"})

(def Blitz
  "Blitz"
  {:db/ident   :mtg/Blitz,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Blitz"})

(def Blood
  "Blood"
  {:db/ident   :mtg/Blood,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Blood"})

(def Bloodrush
  "Bloodrush"
  {:db/ident   :mtg/Bloodrush,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Bloodrush"})

(def Bloodthirst
  "Bloodthirst"
  {:db/ident   :mtg/Bloodthirst,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Bloodthirst"})

(def Blue
  "Blue"
  {:db/ident   :mtg/Blue,
   :rdf/type   [:mtg/Color :owl/NamedIndividual],
   :rdfs/label "Blue"})

(def BlueBlackMana
  "{U/B}"
  {:db/ident   :mtg/BlueBlackMana,
   :rdf/type   [:mtg/Mana :mtg/Black :mtg/Blue :owl/NamedIndividual],
   :rdfs/label "{U/B}"})

(def BlueBlackPhyrexianMana
  "{U/B/P}"
  {:db/ident   :mtg/BlueBlackPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Black :mtg/Blue :owl/NamedIndividual],
   :rdfs/label "{U/B/P}"})

(def BlueHybridMana
  "{2/U}"
  {:db/ident   :mtg/BlueHybridMana,
   :rdf/type   [:mtg/Mana :mtg/Blue :owl/NamedIndividual],
   :rdfs/label "{2/U}"})

(def BlueMana
  "{U}"
  {:db/ident   :mtg/BlueMana,
   :rdf/type   [:mtg/Blue :mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{U}"})

(def BluePhyrexianMana
  "{U/P}"
  {:db/ident   :mtg/BluePhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Blue :owl/NamedIndividual],
   :rdfs/label "{U/P}"})

(def BlueRedMana
  "{U/R}"
  {:db/ident   :mtg/BlueRedMana,
   :rdf/type   [:mtg/Mana :mtg/Red :mtg/Blue :owl/NamedIndividual],
   :rdfs/label "{U/R}"})

(def BlueRedPhyrexianMana
  "{U/R/P}"
  {:db/ident   :mtg/BlueRedPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Red :mtg/Blue :owl/NamedIndividual],
   :rdfs/label "{U/R/P}"})

(def Boar
  "Boar"
  {:db/ident   :mtg/Boar,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Boar"})

(def Boast
  "Boast"
  {:db/ident   :mtg/Boast,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Boast"})

(def Bolas
  "Bolas"
  {:db/ident   :mtg/Bolas,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bolas"})

(def BolassMeditationRealm
  "Bolas’s Meditation Realm"
  {:db/ident   :mtg/BolassMeditationRealm,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bolas’s Meditation Realm"})

(def Bolster
  "Bolster"
  {:db/ident   :mtg/Bolster,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Bolster"})

(def Bringer
  "Bringer"
  {:db/ident   :mtg/Bringer,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Bringer"})

(def Brushwagg
  "Brushwagg"
  {:db/ident   :mtg/Brushwagg,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Brushwagg"})

(def Bushido
  "Bushido"
  {:db/ident   :mtg/Bushido,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Bushido"})

(def Buyback
  "Buyback"
  {:db/ident   :mtg/Buyback,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Buyback"})

(def Calix
  "Calix"
  {:db/ident   :mtg/Calix,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Calix"})

(def Camarid
  "Camarid"
  {:db/ident   :mtg/Camarid,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Camarid"})

(def Camel
  "Camel"
  {:db/ident   :mtg/Camel,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Camel"})

(def Capenna
  "Capenna"
  {:db/ident   :mtg/Capenna,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Capenna"})

(def Card
  "Card"
  {:db/ident   :mtg/Card,
   :prov/wasDerivedFrom :mtg.rules/Card,
   :rdf/type   :owl/Class,
   :rdfs/label "Card"})

(def CardType
  "Card Type"
  {:db/ident   :mtg/CardType,
   :prov/wasDerivedFrom :mtg.rules/CardType,
   :rdf/type   :owl/Class,
   :rdfs/label "Card Type"})

(def Caribou
  "Caribou"
  {:db/ident   :mtg/Caribou,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Caribou"})

(def Carrier
  "Carrier"
  {:db/ident   :mtg/Carrier,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Carrier"})

(def Cartouche
  "Cartouche"
  {:db/ident   :mtg/Cartouche,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cartouche"})

(def Cascade
  "Cascade"
  {:db/ident   :mtg/Cascade,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Cascade"})

(def Cast
  "Cast"
  {:db/ident   :mtg/Cast,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Cast"})

(def Casualty
  "Casualty"
  {:db/ident   :mtg/Casualty,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Casualty"})

(def Cat
  "Cat"
  {:db/ident   :mtg/Cat,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cat"})

(def Centaur
  "Centaur"
  {:db/ident   :mtg/Centaur,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Centaur"})

(def Cephalid
  "Cephalid"
  {:db/ident   :mtg/Cephalid,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cephalid"})

(def Champion
  "Champion"
  {:db/ident   :mtg/Champion,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Champion"})

(def Chandra
  "Chandra"
  {:db/ident   :mtg/Chandra,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Chandra"})

(def Changeling
  "Changeling"
  {:db/ident   :mtg/Changeling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Changeling"})

(def Channel
  "Channel"
  {:db/ident   :mtg/Channel,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Channel"})

(def Chicken
  "Chicken"
  {:db/ident   :mtg/Chicken,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Chicken"})

(def Child
  "Child"
  {:db/ident   :mtg/Child,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Child"})

(def Chimera
  "Chimera"
  {:db/ident   :mtg/Chimera,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Chimera"})

(def Chroma
  "Chroma"
  {:db/ident   :mtg/Chroma,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Chroma"})

(def Cipher
  "Cipher"
  {:db/ident   :mtg/Cipher,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Cipher"})

(def Citizen
  "Citizen"
  {:db/ident   :mtg/Citizen,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Citizen"})

(def Clash
  "Clash"
  {:db/ident   :mtg/Clash,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Clash"})

(def T
  "Class"
  {:db/ident   :mtg/Class,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Class"})

(def Cleave
  "Cleave"
  {:db/ident   :mtg/Cleave,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Cleave"})

(def Cleric
  "Cleric"
  {:db/ident   :mtg/Cleric,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cleric"})

(def Cloud
  "Cloud"
  {:db/ident   :mtg/Cloud,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cloud"})

(def Clown
  "Clown"
  {:db/ident   :mtg/Clown,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Clown"})

(def Clue
  "Clue"
  {:db/ident   :mtg/Clue,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Clue"})

(def Cockatrice
  "Cockatrice"
  {:db/ident   :mtg/Cockatrice,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cockatrice"})

(def Cohort
  "Cohort"
  {:db/ident   :mtg/Cohort,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Cohort"})

(def Color
  "Color"
  {:db/ident   :mtg/Color,
   :prov/wasDerivedFrom :mtg.rules/Colors,
   :rdf/type   :owl/Class,
   :rdfs/label "Color"})

(def ColorlessMana
  "{C}"
  {:db/ident   :mtg/ColorlessMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{C}"})

(def Comet
  "Comet"
  {:db/ident   :mtg/Comet,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Comet"})

(def CommanderNinjutsu
  "Commander ninjutsu"
  {:db/ident   :mtg/CommanderNinjutsu,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Commander ninjutsu"})

(def Companion
  "Companion"
  {:db/ident   :mtg/Companion,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Companion"})

(def Compleated
  "Compleated"
  {:db/ident   :mtg/Compleated,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Compleated"})

(def Conjure
  "Conjure"
  {:db/ident   :mtg/Conjure,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Conjure"})

(def Connive
  "Connive"
  {:db/ident   :mtg/Connive,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Connive"})

(def Conspiracy
  "Conspiracy"
  {:db/ident            :mtg/Conspiracy,
   :prov/wasDerivedFrom :mtg.rules/Conspiracies,
   :rdf/type            :owl/Class,
   :rdfs/label          "Conspiracy",
   :rdfs/subClassOf     :mtg/CardType})

(def Conspire
  "Conspire"
  {:db/ident   :mtg/Conspire,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Conspire"})

(def Constellation
  "Constellation"
  {:db/ident   :mtg/Constellation,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Constellation"})

(def Construct
  "Construct"
  {:db/ident   :mtg/Construct,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Construct"})

(def Contraption
  "Contraption"
  {:db/ident   :mtg/Contraption,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Contraption"})

(def Converge
  "Converge"
  {:db/ident   :mtg/Converge,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Converge"})

(def Convert
  "Convert"
  {:db/ident   :mtg/Convert,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Convert"})

(def Convoke
  "Convoke"
  {:db/ident   :mtg/Convoke,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Convoke"})

(def Corrupted
  "Corrupted"
  {:db/ident   :mtg/Corrupted,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Corrupted"})

(def CouncilsDilemma
  "Council's dilemma"
  {:db/ident   :mtg/CouncilsDilemma,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Council's dilemma"})

(def Counter
  "Counter"
  {:db/ident   :mtg/Counter,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Counter"})

(def Coven
  "Coven"
  {:db/ident   :mtg/Coven,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Coven"})

(def Coward
  "Coward"
  {:db/ident   :mtg/Coward,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Coward"})

(def Crab
  "Crab"
  {:db/ident   :mtg/Crab,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Crab"})

(def Create
  "Create"
  {:db/ident   :mtg/Create,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Create"})

(def Creature
  "Creature"
  {:db/ident            :mtg/Creature,
   :prov/wasDerivedFrom :mtg.rules/Creatures,
   :rdf/type            :owl/Class,
   :rdfs/label          "Creature",
   :rdfs/subClassOf     :mtg/CardType})

(def Crew
  "Crew"
  {:db/ident   :mtg/Crew,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Crew"})

(def Cridhe
  "Cridhe"
  {:db/ident   :mtg/Cridhe,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cridhe"})

(def Crocodile
  "Crocodile"
  {:db/ident   :mtg/Crocodile,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Crocodile"})

(def Ctan
  "C'tan"
  {:db/ident   :mtg/Ctan,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "C'tan"})

(def CumulativeUpkeep
  "Cumulative upkeep"
  {:db/ident   :mtg/CumulativeUpkeep,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Cumulative upkeep"})

(def Curse
  "Curse"
  {:db/ident   :mtg/Curse,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Curse"})

(def Custodes
  "Custodes"
  {:db/ident   :mtg/Custodes,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Custodes"})

(def Cycling
  "Cycling"
  {:db/ident   :mtg/Cycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Cycling"})

(def Cyclops
  "Cyclops"
  {:db/ident   :mtg/Cyclops,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Cyclops"})

(def Dack
  "Dack"
  {:db/ident   :mtg/Dack,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dack"})

(def Dakkon
  "Dakkon"
  {:db/ident   :mtg/Dakkon,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dakkon"})

(def Daretti
  "Daretti"
  {:db/ident   :mtg/Daretti,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Daretti"})

(def Dash
  "Dash"
  {:db/ident   :mtg/Dash,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Dash"})

(def Dauthi
  "Dauthi"
  {:db/ident   :mtg/Dauthi,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dauthi"})

(def Davriel
  "Davriel"
  {:db/ident   :mtg/Davriel,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Davriel"})

(def Daybound
  "Daybound"
  {:db/ident   :mtg/Daybound,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Daybound"})

(def Deathtouch
  "Deathtouch"
  {:db/ident   :mtg/Deathtouch,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Deathtouch"})

(def Decayed
  "Decayed"
  {:db/ident   :mtg/Decayed,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Decayed"})

(def Defender
  "Defender"
  {:db/ident   :mtg/Defender,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Defender"})

(def Delirium
  "Delirium"
  {:db/ident   :mtg/Delirium,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Delirium"})

(def Delve
  "Delve"
  {:db/ident   :mtg/Delve,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Delve"})

(def Demigod
  "Demigod"
  {:db/ident   :mtg/Demigod,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Demigod"})

(def Demon
  "Demon"
  {:db/ident   :mtg/Demon,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Demon"})

(def Demonstrate
  "Demonstrate"
  {:db/ident   :mtg/Demonstrate,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Demonstrate"})

(def Desert
  "Desert"
  {:db/ident   :mtg/Desert,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Desert"})

(def Deserter
  "Deserter"
  {:db/ident   :mtg/Deserter,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Deserter"})

(def Desertwalk
  "Desertwalk"
  {:db/ident   :mtg/Desertwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Desertwalk"})

(def Destroy
  "Destroy"
  {:db/ident   :mtg/Destroy,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Destroy"})

(def Detain
  "Detain"
  {:db/ident   :mtg/Detain,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Detain"})

(def Dethrone
  "Dethrone"
  {:db/ident   :mtg/Dethrone,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Dethrone"})

(def Devil
  "Devil"
  {:db/ident   :mtg/Devil,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Devil"})

(def Devoid
  "Devoid"
  {:db/ident   :mtg/Devoid,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Devoid"})

(def Devour
  "Devour"
  {:db/ident   :mtg/Devour,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Devour"})

(def Dihada
  "Dihada"
  {:db/ident   :mtg/Dihada,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dihada"})

(def Dinosaur
  "Dinosaur"
  {:db/ident   :mtg/Dinosaur,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dinosaur"})

(def Discard
  "Discard"
  {:db/ident   :mtg/Discard,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Discard"})

(def Disturb
  "Disturb"
  {:db/ident   :mtg/Disturb,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Disturb"})

(def Djinn
  "Djinn"
  {:db/ident   :mtg/Djinn,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Djinn"})

(def Dog
  "Dog"
  {:db/ident   :mtg/Dog,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dog"})

(def Domain
  "Domain"
  {:db/ident   :mtg/Domain,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Domain"})

(def Dominaria
  "Dominaria"
  {:db/ident   :mtg/Dominaria,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dominaria"})

(def Domri
  "Domri"
  {:db/ident   :mtg/Domri,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Domri"})

(def DoubleClass
  "Double"
  {:db/ident   :mtg/Double,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Double"})

(def DoubleAgenda
  "Double agenda"
  {:db/ident   :mtg/DoubleAgenda,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Double agenda"})

(def DoubleStrike
  "Double strike"
  {:db/ident   :mtg/DoubleStrike,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Double strike"})

(def Dovin
  "Dovin"
  {:db/ident   :mtg/Dovin,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dovin"})

(def Dragon
  "Dragon"
  {:db/ident   :mtg/Dragon,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dragon"})

(def Drake
  "Drake"
  {:db/ident   :mtg/Drake,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Drake"})

(def Dreadnought
  "Dreadnought"
  {:db/ident   :mtg/Dreadnought,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dreadnought"})

(def Dredge
  "Dredge"
  {:db/ident   :mtg/Dredge,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Dredge"})

(def Drone
  "Drone"
  {:db/ident   :mtg/Drone,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Drone"})

(def Druid
  "Druid"
  {:db/ident   :mtg/Druid,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Druid"})

(def Dryad
  "Dryad"
  {:db/ident   :mtg/Dryad,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dryad"})

(def Duck
  "Duck"
  {:db/ident   :mtg/Duck,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Duck"})

(def Dungeon
  "Dungeon"
  {:db/ident            :mtg/Dungeon,
   :prov/wasDerivedFrom :mtg.rules/Dungeons,
   :rdf/type            [:mtg/Planeswalker
                         :owl/NamedIndividual
                         :owl/Class
                         :mtg/CardType],
   :rdfs/label          "Dungeon",
   :rdfs/subClassOf     :mtg/CardType})

(def Dwarf
  "Dwarf"
  {:db/ident   :mtg/Dwarf,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Dwarf"})

(def Echo
  "Echo"
  {:db/ident   :mtg/Echo,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Echo"})

(def Echoir
  "Echoir"
  {:db/ident   :mtg/Echoir,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Echoir"})

(def Efreet
  "Efreet"
  {:db/ident   :mtg/Efreet,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Efreet"})

(def Egg
  "Egg"
  {:db/ident   :mtg/Egg,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Egg"})

(def EightMana
  "{8}"
  {:db/ident   :mtg/EightMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{8}"})

(def EighteenMana
  "{18}"
  {:db/ident   :mtg/EighteenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{18}"})

(def Elder
  "Elder"
  {:db/ident   :mtg/Elder,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Elder"})

(def Eldraine
  "Eldraine"
  {:db/ident   :mtg/Eldraine,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Eldraine"})

(def Eldrazi
  "Eldrazi"
  {:db/ident   :mtg/Eldrazi,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Eldrazi"})

(def Elemental
  "Elemental"
  {:db/ident   :mtg/Elemental,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Elemental"})

(def Elephant
  "Elephant"
  {:db/ident   :mtg/Elephant,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Elephant"})

(def ElevenMana
  "{11}"
  {:db/ident   :mtg/ElevenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{11}"})

(def Elf
  "Elf"
  {:db/ident   :mtg/Elf,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Elf"})

(def Elk
  "Elk"
  {:db/ident   :mtg/Elk,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Elk"})

(def Ellywick
  "Ellywick"
  {:db/ident   :mtg/Ellywick,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ellywick"})

(def Elminster
  "Elminster"
  {:db/ident   :mtg/Elminster,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Elminster"})

(def Elspeth
  "Elspeth"
  {:db/ident   :mtg/Elspeth,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Elspeth"})

(def Embalm
  "Embalm"
  {:db/ident   :mtg/Embalm,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Embalm"})

(def Emerge
  "Emerge"
  {:db/ident   :mtg/Emerge,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Emerge"})

(def Eminence
  "Eminence"
  {:db/ident   :mtg/Eminence,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Eminence"})

(def Employee
  "Employee"
  {:db/ident   :mtg/Employee,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Employee"})

(def Enchant
  "Enchant"
  {:db/ident   :mtg/Enchant,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Enchant"})

(def Enchantment
  "Enchantment"
  {:db/ident            :mtg/Enchantment,
   :prov/wasDerivedFrom :mtg.rules/Enchantments,
   :rdf/type            :owl/Class,
   :rdfs/label          "Enchantment",
   :rdfs/subClassOf     :mtg/CardType})

(def Encore
  "Encore"
  {:db/ident   :mtg/Encore,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Encore"})

(def Enlist
  "Enlist"
  {:db/ident   :mtg/Enlist,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Enlist"})

(def Enrage
  "Enrage"
  {:db/ident   :mtg/Enrage,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Enrage"})

(def Entwine
  "Entwine"
  {:db/ident   :mtg/Entwine,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Entwine"})

(def Epic
  "Epic"
  {:db/ident   :mtg/Epic,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Epic"})

(def Equilor
  "Equilor"
  {:db/ident   :mtg/Equilor,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Equilor"})

(def Equip
  "Equip"
  {:db/ident   :mtg/Equip,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Equip"})

(def Equipment
  "Equipment"
  {:db/ident   :mtg/Equipment,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Equipment"})

(def Ergamon
  "Ergamon"
  {:db/ident   :mtg/Ergamon,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ergamon"})

(def Ersta
  "Ersta"
  {:db/ident   :mtg/Ersta,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ersta"})

(def Escalate
  "Escalate"
  {:db/ident   :mtg/Escalate,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Escalate"})

(def Escape
  "Escape"
  {:db/ident   :mtg/Escape,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Escape"})

(def Estrid
  "Estrid"
  {:db/ident   :mtg/Estrid,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Estrid"})

(def Eternalize
  "Eternalize"
  {:db/ident   :mtg/Eternalize,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Eternalize"})

(def Evoke
  "Evoke"
  {:db/ident   :mtg/Evoke,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Evoke"})

(def Evolve
  "Evolve"
  {:db/ident   :mtg/Evolve,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Evolve"})

(def Exalted
  "Exalted"
  {:db/ident   :mtg/Exalted,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Exalted"})

(def Exchange
  "Exchange"
  {:db/ident   :mtg/Exchange,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Exchange"})

(def Exert
  "Exert"
  {:db/ident   :mtg/Exert,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Exert"})

(def Exile
  "Exile"
  {:db/ident   :mtg/Exile,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Exile"})

(def Exploit
  "Exploit"
  {:db/ident   :mtg/Exploit,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Exploit"})

(def Explore
  "Explore"
  {:db/ident   :mtg/Explore,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Explore"})

(def Extort
  "Extort"
  {:db/ident   :mtg/Extort,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Extort"})

(def Eye
  "Eye"
  {:db/ident   :mtg/Eye,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Eye"})

(def Fabacin
  "Fabacin"
  {:db/ident   :mtg/Fabacin,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Fabacin"})

(def Fabricate
  "Fabricate"
  {:db/ident   :mtg/Fabricate,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Fabricate"})

(def Fading
  "Fading"
  {:db/ident   :mtg/Fading,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Fading"})

(def Faerie
  "Faerie"
  {:db/ident   :mtg/Faerie,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Faerie"})

(def FatefulHour
  "Fateful hour"
  {:db/ident   :mtg/FatefulHour,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Fateful hour"})

(def Fateseal
  "Fateseal"
  {:db/ident   :mtg/Fateseal,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Fateseal"})

(def Fear
  "Fear"
  {:db/ident   :mtg/Fear,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Fear"})

(def Ferocious
  "Ferocious"
  {:db/ident   :mtg/Ferocious,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Ferocious"})

(def Ferret
  "Ferret"
  {:db/ident   :mtg/Ferret,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ferret"})

(def FifteenMana
  "{15}"
  {:db/ident   :mtg/FifteenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{15}"})

(def Fight
  "Fight"
  {:db/ident   :mtg/Fight,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Fight"})

(def Fiora
  "Fiora"
  {:db/ident   :mtg/Fiora,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Fiora"})

(def FirstStrike
  "First strike"
  {:db/ident   :mtg/FirstStrike,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "First strike"})

(def Fish
  "Fish"
  {:db/ident   :mtg/Fish,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Fish"})

(def FiveMana
  "{5}"
  {:db/ident   :mtg/FiveMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{5}"})

(def Flagbearer
  "Flagbearer"
  {:db/ident   :mtg/Flagbearer,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Flagbearer"})

(def Flanking
  "Flanking"
  {:db/ident   :mtg/Flanking,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Flanking"})

(def Flash
  "Flash"
  {:db/ident   :mtg/Flash,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Flash"})

(def Flashback
  "Flashback"
  {:db/ident   :mtg/Flashback,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Flashback"})

(def Flying
  "Flying"
  {:db/ident   :mtg/Flying,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Flying"})

(def Food
  "Food"
  {:db/ident   :mtg/Food,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Food"})

(def ForMirrodin
  "For Mirrodin!"
  {:db/ident   :mtg/ForMirrodin,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "For Mirrodin!"})

(def Forecast
  "Forecast"
  {:db/ident   :mtg/Forecast,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Forecast"})

(def Forest
  "Forest"
  {:db/ident   :mtg/Forest,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Forest"})

(def Forestcycling
  "Forestcycling"
  {:db/ident   :mtg/Forestcycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Forestcycling"})

(def Forestwalk
  "Forestwalk"
  {:db/ident   :mtg/Forestwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Forestwalk"})

(def Foretell
  "Foretell"
  {:db/ident   :mtg/Foretell,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Foretell"})

(def Formidable
  "Formidable"
  {:db/ident   :mtg/Formidable,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Formidable"})

(def Fortification
  "Fortification"
  {:db/ident   :mtg/Fortification,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Fortification"})

(def Fortify
  "Fortify"
  {:db/ident   :mtg/Fortify,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Fortify"})

(def FourMana
  "{4}"
  {:db/ident   :mtg/FourMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{4}"})

(def FourteenMana
  "{14}"
  {:db/ident   :mtg/FourteenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{14}"})

(def Fox
  "Fox"
  {:db/ident   :mtg/Fox,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Fox"})

(def Fractal
  "Fractal"
  {:db/ident   :mtg/Fractal,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Fractal"})

(def Frenzy
  "Frenzy"
  {:db/ident   :mtg/Frenzy,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Frenzy"})

(def Freyalise
  "Freyalise"
  {:db/ident   :mtg/Freyalise,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Freyalise"})

(def FriendsForever
  "Friends forever"
  {:db/ident   :mtg/FriendsForever,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Friends forever"})

(def Frog
  "Frog"
  {:db/ident   :mtg/Frog,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Frog"})

(def Fungus
  "Fungus"
  {:db/ident   :mtg/Fungus,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Fungus"})

(def Fuse
  "Fuse"
  {:db/ident   :mtg/Fuse,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Fuse"})

(def Gamer
  "Gamer"
  {:db/ident   :mtg/Gamer,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gamer"})

(def Gargantikar
  "Gargantikar"
  {:db/ident   :mtg/Gargantikar,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gargantikar"})

(def Gargoyle
  "Gargoyle"
  {:db/ident   :mtg/Gargoyle,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gargoyle"})

(def Garruk
  "Garruk"
  {:db/ident   :mtg/Garruk,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Garruk"})

(def Gate
  "Gate"
  {:db/ident   :mtg/Gate,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gate"})

(def Germ
  "Germ"
  {:db/ident   :mtg/Germ,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Germ"})

(def Giant
  "Giant"
  {:db/ident   :mtg/Giant,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Giant"})

(def Gideon
  "Gideon"
  {:db/ident   :mtg/Gideon,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gideon"})

(def Gith
  "Gith"
  {:db/ident   :mtg/Gith,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gith"})

(def Gnoll
  "Gnoll"
  {:db/ident   :mtg/Gnoll,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gnoll"})

(def Gnome
  "Gnome"
  {:db/ident   :mtg/Gnome,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gnome"})

(def Goad
  "Goad"
  {:db/ident   :mtg/Goad,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Goad"})

(def Goat
  "Goat"
  {:db/ident   :mtg/Goat,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Goat"})

(def Gobakhan
  "Gobakhan"
  {:db/ident   :mtg/Gobakhan,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gobakhan"})

(def Goblin
  "Goblin"
  {:db/ident   :mtg/Goblin,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Goblin"})

(def God
  "God"
  {:db/ident   :mtg/God,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "God"})

(def Gold
  "Gold"
  {:db/ident   :mtg/Gold,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gold"})

(def Golem
  "Golem"
  {:db/ident   :mtg/Golem,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Golem"})

(def Gorgon
  "Gorgon"
  {:db/ident   :mtg/Gorgon,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gorgon"})

(def Graft
  "Graft"
  {:db/ident   :mtg/Graft,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Graft"})

(def Grandeur
  "Grandeur"
  {:db/ident   :mtg/Grandeur,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Grandeur"})

(def Graveborn
  "Graveborn"
  {:db/ident   :mtg/Graveborn,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Graveborn"})

(def Gravestorm
  "Gravestorm"
  {:db/ident   :mtg/Gravestorm,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Gravestorm"})

(def Green
  "Green"
  {:db/ident   :mtg/Green,
   :rdf/type   [:mtg/Color :owl/NamedIndividual],
   :rdfs/label "Green"})

(def GreenBlueMana
  "{G/U}"
  {:db/ident   :mtg/GreenBlueMana,
   :rdf/type   [:mtg/Mana :mtg/Blue :mtg/Green :owl/NamedIndividual],
   :rdfs/label "{G/U}"})

(def GreenBluePhyrexianMana
  "{G/U/P}"
  {:db/ident   :mtg/GreenBluePhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Blue :mtg/Green :owl/NamedIndividual],
   :rdfs/label "{G/U/P}"})

(def GreenHybridMana
  "{2/G}"
  {:db/ident   :mtg/GreenHybridMana,
   :rdf/type   [:mtg/Mana :mtg/Green :owl/NamedIndividual],
   :rdfs/label "{2/G}"})

(def GreenMana
  "{G}"
  {:db/ident   :mtg/GreenMana,
   :rdf/type   [:mtg/Green :mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{G}"})

(def GreenPhyrexianMana
  "{G/P}"
  {:db/ident   :mtg/GreenPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Green :owl/NamedIndividual],
   :rdfs/label "{G/P}"})

(def GreenWhiteMana
  "{G/W}"
  {:db/ident   :mtg/GreenWhiteMana,
   :rdf/type   [:mtg/Mana :mtg/White :mtg/Green :owl/NamedIndividual],
   :rdfs/label "{G/W}"})

(def GreenWhitePhyrexianMana
  "{G/W/P}"
  {:db/ident   :mtg/GreenWhitePhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/White :mtg/Green :owl/NamedIndividual],
   :rdfs/label "{G/W/P}"})

(def Gremlin
  "Gremlin"
  {:db/ident   :mtg/Gremlin,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Gremlin"})

(def Griffin
  "Griffin"
  {:db/ident   :mtg/Griffin,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Griffin"})

(def Grist
  "Grist"
  {:db/ident   :mtg/Grist,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Grist"})

(def Guest
  "Guest"
  {:db/ident   :mtg/Guest,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Guest"})

(def Hag
  "Hag"
  {:db/ident   :mtg/Hag,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hag"})

(def Halfling
  "Halfling"
  {:db/ident   :mtg/Halfling,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Halfling"})

(def Hamster
  "Hamster"
  {:db/ident   :mtg/Hamster,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hamster"})

(def Harpy
  "Harpy"
  {:db/ident   :mtg/Harpy,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Harpy"})

(def Haste
  "Haste"
  {:db/ident   :mtg/Haste,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Haste"})

(def Haunt
  "Haunt"
  {:db/ident   :mtg/Haunt,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Haunt"})

(def Head
  "Head"
  {:db/ident   :mtg/Head,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Head"})

(def Hellbent
  "Hellbent"
  {:db/ident   :mtg/Hellbent,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Hellbent"})

(def Hellion
  "Hellion"
  {:db/ident   :mtg/Hellion,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hellion"})

(def Heroic
  "Heroic"
  {:db/ident   :mtg/Heroic,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Heroic"})

(def HerosReward
  "Hero's Reward"
  {:db/ident   :mtg/HerosReward,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Hero's Reward"})

(def Hexproof
  "Hexproof"
  {:db/ident   :mtg/Hexproof,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Hexproof"})

(def HexproofFrom
  "Hexproof from"
  {:db/ident   :mtg/HexproofFrom,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Hexproof from"})

(def HiddenAgenda
  "Hidden agenda"
  {:db/ident   :mtg/HiddenAgenda,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Hidden agenda"})

(def Hideaway
  "Hideaway"
  {:db/ident   :mtg/Hideaway,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Hideaway"})

(def Hippo
  "Hippo"
  {:db/ident   :mtg/Hippo,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hippo"})

(def Hippogriff
  "Hippogriff"
  {:db/ident   :mtg/Hippogriff,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hippogriff"})

(def Homarid
  "Homarid"
  {:db/ident   :mtg/Homarid,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Homarid"})

(def Homunculus
  "Homunculus"
  {:db/ident   :mtg/Homunculus,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Homunculus"})

(def Hornet
  "Hornet"
  {:db/ident   :mtg/Hornet,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hornet"})

(def Horror
  "Horror"
  {:db/ident   :mtg/Horror,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Horror"})

(def Horse
  "Horse"
  {:db/ident   :mtg/Horse,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Horse"})

(def Horsemanship
  "Horsemanship"
  {:db/ident   :mtg/Horsemanship,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Horsemanship"})

(def Huatli
  "Huatli"
  {:db/ident   :mtg/Huatli,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Huatli"})

(def Human
  "Human"
  {:db/ident   :mtg/Human,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Human"})

(def Hydra
  "Hydra"
  {:db/ident   :mtg/Hydra,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hydra"})

(def Hyena
  "Hyena"
  {:db/ident   :mtg/Hyena,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Hyena"})

(def Ikoria
  "Ikoria"
  {:db/ident   :mtg/Ikoria,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ikoria"})

(def Illusion
  "Illusion"
  {:db/ident   :mtg/Illusion,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Illusion"})

(def Imp
  "Imp"
  {:db/ident   :mtg/Imp,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Imp"})

(def Imprint
  "Imprint"
  {:db/ident   :mtg/Imprint,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Imprint"})

(def Improvise
  "Improvise"
  {:db/ident   :mtg/Improvise,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Improvise"})

(def Incarnation
  "Incarnation"
  {:db/ident   :mtg/Incarnation,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Incarnation"})

(def Indestructible
  "Indestructible"
  {:db/ident   :mtg/Indestructible,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Indestructible"})

(def Infect
  "Infect"
  {:db/ident   :mtg/Infect,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Infect"})

(def InfinityMana
  "{∞}"
  {:db/ident   :mtg/InfinityMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{∞}"})

(def Ingest
  "Ingest"
  {:db/ident   :mtg/Ingest,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Ingest"})

(def Inkling
  "Inkling"
  {:db/ident   :mtg/Inkling,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Inkling"})

(def Innistrad
  "Innistrad"
  {:db/ident   :mtg/Innistrad,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Innistrad"})

(def Inquisitor
  "Inquisitor"
  {:db/ident   :mtg/Inquisitor,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Inquisitor"})

(def Insect
  "Insect"
  {:db/ident   :mtg/Insect,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Insect"})

(def Inspired
  "Inspired"
  {:db/ident   :mtg/Inspired,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Inspired"})

(def Instant
  "Instant"
  {:db/ident            :mtg/Instant,
   :prov/wasDerivedFrom :mtg.rules/Instants,
   :rdf/type            :owl/Class,
   :rdfs/label          "Instant",
   :rdfs/subClassOf     :mtg/CardType})

(def Intensity
  "Intensity"
  {:db/ident   :mtg/Intensity,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Intensity"})

(def Intimidate
  "Intimidate"
  {:db/ident   :mtg/Intimidate,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Intimidate"})

(def Investigate
  "Investigate"
  {:db/ident   :mtg/Investigate,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Investigate"})

(def Inzerva
  "Inzerva"
  {:db/ident   :mtg/Inzerva,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Inzerva"})

(def Iquatana
  "Iquatana"
  {:db/ident   :mtg/Iquatana,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Iquatana"})

(def Ir
  "Ir"
  {:db/ident   :mtg/Ir,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ir"})

(def Island
  "Island"
  {:db/ident   :mtg/Island,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Island"})

(def Islandcycling
  "Islandcycling"
  {:db/ident   :mtg/Islandcycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Islandcycling"})

(def Islandwalk
  "Islandwalk"
  {:db/ident   :mtg/Islandwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Islandwalk"})

(def Ixalan
  "Ixalan"
  {:db/ident   :mtg/Ixalan,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ixalan"})

(def Jace
  "Jace"
  {:db/ident   :mtg/Jace,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Jace"})

(def Jackal
  "Jackal"
  {:db/ident   :mtg/Jackal,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Jackal"})

(def Jared
  "Jared"
  {:db/ident   :mtg/Jared,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Jared"})

(def Jaya
  "Jaya"
  {:db/ident   :mtg/Jaya,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Jaya"})

(def Jellyfish
  "Jellyfish"
  {:db/ident   :mtg/Jellyfish,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Jellyfish"})

(def Jeska
  "Jeska"
  {:db/ident   :mtg/Jeska,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Jeska"})

(def JoinForces
  "Join forces"
  {:db/ident   :mtg/JoinForces,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Join forces"})

(def Juggernaut
  "Juggernaut"
  {:db/ident   :mtg/Juggernaut,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Juggernaut"})

(def JumpStart
  "Jump-start"
  {:db/ident   :mtg/JumpStart,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Jump-start"})

(def Kaito
  "Kaito"
  {:db/ident   :mtg/Kaito,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kaito"})

(def Kaladesh
  "Kaladesh"
  {:db/ident   :mtg/Kaladesh,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kaladesh"})

(def Kaldheim
  "Kaldheim"
  {:db/ident   :mtg/Kaldheim,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kaldheim"})

(def Kamigawa
  "Kamigawa"
  {:db/ident   :mtg/Kamigawa,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kamigawa"})

(def Karn
  "Karn"
  {:db/ident   :mtg/Karn,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Karn"})

(def Karsus
  "Karsus"
  {:db/ident   :mtg/Karsus,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Karsus"})

(def Kasmina
  "Kasmina"
  {:db/ident   :mtg/Kasmina,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kasmina"})

(def Kavu
  "Kavu"
  {:db/ident   :mtg/Kavu,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kavu"})

(def Kaya
  "Kaya"
  {:db/ident   :mtg/Kaya,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kaya"})

(def Kephalai
  "Kephalai"
  {:db/ident   :mtg/Kephalai,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kephalai"})

(def KeywordAbility
  "Keyword Ability"
  {:db/ident            :mtg/KeywordAbility,
   :prov/wasDerivedFrom :mtg.rules/KeywordAbilities,
   :rdf/type            :owl/Class,
   :rdfs/label          "Keyword Ability",
   :skos/exactMatch     :mtg.rules/KeywordAbility})

(def KeywordAction
  "Keyword Action"
  {:db/ident   :mtg/KeywordAction,
   :prov/wasDerivedFrom :mtg.rules/KeywordActions,
   :rdf/type   :owl/Class,
   :rdfs/label "Keyword Action"})

(def Kicker
  "Kicker"
  {:db/ident   :mtg/Kicker,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Kicker"})

(def Kinfall
  "Kinfall"
  {:db/ident   :mtg/Kinfall,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Kinfall"})

(def Kinshala
  "Kinshala"
  {:db/ident   :mtg/Kinshala,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kinshala"})

(def Kinship
  "Kinship"
  {:db/ident   :mtg/Kinship,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Kinship"})

(def Kiora
  "Kiora"
  {:db/ident   :mtg/Kiora,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kiora"})

(def Kirin
  "Kirin"
  {:db/ident   :mtg/Kirin,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kirin"})

(def Kithkin
  "Kithkin"
  {:db/ident   :mtg/Kithkin,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kithkin"})

(def Knight
  "Knight"
  {:db/ident   :mtg/Knight,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Knight"})

(def Kobold
  "Kobold"
  {:db/ident   :mtg/Kobold,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kobold"})

(def Kolbahan
  "Kolbahan"
  {:db/ident   :mtg/Kolbahan,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kolbahan"})

(def Kor
  "Kor"
  {:db/ident   :mtg/Kor,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kor"})

(def Koth
  "Koth"
  {:db/ident   :mtg/Koth,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Koth"})

(def Kraken
  "Kraken"
  {:db/ident   :mtg/Kraken,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kraken"})

(def Kylem
  "Kylem"
  {:db/ident   :mtg/Kylem,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kylem"})

(def Kyneth
  "Kyneth"
  {:db/ident   :mtg/Kyneth,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Kyneth"})

(def Lair
  "Lair"
  {:db/ident   :mtg/Lair,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lair"})

(def Lamia
  "Lamia"
  {:db/ident   :mtg/Lamia,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lamia"})

(def Lammasu
  "Lammasu"
  {:db/ident   :mtg/Lammasu,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lammasu"})

(def Land
  "Land"
  {:db/ident            :mtg/Land,
   :prov/wasDerivedFrom :mtg.rules/Lands,
   :rdf/type            :owl/Class,
   :rdfs/label          "Land",
   :rdfs/subClassOf     :mtg/CardType})

(def Landcycling
  "Landcycling"
  {:db/ident   :mtg/Landcycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Landcycling"})

(def Landfall
  "Landfall"
  {:db/ident   :mtg/Landfall,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Landfall"})

(def Landship
  "Landship"
  {:db/ident   :mtg/Landship,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Landship"})

(def Landwalk
  "Landwalk"
  {:db/ident   :mtg/Landwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Landwalk"})

(def Learn
  "Learn"
  {:db/ident   :mtg/Learn,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Learn"})

(def Leech
  "Leech"
  {:db/ident   :mtg/Leech,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Leech"})

(def Legacy
  "Legacy"
  {:db/ident   :mtg/Legacy,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Legacy"})

(def Legendary
  "Legendary"
  {:db/ident            :mtg/Legendary,
   :prov/wasDerivedFrom :mtg.rules/|205_4d|,
   :rdf/type            :owl/Class,
   :rdfs/label          "Legendary",
   :rdfs/subClassOf     :mtg/Supertype})

(def LegendaryLandwalk
  "Legendary landwalk"
  {:db/ident   :mtg/LegendaryLandwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Legendary landwalk"})

(def Lesson
  "Lesson"
  {:db/ident   :mtg/Lesson,
   :rdf/type   [:mtg/Spell :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lesson"})

(def LevelUp
  "Level Up"
  {:db/ident   :mtg/LevelUp,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Level Up"})

(def Leviathan
  "Leviathan"
  {:db/ident   :mtg/Leviathan,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Leviathan"})

(def Lhurgoyf
  "Lhurgoyf"
  {:db/ident   :mtg/Lhurgoyf,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lhurgoyf"})

(def Licid
  "Licid"
  {:db/ident   :mtg/Licid,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Licid"})

(def Lieutenant
  "Lieutenant"
  {:db/ident   :mtg/Lieutenant,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Lieutenant"})

(def Lifelink
  "Lifelink"
  {:db/ident   :mtg/Lifelink,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Lifelink"})

(def Liliana
  "Liliana"
  {:db/ident   :mtg/Liliana,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Liliana"})

(def LivingMetal
  "Living metal"
  {:db/ident   :mtg/LivingMetal,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Living metal"})

(def LivingWeapon
  "Living weapon"
  {:db/ident   :mtg/LivingWeapon,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Living weapon"})

(def Lizard
  "Lizard"
  {:db/ident   :mtg/Lizard,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lizard"})

(def Locus
  "Locus"
  {:db/ident   :mtg/Locus,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Locus"})

(def Lolth
  "Lolth"
  {:db/ident   :mtg/Lolth,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lolth"})

(def Lorwyn
  "Lorwyn"
  {:db/ident   :mtg/Lorwyn,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lorwyn"})

(def Loyalty
  "loyalty"
  {:db/ident   :mtg/Loyalty,
   :rdf/type   :owl/Class,
   :rdfs/label "loyalty"})

(def Loyalty0
  "0"
  {:db/ident   :mtg/Loyalty0,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 0},
   :rdfs/label "0"})

(def Loyalty1
  "1"
  {:db/ident   :mtg/Loyalty1,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 1},
   :rdfs/label "1"})

(def Loyalty1d4Plus1
  "1d4+1"
  {:db/ident   :mtg/Loyalty1d4Plus1,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdfs/label "1d4+1"})

(def Loyalty2
  "2"
  {:db/ident   :mtg/Loyalty2,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 2},
   :rdfs/label "2"})

(def Loyalty20
  "20"
  {:db/ident   :mtg/Loyalty20,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 20},
   :rdfs/label "20"})

(def Loyalty3
  "3"
  {:db/ident   :mtg/Loyalty3,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 3},
   :rdfs/label "3"})

(def Loyalty4
  "4"
  {:db/ident   :mtg/Loyalty4,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 4},
   :rdfs/label "4"})

(def Loyalty5
  "5"
  {:db/ident   :mtg/Loyalty5,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 5},
   :rdfs/label "5"})

(def Loyalty6
  "6"
  {:db/ident   :mtg/Loyalty6,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 6},
   :rdfs/label "6"})

(def Loyalty7
  "7"
  {:db/ident   :mtg/Loyalty7,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdf/value  {:xsd/long 7},
   :rdfs/label "7"})

(def LoyaltyStar
  "*"
  {:db/ident   :mtg/LoyaltyStar,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdfs/label "*"})

(def LoyaltyX
  "X"
  {:db/ident   :mtg/LoyaltyX,
   :rdf/type   [:mtg/Loyalty :owl/NamedIndividual],
   :rdfs/label "X"})

(def Lukka
  "Lukka"
  {:db/ident   :mtg/Lukka,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Lukka"})

(def Luvion
  "Luvion"
  {:db/ident   :mtg/Luvion,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Luvion"})

(def Madness
  "Madness"
  {:db/ident   :mtg/Madness,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Madness"})

(def Magecraft
  "Magecraft"
  {:db/ident   :mtg/Magecraft,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Magecraft"})

(def Mana
  "Mana"
  {:db/ident   :mtg/Mana,
   :prov/wasDerivedFrom :mtg.rules/Mana,
   :rdf/type   :owl/Class,
   :rdfs/label "Mana"})

(def Manifest
  "Manifest"
  {:db/ident   :mtg/Manifest,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Manifest"})

(def Manticore
  "Manticore"
  {:db/ident   :mtg/Manticore,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Manticore"})

(def Master
  "Master"
  {:db/ident   :mtg/Master,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Master"})

(def Masticore
  "Masticore"
  {:db/ident   :mtg/Masticore,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Masticore"})

(def Megamorph
  "Megamorph"
  {:db/ident   :mtg/Megamorph,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Megamorph"})

(def Meld
  "Meld"
  {:db/ident   :mtg/Meld,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Meld"})

(def Melee
  "Melee"
  {:db/ident   :mtg/Melee,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Melee"})

(def Menace
  "Menace"
  {:db/ident   :mtg/Menace,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Menace"})

(def Mentor
  "Mentor"
  {:db/ident   :mtg/Mentor,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Mentor"})

(def Mercadia
  "Mercadia"
  {:db/ident   :mtg/Mercadia,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mercadia"})

(def Mercenary
  "Mercenary"
  {:db/ident   :mtg/Mercenary,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mercenary"})

(def Merfolk
  "Merfolk"
  {:db/ident   :mtg/Merfolk,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Merfolk"})

(def Metalcraft
  "Metalcraft"
  {:db/ident   :mtg/Metalcraft,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Metalcraft"})

(def Metathran
  "Metathran"
  {:db/ident   :mtg/Metathran,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Metathran"})

(def Mill
  "Mill"
  {:db/ident   :mtg/Mill,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Mill"})

(def Mine
  "Mine"
  {:db/ident   :mtg/Mine,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mine"})

(def Minion
  "Minion"
  {:db/ident   :mtg/Minion,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Minion"})

(def Minotaur
  "Minotaur"
  {:db/ident   :mtg/Minotaur,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Minotaur"})

(def Minsc
  "Minsc"
  {:db/ident   :mtg/Minsc,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Minsc"})

(def Miracle
  "Miracle"
  {:db/ident   :mtg/Miracle,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Miracle"})

(def Mirrodin
  "Mirrodin"
  {:db/ident   :mtg/Mirrodin,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mirrodin"})

(def Mite
  "Mite"
  {:db/ident   :mtg/Mite,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mite"})

(def Moag
  "Moag"
  {:db/ident   :mtg/Moag,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Moag"})

(def Modular
  "Modular"
  {:db/ident   :mtg/Modular,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Modular"})

(def Mole
  "Mole"
  {:db/ident   :mtg/Mole,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mole"})

(def Monger
  "Monger"
  {:db/ident   :mtg/Monger,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Monger"})

(def Mongoose
  "Mongoose"
  {:db/ident   :mtg/Mongoose,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mongoose"})

(def Mongseng
  "Mongseng"
  {:db/ident   :mtg/Mongseng,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mongseng"})

(def Monk
  "Monk"
  {:db/ident   :mtg/Monk,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Monk"})

(def Monkey
  "Monkey"
  {:db/ident   :mtg/Monkey,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Monkey"})

(def Monstrosity
  "Monstrosity"
  {:db/ident   :mtg/Monstrosity,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Monstrosity"})

(def Moonfolk
  "Moonfolk"
  {:db/ident   :mtg/Moonfolk,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Moonfolk"})

(def Morbid
  "Morbid"
  {:db/ident   :mtg/Morbid,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Morbid"})

(def Mordenkainen
  "Mordenkainen"
  {:db/ident   :mtg/Mordenkainen,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mordenkainen"})

(def MoreThanMeetsTheEye
  "More Than Meets the Eye"
  {:db/ident   :mtg/MoreThanMeetsTheEye,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "More Than Meets the Eye"})

(def Morph
  "Morph"
  {:db/ident   :mtg/Morph,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Morph"})

(def Mountain
  "Mountain"
  {:db/ident   :mtg/Mountain,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mountain"})

(def Mountaincycling
  "Mountaincycling"
  {:db/ident   :mtg/Mountaincycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Mountaincycling"})

(def Mountainwalk
  "Mountainwalk"
  {:db/ident   :mtg/Mountainwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Mountainwalk"})

(def Mouse
  "Mouse"
  {:db/ident   :mtg/Mouse,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mouse"})

(def Multikicker
  "Multikicker"
  {:db/ident   :mtg/Multikicker,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Multikicker"})

(def Muraganda
  "Muraganda"
  {:db/ident   :mtg/Muraganda,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Muraganda"})

(def Mutant
  "Mutant"
  {:db/ident   :mtg/Mutant,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mutant"})

(def Mutate
  "Mutate"
  {:db/ident   :mtg/Mutate,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Mutate"})

(def Myr
  "Myr"
  {:db/ident   :mtg/Myr,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Myr"})

(def Myriad
  "Myriad"
  {:db/ident   :mtg/Myriad,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Myriad"})

(def Mystic
  "Mystic"
  {:db/ident   :mtg/Mystic,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Mystic"})

(def Naga
  "Naga"
  {:db/ident   :mtg/Naga,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Naga"})

(def Nahiri
  "Nahiri"
  {:db/ident   :mtg/Nahiri,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nahiri"})

(def Narset
  "Narset"
  {:db/ident   :mtg/Narset,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Narset"})

(def Nautilus
  "Nautilus"
  {:db/ident   :mtg/Nautilus,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nautilus"})

(def Necron
  "Necron"
  {:db/ident   :mtg/Necron,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Necron"})

(def Nephilim
  "Nephilim"
  {:db/ident   :mtg/Nephilim,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nephilim"})

(def NewPhyrexia
  "New Phyrexia"
  {:db/ident   :mtg/NewPhyrexia,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "New Phyrexia"})

(def Nightbound
  "Nightbound"
  {:db/ident   :mtg/Nightbound,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Nightbound"})

(def Nightmare
  "Nightmare"
  {:db/ident   :mtg/Nightmare,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nightmare"})

(def Nightstalker
  "Nightstalker"
  {:db/ident   :mtg/Nightstalker,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nightstalker"})

(def Niko
  "Niko"
  {:db/ident   :mtg/Niko,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Niko"})

(def NineMana
  "{9}"
  {:db/ident   :mtg/NineMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{9}"})

(def NineteenMana
  "{19}"
  {:db/ident   :mtg/NineteenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{19}"})

(def Ninja
  "Ninja"
  {:db/ident   :mtg/Ninja,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ninja"})

(def Ninjutsu
  "Ninjutsu"
  {:db/ident   :mtg/Ninjutsu,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Ninjutsu"})

(def Nissa
  "Nissa"
  {:db/ident   :mtg/Nissa,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nissa"})

(def Nixilis
  "Nixilis"
  {:db/ident   :mtg/Nixilis,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nixilis"})

(def Noble
  "Noble"
  {:db/ident   :mtg/Noble,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Noble"})

(def Noggle
  "Noggle"
  {:db/ident   :mtg/Noggle,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Noggle"})

(def Nomad
  "Nomad"
  {:db/ident   :mtg/Nomad,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nomad"})

(def NonbasicLandwalk
  "Nonbasic landwalk"
  {:db/ident   :mtg/NonbasicLandwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Nonbasic landwalk"})

(def Nymph
  "Nymph"
  {:db/ident   :mtg/Nymph,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Nymph"})

(def Octopus
  "Octopus"
  {:db/ident   :mtg/Octopus,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Octopus"})

(def Offering
  "Offering"
  {:db/ident   :mtg/Offering,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Offering"})

(def Ogre
  "Ogre"
  {:db/ident   :mtg/Ogre,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ogre"})

(def Oko
  "Oko"
  {:db/ident   :mtg/Oko,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Oko"})

(def OneMana
  "{1}"
  {:db/ident   :mtg/OneMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{1}"})

(def Ongoing
  "Ongoing"
  {:db/ident            :mtg/Ongoing,
   :prov/wasDerivedFrom :mtg.rules/|205_4h|,
   :rdf/type            :owl/Class,
   :rdfs/label          "Ongoing",
   :rdfs/subClassOf     :mtg/Supertype})

(def Ooze
  "Ooze"
  {:db/ident   :mtg/Ooze,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ooze"})

(def OpenAnAttraction
  "Open an Attraction"
  {:db/ident   :mtg/OpenAnAttraction,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Open an Attraction"})

(def Orb
  "Orb"
  {:db/ident   :mtg/Orb,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Orb"})

(def Orc
  "Orc"
  {:db/ident   :mtg/Orc,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Orc"})

(def Orgg
  "Orgg"
  {:db/ident   :mtg/Orgg,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Orgg"})

(def Otter
  "Otter"
  {:db/ident   :mtg/Otter,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Otter"})

(def Ouphe
  "Ouphe"
  {:db/ident   :mtg/Ouphe,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ouphe"})

(def Outlast
  "Outlast"
  {:db/ident   :mtg/Outlast,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Outlast"})

(def Overload
  "Overload"
  {:db/ident   :mtg/Overload,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Overload"})

(def Ox
  "Ox"
  {:db/ident   :mtg/Ox,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ox"})

(def Oyster
  "Oyster"
  {:db/ident   :mtg/Oyster,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Oyster"})

(def PackTactics
  "Pack tactics"
  {:db/ident   :mtg/PackTactics,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Pack tactics"})

(def Pangolin
  "Pangolin"
  {:db/ident   :mtg/Pangolin,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pangolin"})

(def Parley
  "Parley"
  {:db/ident   :mtg/Parley,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Parley"})

(def Partner
  "Partner"
  {:db/ident   :mtg/Partner,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Partner"})

(def PartnerWith
  "Partner with"
  {:db/ident   :mtg/PartnerWith,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Partner with"})

(def Peasant
  "Peasant"
  {:db/ident   :mtg/Peasant,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Peasant"})

(def Pegasus
  "Pegasus"
  {:db/ident   :mtg/Pegasus,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pegasus"})

(def Pentavite
  "Pentavite"
  {:db/ident   :mtg/Pentavite,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pentavite"})

(def Performer
  "Performer"
  {:db/ident   :mtg/Performer,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Performer"})

(def Persist
  "Persist"
  {:db/ident   :mtg/Persist,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Persist"})

(def Pest
  "Pest"
  {:db/ident   :mtg/Pest,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pest"})

(def Phasing
  "Phasing"
  {:db/ident   :mtg/Phasing,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Phasing"})

(def Phelddagrif
  "Phelddagrif"
  {:db/ident   :mtg/Phelddagrif,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Phelddagrif"})

(def Phenomenon
  "Phenomenon"
  {:db/ident            :mtg/Phenomenon,
   :prov/wasDerivedFrom :mtg.rules/Phenomena,
   :rdf/type            :owl/Class,
   :rdfs/label          "Phenomenon",
   :rdfs/subClassOf     :mtg/CardType})

(def Phoenix
  "Phoenix"
  {:db/ident   :mtg/Phoenix,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Phoenix"})

(def Phyrexia
  "Phyrexia"
  {:db/ident   :mtg/Phyrexia,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Phyrexia"})

(def Phyrexian
  "Phyrexian"
  {:db/ident   :mtg/Phyrexian,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Phyrexian"})

(def PhyrexianMana
  "{P}"
  {:db/ident   :mtg/PhyrexianMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{P}"})

(def Pilot
  "Pilot"
  {:db/ident   :mtg/Pilot,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pilot"})

(def Pincher
  "Pincher"
  {:db/ident   :mtg/Pincher,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pincher"})

(def Pirate
  "Pirate"
  {:db/ident   :mtg/Pirate,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pirate"})

(def Plains
  "Plains"
  {:db/ident   :mtg/Plains,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Plains"})

(def Plainscycling
  "Plainscycling"
  {:db/ident   :mtg/Plainscycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Plainscycling"})

(def Plainswalk
  "Plainswalk"
  {:db/ident   :mtg/Plainswalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Plainswalk"})

(def Plane
  "Plane"
  {:db/ident            :mtg/Plane,
   :prov/wasDerivedFrom :mtg.rules/Planes,
   :rdf/type            :owl/Class,
   :rdfs/label          "Plane",
   :rdfs/subClassOf     :mtg/CardType})

(def Planeswalk
  "Planeswalk"
  {:db/ident   :mtg/Planeswalk,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Planeswalk"})

(def Planeswalker
  "Planeswalker"
  {:db/ident            :mtg/Planeswalker,
   :prov/wasDerivedFrom :mtg.rules/Planeswalkers,
   :rdf/type            :owl/Class,
   :rdfs/label          "Planeswalker",
   :rdfs/subClassOf     :mtg/CardType})

(def Plant
  "Plant"
  {:db/ident   :mtg/Plant,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Plant"})

(def Play
  "Play"
  {:db/ident   :mtg/Play,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Play"})

(def Poisonous
  "Poisonous"
  {:db/ident   :mtg/Poisonous,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Poisonous"})

(def Populate
  "Populate"
  {:db/ident   :mtg/Populate,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Populate"})

(def Power
  "power"
  {:db/ident   :mtg/Power,
   :rdf/type   :owl/Class,
   :rdfs/label "power"})

(def Power0
  "0"
  {:db/ident   :mtg/Power0,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 0},
   :rdfs/label "0"})

(def Power1
  "1"
  {:db/ident   :mtg/Power1,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 1},
   :rdfs/label "1"})

(def Power10
  "10"
  {:db/ident   :mtg/Power10,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 10},
   :rdfs/label "10"})

(def Power11
  "11"
  {:db/ident   :mtg/Power11,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 11},
   :rdfs/label "11"})

(def Power12
  "12"
  {:db/ident   :mtg/Power12,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 12},
   :rdfs/label "12"})

(def Power13
  "13"
  {:db/ident   :mtg/Power13,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 13},
   :rdfs/label "13"})

(def Power15
  "15"
  {:db/ident   :mtg/Power15,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 15},
   :rdfs/label "15"})

(def Power16
  "16"
  {:db/ident   :mtg/Power16,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 16},
   :rdfs/label "16"})

(def Power18
  "18"
  {:db/ident   :mtg/Power18,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 18},
   :rdfs/label "18"})

(def Power1Half
  "1.5"
  {:db/ident   :mtg/Power1Half,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 1.5M},
   :rdfs/label "1.5"})

(def Power1PlusStar
  "1+*"
  {:db/ident   :mtg/Power1PlusStar,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 1},
   :rdfs/label "1+*"})

(def Power2
  "2"
  {:db/ident   :mtg/Power2,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 2},
   :rdfs/label "2"})

(def Power20
  "20"
  {:db/ident   :mtg/Power20,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 20},
   :rdfs/label "20"})

(def Power2Half
  "2.5"
  {:db/ident   :mtg/Power2Half,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 2.5M},
   :rdfs/label "2.5"})

(def Power2PlusStar
  "2+*"
  {:db/ident   :mtg/Power2PlusStar,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 2},
   :rdfs/label "2+*"})

(def Power3
  "3"
  {:db/ident   :mtg/Power3,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 3},
   :rdfs/label "3"})

(def Power3Half
  "3.5"
  {:db/ident   :mtg/Power3Half,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 3.5M},
   :rdfs/label "3.5"})

(def Power4
  "4"
  {:db/ident   :mtg/Power4,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 4},
   :rdfs/label "4"})

(def Power5
  "5"
  {:db/ident   :mtg/Power5,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 5},
   :rdfs/label "5"})

(def Power6
  "6"
  {:db/ident   :mtg/Power6,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 6},
   :rdfs/label "6"})

(def Power7
  "7"
  {:db/ident   :mtg/Power7,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 7},
   :rdfs/label "7"})

(def Power8
  "8"
  {:db/ident   :mtg/Power8,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 8},
   :rdfs/label "8"})

(def Power9
  "9"
  {:db/ident   :mtg/Power9,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 9},
   :rdfs/label "9"})

(def Power99
  "99"
  {:db/ident   :mtg/Power99,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 99},
   :rdfs/label "99"})

(def PowerHalf
  ".5"
  {:db/ident   :mtg/PowerHalf,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 0.5M},
   :rdfs/label ".5"})

(def PowerInfinity
  "∞"
  {:db/ident   :mtg/PowerInfinity,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdfs/label "∞"})

(def PowerMinus1
  "-1"
  {:db/ident   :mtg/PowerMinus1,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long -1},
   :rdfs/label "-1"})

(def PowerPlant
  "Power-Plant"
  {:db/ident   :mtg/PowerPlant,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Power-Plant"})

(def PowerPlus0
  "+0"
  {:db/ident   :mtg/PowerPlus0,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 0},
   :rdfs/label "+0"})

(def PowerPlus1
  "+1"
  {:db/ident   :mtg/PowerPlus1,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 1},
   :rdfs/label "+1"})

(def PowerPlus2
  "+2"
  {:db/ident   :mtg/PowerPlus2,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 2},
   :rdfs/label "+2"})

(def PowerPlus3
  "+3"
  {:db/ident   :mtg/PowerPlus3,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 3},
   :rdfs/label "+3"})

(def PowerPlus4
  "+4"
  {:db/ident   :mtg/PowerPlus4,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdf/value  {:xsd/long 4},
   :rdfs/label "+4"})

(def PowerQuestionMark
  "?"
  {:db/ident   :mtg/PowerQuestionMark,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdfs/label "?"})

(def PowerStar
  "*"
  {:db/ident   :mtg/PowerStar,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdfs/label "*"})

(def PowerStar2
  "*²"
  {:db/ident   :mtg/PowerStar2,
   :rdf/type   [:mtg/Power :owl/NamedIndividual],
   :rdfs/label "*²"})

(def Powerstone
  "Powerstone"
  {:db/ident   :mtg/Powerstone,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Powerstone"})

(def Praetor
  "Praetor"
  {:db/ident   :mtg/Praetor,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Praetor"})

(def Primarch
  "Primarch"
  {:db/ident   :mtg/Primarch,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Primarch"})

(def Prism
  "Prism"
  {:db/ident   :mtg/Prism,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Prism"})

(def Processor
  "Processor"
  {:db/ident   :mtg/Processor,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Processor"})

(def Proliferate
  "Proliferate"
  {:db/ident   :mtg/Proliferate,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Proliferate"})

(def Protection
  "Protection"
  {:db/ident   :mtg/Protection,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Protection"})

(def Prototype
  "Prototype"
  {:db/ident   :mtg/Prototype,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Prototype"})

(def Provoke
  "Provoke"
  {:db/ident   :mtg/Provoke,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Provoke"})

(def Prowess
  "Prowess"
  {:db/ident   :mtg/Prowess,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Prowess"})

(def Prowl
  "Prowl"
  {:db/ident   :mtg/Prowl,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Prowl"})

(def Pyrulea
  "Pyrulea"
  {:db/ident   :mtg/Pyrulea,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Pyrulea"})

(def Rabbit
  "Rabbit"
  {:db/ident   :mtg/Rabbit,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rabbit"})

(def Rabiah
  "Rabiah"
  {:db/ident   :mtg/Rabiah,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rabiah"})

(def Raccoon
  "Raccoon"
  {:db/ident   :mtg/Raccoon,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Raccoon"})

(def Radiance
  "Radiance"
  {:db/ident   :mtg/Radiance,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Radiance"})

(def Raid
  "Raid"
  {:db/ident   :mtg/Raid,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Raid"})

(def Ral
  "Ral"
  {:db/ident   :mtg/Ral,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ral"})

(def Rally
  "Rally"
  {:db/ident   :mtg/Rally,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Rally"})

(def Rampage
  "Rampage"
  {:db/ident   :mtg/Rampage,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Rampage"})

(def Ranger
  "Ranger"
  {:db/ident   :mtg/Ranger,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ranger"})

(def Rat
  "Rat"
  {:db/ident   :mtg/Rat,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rat"})

(def Rath
  "Rath"
  {:db/ident   :mtg/Rath,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rath"})

(def Ravenous
  "Ravenous"
  {:db/ident   :mtg/Ravenous,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Ravenous"})

(def Ravnica
  "Ravnica"
  {:db/ident   :mtg/Ravnica,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ravnica"})

(def Reach
  "Reach"
  {:db/ident   :mtg/Reach,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Reach"})

(def ReadAhead
  "Read Ahead"
  {:db/ident   :mtg/ReadAhead,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Read Ahead"})

(def Rebel
  "Rebel"
  {:db/ident   :mtg/Rebel,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rebel"})

(def Rebound
  "Rebound"
  {:db/ident   :mtg/Rebound,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Rebound"})

(def Reconfigure
  "Reconfigure"
  {:db/ident   :mtg/Reconfigure,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Reconfigure"})

(def Recover
  "Recover"
  {:db/ident   :mtg/Recover,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Recover"})

(def Red
  "Red"
  {:db/ident   :mtg/Red,
   :rdf/type   [:mtg/Color :owl/NamedIndividual],
   :rdfs/label "Red"})

(def RedGreenMana
  "{R/G}"
  {:db/ident   :mtg/RedGreenMana,
   :rdf/type   [:mtg/Mana :mtg/Green :mtg/Red :owl/NamedIndividual],
   :rdfs/label "{R/G}"})

(def RedGreenPhyrexianMana
  "{R/G/P}"
  {:db/ident   :mtg/RedGreenPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Green :mtg/Red :owl/NamedIndividual],
   :rdfs/label "{R/G/P}"})

(def RedHybridMana
  "{2/R}"
  {:db/ident   :mtg/RedHybridMana,
   :rdf/type   [:mtg/Mana :mtg/Red :owl/NamedIndividual],
   :rdfs/label "{2/R}"})

(def RedMana
  "{R}"
  {:db/ident   :mtg/RedMana,
   :rdf/type   [:mtg/Red :mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{R}"})

(def RedPhyrexianMana
  "{R/P}"
  {:db/ident   :mtg/RedPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Red :owl/NamedIndividual],
   :rdfs/label "{R/P}"})

(def RedWhiteMana
  "{R/W}"
  {:db/ident   :mtg/RedWhiteMana,
   :rdf/type   [:mtg/Mana :mtg/White :mtg/Red :owl/NamedIndividual],
   :rdfs/label "{R/W}"})

(def RedWhitePhyrexianMana
  "{R/W/P}"
  {:db/ident   :mtg/RedWhitePhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/White :mtg/Red :owl/NamedIndividual],
   :rdfs/label "{R/W/P}"})

(def Reflection
  "Reflection"
  {:db/ident   :mtg/Reflection,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Reflection"})

(def Regatha
  "Regatha"
  {:db/ident   :mtg/Regatha,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Regatha"})

(def Regenerate
  "Regenerate"
  {:db/ident   :mtg/Regenerate,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Regenerate"})

(def Reinforce
  "Reinforce"
  {:db/ident   :mtg/Reinforce,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Reinforce"})

(def Renown
  "Renown"
  {:db/ident   :mtg/Renown,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Renown"})

(def Replicate
  "Replicate"
  {:db/ident   :mtg/Replicate,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Replicate"})

(def Retrace
  "Retrace"
  {:db/ident   :mtg/Retrace,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Retrace"})

(def Reveal
  "Reveal"
  {:db/ident   :mtg/Reveal,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Reveal"})

(def Reveler
  "Reveler"
  {:db/ident   :mtg/Reveler,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Reveler"})

(def Revolt
  "Revolt"
  {:db/ident   :mtg/Revolt,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Revolt"})

(def Rhino
  "Rhino"
  {:db/ident   :mtg/Rhino,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rhino"})

(def Rigger
  "Rigger"
  {:db/ident   :mtg/Rigger,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rigger"})

(def Riot
  "Riot"
  {:db/ident   :mtg/Riot,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Riot"})

(def Ripple
  "Ripple"
  {:db/ident   :mtg/Ripple,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Ripple"})

(def Robot
  "Robot"
  {:db/ident   :mtg/Robot,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Robot"})

(def Rogue
  "Rogue"
  {:db/ident   :mtg/Rogue,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rogue"})

(def RollToVisitYourAttractions
  "Roll to Visit Your Attractions"
  {:db/ident   :mtg/RollToVisitYourAttractions,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Roll to Visit Your Attractions"})

(def Rowan
  "Rowan"
  {:db/ident   :mtg/Rowan,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rowan"})

(def Rune
  "Rune"
  {:db/ident   :mtg/Rune,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Rune"})

(def Sable
  "Sable"
  {:db/ident   :mtg/Sable,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sable"})

(def Sacrifice
  "Sacrifice"
  {:db/ident   :mtg/Sacrifice,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Sacrifice"})

(def Saga
  "Saga"
  {:db/ident   :mtg/Saga,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Saga"})

(def Saheeli
  "Saheeli"
  {:db/ident   :mtg/Saheeli,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Saheeli"})

(def Salamander
  "Salamander"
  {:db/ident   :mtg/Salamander,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Salamander"})

(def Samurai
  "Samurai"
  {:db/ident   :mtg/Samurai,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Samurai"})

(def Samut
  "Samut"
  {:db/ident   :mtg/Samut,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Samut"})

(def Sand
  "Sand"
  {:db/ident   :mtg/Sand,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sand"})

(def Saproling
  "Saproling"
  {:db/ident   :mtg/Saproling,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Saproling"})

(def Sarkhan
  "Sarkhan"
  {:db/ident   :mtg/Sarkhan,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sarkhan"})

(def Satyr
  "Satyr"
  {:db/ident   :mtg/Satyr,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Satyr"})

(def Scarecrow
  "Scarecrow"
  {:db/ident   :mtg/Scarecrow,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Scarecrow"})

(def Scavenge
  "Scavenge"
  {:db/ident   :mtg/Scavenge,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Scavenge"})

(def Scheme
  "Scheme"
  {:db/ident            :mtg/Scheme,
   :prov/wasDerivedFrom :mtg.rules/Schemes,
   :rdf/type            :owl/Class,
   :rdfs/label          "Scheme",
   :rdfs/subClassOf     :mtg/CardType})

(def Scion
  "Scion"
  {:db/ident   :mtg/Scion,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Scion"})

(def Scorpion
  "Scorpion"
  {:db/ident   :mtg/Scorpion,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Scorpion"})

(def Scout
  "Scout"
  {:db/ident   :mtg/Scout,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Scout"})

(def Scry
  "Scry"
  {:db/ident   :mtg/Scry,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Scry"})

(def Sculpture
  "Sculpture"
  {:db/ident   :mtg/Sculpture,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sculpture"})

(def SecretCouncil
  "Secret Council"
  {:db/ident   :mtg/SecretCouncil,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Secret Council"})

(def Seek
  "Seek"
  {:db/ident   :mtg/Seek,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Seek"})

(def Segovia
  "Segovia"
  {:db/ident   :mtg/Segovia,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Segovia"})

(def Serf
  "Serf"
  {:db/ident   :mtg/Serf,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Serf"})

(def Serpent
  "Serpent"
  {:db/ident   :mtg/Serpent,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Serpent"})

(def Serra
  "Serra"
  {:db/ident   :mtg/Serra,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Serra"})

(def SerrasRealm
  "Serra’s Realm"
  {:db/ident   :mtg/SerrasRealm,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Serra’s Realm"})

(def Servo
  "Servo"
  {:db/ident   :mtg/Servo,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Servo"})

(def SetInMotion
  "Set in motion"
  {:db/ident   :mtg/SetInMotion,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Set in motion"})

(def SevenMana
  "{7}"
  {:db/ident   :mtg/SevenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{7}"})

(def SeventeenMana
  "{17}"
  {:db/ident   :mtg/SeventeenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{17}"})

(def Shade
  "Shade"
  {:db/ident   :mtg/Shade,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shade"})

(def Shadow
  "Shadow"
  {:db/ident   :mtg/Shadow,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Shadow"})

(def Shadowmoor
  "Shadowmoor"
  {:db/ident   :mtg/Shadowmoor,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shadowmoor"})

(def Shaman
  "Shaman"
  {:db/ident   :mtg/Shaman,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shaman"})

(def Shandalar
  "Shandalar"
  {:db/ident   :mtg/Shandalar,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shandalar"})

(def Shapeshifter
  "Shapeshifter"
  {:db/ident   :mtg/Shapeshifter,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shapeshifter"})

(def Shard
  "Shard"
  {:db/ident   :mtg/Shard,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shard"})

(def Shark
  "Shark"
  {:db/ident   :mtg/Shark,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shark"})

(def Sheep
  "Sheep"
  {:db/ident   :mtg/Sheep,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sheep"})

(def Shenmeng
  "Shenmeng"
  {:db/ident   :mtg/Shenmeng,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shenmeng"})

(def Shrine
  "Shrine"
  {:db/ident   :mtg/Shrine,
   :rdf/type   [:mtg/Enchantment :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Shrine"})

(def Shroud
  "Shroud"
  {:db/ident   :mtg/Shroud,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Shroud"})

(def Shuffle
  "Shuffle"
  {:db/ident   :mtg/Shuffle,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Shuffle"})

(def Siren
  "Siren"
  {:db/ident   :mtg/Siren,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Siren"})

(def Sivitri
  "Sivitri"
  {:db/ident   :mtg/Sivitri,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sivitri"})

(def SixMana
  "{6}"
  {:db/ident   :mtg/SixMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{6}"})

(def SixteenMana
  "{16}"
  {:db/ident   :mtg/SixteenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{16}"})

(def Skeleton
  "Skeleton"
  {:db/ident   :mtg/Skeleton,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Skeleton"})

(def Skulk
  "Skulk"
  {:db/ident   :mtg/Skulk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Skulk"})

(def Slith
  "Slith"
  {:db/ident   :mtg/Slith,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Slith"})

(def Sliver
  "Sliver"
  {:db/ident   :mtg/Sliver,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sliver"})

(def Slivercycling
  "Slivercycling"
  {:db/ident   :mtg/Slivercycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Slivercycling"})

(def Slug
  "Slug"
  {:db/ident   :mtg/Slug,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Slug"})

(def Snake
  "Snake"
  {:db/ident   :mtg/Snake,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Snake"})

(def Snow
  "Snow"
  {:db/ident            :mtg/Snow,
   :prov/wasDerivedFrom :mtg.rules/|205_4g|,
   :rdf/type            :owl/Class,
   :rdfs/label          "Snow",
   :rdfs/subClassOf     :mtg/Supertype})

(def SnowMana
  "{S}"
  {:db/ident   :mtg/SnowMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{S}"})

(def Soldier
  "Soldier"
  {:db/ident   :mtg/Soldier,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Soldier"})

(def Soltari
  "Soltari"
  {:db/ident   :mtg/Soltari,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Soltari"})

(def Sorcery
  "Sorcery"
  {:db/ident            :mtg/Sorcery,
   :prov/wasDerivedFrom :mtg.rules/Sorceries,
   :rdf/type            :owl/Class,
   :rdfs/label          "Sorcery",
   :rdfs/subClassOf     :mtg/CardType})

(def Sorin
  "Sorin"
  {:db/ident   :mtg/Sorin,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sorin"})

(def Soulbond
  "Soulbond"
  {:db/ident   :mtg/Soulbond,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Soulbond"})

(def Soulshift
  "Soulshift"
  {:db/ident   :mtg/Soulshift,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Soulshift"})

(def Spawn
  "Spawn"
  {:db/ident   :mtg/Spawn,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Spawn"})

(def Spectacle
  "Spectacle"
  {:db/ident   :mtg/Spectacle,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Spectacle"})

(def Specter
  "Specter"
  {:db/ident   :mtg/Specter,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Specter"})

(def Spell
  "Spell"
  {:db/ident            :mtg/Spell,
   :prov/wasDerivedFrom :mtg.rules/Spells,
   :rdf/type            :owl/Class,
   :rdfs/label          "Spell",
   :rdfs/subClassOf     :mtg/CardType})

(def SpellMastery
  "Spell mastery"
  {:db/ident   :mtg/SpellMastery,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Spell mastery"})

(def Spellshaper
  "Spellshaper"
  {:db/ident   :mtg/Spellshaper,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Spellshaper"})

(def Sphere
  "Sphere"
  {:db/ident   :mtg/Sphere,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sphere"})

(def Sphinx
  "Sphinx"
  {:db/ident   :mtg/Sphinx,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sphinx"})

(def Spider
  "Spider"
  {:db/ident   :mtg/Spider,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Spider"})

(def Spike
  "Spike"
  {:db/ident   :mtg/Spike,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Spike"})

(def Spirit
  "Spirit"
  {:db/ident   :mtg/Spirit,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Spirit"})

(def Splice
  "Splice"
  {:db/ident   :mtg/Splice,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Splice"})

(def Splinter
  "Splinter"
  {:db/ident   :mtg/Splinter,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Splinter"})

(def SplitSecond
  "Split second"
  {:db/ident   :mtg/SplitSecond,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Split second"})

(def Sponge
  "Sponge"
  {:db/ident   :mtg/Sponge,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Sponge"})

(def Squad
  "Squad"
  {:db/ident   :mtg/Squad,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Squad"})

(def Squid
  "Squid"
  {:db/ident   :mtg/Squid,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Squid"})

(def Squirrel
  "Squirrel"
  {:db/ident   :mtg/Squirrel,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Squirrel"})

(def Starfish
  "Starfish"
  {:db/ident   :mtg/Starfish,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Starfish"})

(def Storm
  "Storm"
  {:db/ident   :mtg/Storm,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Storm"})

(def Strive
  "Strive"
  {:db/ident   :mtg/Strive,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Strive"})

(def Subtype
  "Subtype"
  {:db/ident   :mtg/Subtype,
   :prov/wasDerivedFrom :mtg.rules/|205_3|,
   :rdf/type   :owl/Class,
   :rdfs/label "Subtype"})

(def Sunburst
  "Sunburst"
  {:db/ident   :mtg/Sunburst,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Sunburst"})

(def Supertype
  "Supertype"
  {:db/ident   :mtg/Supertype,
   :prov/wasDerivedFrom :mtg.rules/|205_4|,
   :rdf/type   :owl/Class,
   :rdfs/label "Supertype"})

(def Support
  "Support"
  {:db/ident   :mtg/Support,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Support"})

(def Surge
  "Surge"
  {:db/ident   :mtg/Surge,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Surge"})

(def Surrakar
  "Surrakar"
  {:db/ident   :mtg/Surrakar,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Surrakar"})

(def Surveil
  "Surveil"
  {:db/ident   :mtg/Surveil,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Surveil"})

(def Survivor
  "Survivor"
  {:db/ident   :mtg/Survivor,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Survivor"})

(def Suspend
  "Suspend"
  {:db/ident   :mtg/Suspend,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Suspend"})

(def Swamp
  "Swamp"
  {:db/ident   :mtg/Swamp,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Swamp"})

(def Swampcycling
  "Swampcycling"
  {:db/ident   :mtg/Swampcycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Swampcycling"})

(def Swampwalk
  "Swampwalk"
  {:db/ident   :mtg/Swampwalk,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Swampwalk"})

(def Sweep
  "Sweep"
  {:db/ident   :mtg/Sweep,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Sweep"})

(def Szat
  "Szat"
  {:db/ident   :mtg/Szat,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Szat"})

(def Tamiyo
  "Tamiyo"
  {:db/ident   :mtg/Tamiyo,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tamiyo"})

(def Tap
  "Tap"
  {:db/ident   :mtg/Tap,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Tap"})

(def Tarkir
  "Tarkir"
  {:db/ident   :mtg/Tarkir,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tarkir"})

(def Tasha
  "Tasha"
  {:db/ident   :mtg/Tasha,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tasha"})

(def Teamwork
  "Teamwork"
  {:db/ident   :mtg/Teamwork,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Teamwork"})

(def Teddy
  "Teddy"
  {:db/ident   :mtg/Teddy,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Teddy"})

(def Teferi
  "Teferi"
  {:db/ident   :mtg/Teferi,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Teferi"})

(def TemptingOffer
  "Tempting offer"
  {:db/ident   :mtg/TemptingOffer,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Tempting offer"})

(def TenMana
  "{10}"
  {:db/ident   :mtg/TenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{10}"})

(def Tentacle
  "Tentacle"
  {:db/ident   :mtg/Tentacle,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tentacle"})

(def Tetravite
  "Tetravite"
  {:db/ident   :mtg/Tetravite,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tetravite"})

(def Teyo
  "Teyo"
  {:db/ident   :mtg/Teyo,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Teyo"})

(def Tezzeret
  "Tezzeret"
  {:db/ident   :mtg/Tezzeret,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tezzeret"})

(def Thalakos
  "Thalakos"
  {:db/ident   :mtg/Thalakos,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Thalakos"})

(def TheAbyss
  "The Abyss"
  {:db/ident   :mtg/TheAbyss,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "The Abyss"})

(def TheRingTemptsYou
  "The Ring Tempts You"
  {:db/ident   :mtg/TheRingTemptsYou,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "The Ring Tempts You"})

(def Theros
  "Theros"
  {:db/ident   :mtg/Theros,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Theros"})

(def ThirteenMana
  "{13}"
  {:db/ident   :mtg/ThirteenMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{13}"})

(def Thopter
  "Thopter"
  {:db/ident   :mtg/Thopter,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Thopter"})

(def ThreeMana
  "{3}"
  {:db/ident   :mtg/ThreeMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{3}"})

(def Threshold
  "Threshold"
  {:db/ident   :mtg/Threshold,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Threshold"})

(def Thrull
  "Thrull"
  {:db/ident   :mtg/Thrull,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Thrull"})

(def Tibalt
  "Tibalt"
  {:db/ident   :mtg/Tibalt,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tibalt"})

(def Tiefling
  "Tiefling"
  {:db/ident   :mtg/Tiefling,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tiefling"})

(def TotemArmor
  "Totem armor"
  {:db/ident   :mtg/TotemArmor,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Totem armor"})

(def Toughness
  "toughness"
  {:db/ident   :mtg/Toughness,
   :rdf/type   :owl/Class,
   :rdfs/label "toughness"})

(def Toughness0
  "0"
  {:db/ident   :mtg/Toughness0,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 0},
   :rdfs/label "0"})

(def Toughness1
  "1"
  {:db/ident   :mtg/Toughness1,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 1},
   :rdfs/label "1"})

(def Toughness10
  "10"
  {:db/ident   :mtg/Toughness10,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 10},
   :rdfs/label "10"})

(def Toughness11
  "11"
  {:db/ident   :mtg/Toughness11,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 11},
   :rdfs/label "11"})

(def Toughness12
  "12"
  {:db/ident   :mtg/Toughness12,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 12},
   :rdfs/label "12"})

(def Toughness13
  "13"
  {:db/ident   :mtg/Toughness13,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 13},
   :rdfs/label "13"})

(def Toughness14
  "14"
  {:db/ident   :mtg/Toughness14,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 14},
   :rdfs/label "14"})

(def Toughness15
  "15"
  {:db/ident   :mtg/Toughness15,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 15},
   :rdfs/label "15"})

(def Toughness16
  "16"
  {:db/ident   :mtg/Toughness16,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 16},
   :rdfs/label "16"})

(def Toughness17
  "17"
  {:db/ident   :mtg/Toughness17,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 17},
   :rdfs/label "17"})

(def Toughness1Half
  "1.5"
  {:db/ident   :mtg/Toughness1Half,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 1.5M},
   :rdfs/label "1.5"})

(def Toughness1PlusStar
  "1+*"
  {:db/ident   :mtg/Toughness1PlusStar,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 1},
   :rdfs/label "1+*"})

(def Toughness2
  "2"
  {:db/ident   :mtg/Toughness2,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 2},
   :rdfs/label "2"})

(def Toughness20
  "20"
  {:db/ident   :mtg/Toughness20,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 20},
   :rdfs/label "20"})

(def Toughness2Half
  "2.5"
  {:db/ident   :mtg/Toughness2Half,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 2.5M},
   :rdfs/label "2.5"})

(def Toughness2PlusStar
  "2+*"
  {:db/ident   :mtg/Toughness2PlusStar,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 2},
   :rdfs/label "2+*"})

(def Toughness3
  "3"
  {:db/ident   :mtg/Toughness3,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 3},
   :rdfs/label "3"})

(def Toughness3Half
  "3.5"
  {:db/ident   :mtg/Toughness3Half,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 3.5M},
   :rdfs/label "3.5"})

(def Toughness4
  "4"
  {:db/ident   :mtg/Toughness4,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 4},
   :rdfs/label "4"})

(def Toughness5
  "5"
  {:db/ident   :mtg/Toughness5,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 5},
   :rdfs/label "5"})

(def Toughness6
  "6"
  {:db/ident   :mtg/Toughness6,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 6},
   :rdfs/label "6"})

(def Toughness7
  "7"
  {:db/ident   :mtg/Toughness7,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 7},
   :rdfs/label "7"})

(def Toughness7MinusStar
  "7-*"
  {:db/ident   :mtg/Toughness7MinusStar,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 7},
   :rdfs/label "7-*"})

(def Toughness8
  "8"
  {:db/ident   :mtg/Toughness8,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 8},
   :rdfs/label "8"})

(def Toughness9
  "9"
  {:db/ident   :mtg/Toughness9,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 9},
   :rdfs/label "9"})

(def Toughness99
  "99"
  {:db/ident   :mtg/Toughness99,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 99},
   :rdfs/label "99"})

(def ToughnessHalf
  ".5"
  {:db/ident   :mtg/ToughnessHalf,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/decimal 0.5M},
   :rdfs/label ".5"})

(def ToughnessMinus0
  "-0"
  {:db/ident   :mtg/ToughnessMinus0,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 0},
   :rdfs/label "-0"})

(def ToughnessMinus1
  "-1"
  {:db/ident   :mtg/ToughnessMinus1,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long -1},
   :rdfs/label "-1"})

(def ToughnessPlus0
  "+0"
  {:db/ident   :mtg/ToughnessPlus0,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 0},
   :rdfs/label "+0"})

(def ToughnessPlus1
  "+1"
  {:db/ident   :mtg/ToughnessPlus1,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 1},
   :rdfs/label "+1"})

(def ToughnessPlus2
  "+2"
  {:db/ident   :mtg/ToughnessPlus2,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 2},
   :rdfs/label "+2"})

(def ToughnessPlus3
  "+3"
  {:db/ident   :mtg/ToughnessPlus3,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 3},
   :rdfs/label "+3"})

(def ToughnessPlus4
  "+4"
  {:db/ident   :mtg/ToughnessPlus4,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdf/value  {:xsd/long 4},
   :rdfs/label "+4"})

(def ToughnessQuestionMark
  "?"
  {:db/ident   :mtg/ToughnessQuestionMark,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdfs/label "?"})

(def ToughnessStar
  "*"
  {:db/ident   :mtg/ToughnessStar,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdfs/label "*"})

(def ToughnessStar2
  "*²"
  {:db/ident   :mtg/ToughnessStar2,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdfs/label "*²"})

(def ToughnessStarPlus1
  "*+1"
  {:db/ident   :mtg/ToughnessStarPlus1,
   :rdf/type   [:mtg/Toughness :owl/NamedIndividual],
   :rdfs/label "*+1"})

(def Tower
  "Tower"
  {:db/ident   :mtg/Tower,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tower"})

(def Toxic
  "Toxic"
  {:db/ident   :mtg/Toxic,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Toxic"})

(def Training
  "Training"
  {:db/ident   :mtg/Training,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Training"})

(def Trample
  "Trample"
  {:db/ident   :mtg/Trample,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Trample"})

(def Transfigure
  "Transfigure"
  {:db/ident   :mtg/Transfigure,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Transfigure"})

(def Transform
  "Transform"
  {:db/ident   :mtg/Transform,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Transform"})

(def Transmute
  "Transmute"
  {:db/ident   :mtg/Transmute,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Transmute"})

(def Trap
  "Trap"
  {:db/ident   :mtg/Trap,
   :rdf/type   [:mtg/Spell :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Trap"})

(def Treasure
  "Treasure"
  {:db/ident   :mtg/Treasure,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Treasure"})

(def Treefolk
  "Treefolk"
  {:db/ident   :mtg/Treefolk,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Treefolk"})

(def Tribal
  "Tribal"
  {:db/ident            :mtg/Tribal,
   :prov/wasDerivedFrom :mtg.rules/Tribals,
   :rdf/type            :owl/Class,
   :rdfs/label          "Tribal",
   :rdfs/subClassOf     :mtg/CardType})

(def Tribute
  "Tribute"
  {:db/ident   :mtg/Tribute,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Tribute"})

(def Trilobite
  "Trilobite"
  {:db/ident   :mtg/Trilobite,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Trilobite"})

(def Triskelavite
  "Triskelavite"
  {:db/ident   :mtg/Triskelavite,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Triskelavite"})

(def Troll
  "Troll"
  {:db/ident   :mtg/Troll,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Troll"})

(def Turtle
  "Turtle"
  {:db/ident   :mtg/Turtle,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Turtle"})

(def TwelveMana
  "{12}"
  {:db/ident   :mtg/TwelveMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{12}"})

(def TwentyMana
  "{20}"
  {:db/ident   :mtg/TwentyMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{20}"})

(def TwoMana
  "{2}"
  {:db/ident   :mtg/TwoMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{2}"})

(def Typecycling
  "Typecycling"
  {:db/ident   :mtg/Typecycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Typecycling"})

(def Tyranid
  "Tyranid"
  {:db/ident   :mtg/Tyranid,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tyranid"})

(def Tyvar
  "Tyvar"
  {:db/ident   :mtg/Tyvar,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Tyvar"})

(def Ugin
  "Ugin"
  {:db/ident   :mtg/Ugin,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ugin"})

(def Ulgrotha
  "Ulgrotha"
  {:db/ident   :mtg/Ulgrotha,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Ulgrotha"})

(def Undaunted
  "Undaunted"
  {:db/ident   :mtg/Undaunted,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Undaunted"})

(def Underdog
  "Underdog"
  {:db/ident   :mtg/Underdog,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Underdog"})

(def Undergrowth
  "Undergrowth"
  {:db/ident   :mtg/Undergrowth,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Undergrowth"})

(def Undying
  "Undying"
  {:db/ident   :mtg/Undying,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Undying"})

(def Unearth
  "Unearth"
  {:db/ident   :mtg/Unearth,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Unearth"})

(def Unicorn
  "Unicorn"
  {:db/ident   :mtg/Unicorn,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Unicorn"})

(def Unleash
  "Unleash"
  {:db/ident   :mtg/Unleash,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Unleash"})

(def Untap
  "Untap"
  {:db/ident   :mtg/Untap,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Untap"})

(def Urza
  "Urza"
  {:db/ident   :mtg/Urza,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Urza"})

(def Urzas
  "Urza's"
  {:db/ident   :mtg/Urzas,
   :rdf/type   [:mtg/Land :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Urza's"})

(def Valla
  "Valla"
  {:db/ident   :mtg/Valla,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Valla"})

(def Vampire
  "Vampire"
  {:db/ident   :mtg/Vampire,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Vampire"})

(def Vanguard
  "Vanguard"
  {:db/ident            :mtg/Vanguard,
   :prov/wasDerivedFrom :mtg.rules/Vanguards,
   :rdf/type            :owl/Class,
   :rdfs/label          "Vanguard",
   :rdfs/subClassOf     :mtg/CardType})

(def Vanishing
  "Vanishing"
  {:db/ident   :mtg/Vanishing,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Vanishing"})

(def VariableMana
  "{X}"
  {:db/ident   :mtg/VariableMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{X}"})

(def Vedalken
  "Vedalken"
  {:db/ident   :mtg/Vedalken,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Vedalken"})

(def Vehicle
  "Vehicle"
  {:db/ident   :mtg/Vehicle,
   :rdf/type   [:mtg/Artifact :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Vehicle"})

(def Venser
  "Venser"
  {:db/ident   :mtg/Venser,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Venser"})

(def VentureIntoTheDungeon
  "Venture into the dungeon"
  {:db/ident   :mtg/VentureIntoTheDungeon,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Venture into the dungeon"})

(def Viashino
  "Viashino"
  {:db/ident   :mtg/Viashino,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Viashino"})

(def Vigilance
  "Vigilance"
  {:db/ident   :mtg/Vigilance,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Vigilance"})

(def Vivien
  "Vivien"
  {:db/ident   :mtg/Vivien,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Vivien"})

(def Volver
  "Volver"
  {:db/ident   :mtg/Volver,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Volver"})

(def Vote
  "Vote"
  {:db/ident   :mtg/Vote,
   :rdf/type   [:mtg/KeywordAction :owl/NamedIndividual],
   :rdfs/label "Vote"})

(def Vraska
  "Vraska"
  {:db/ident   :mtg/Vraska,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Vraska"})

(def Vryn
  "Vryn"
  {:db/ident   :mtg/Vryn,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Vryn"})

(def Wall
  "Wall"
  {:db/ident   :mtg/Wall,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wall"})

(def Walrus
  "Walrus"
  {:db/ident   :mtg/Walrus,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Walrus"})

(def Ward
  "Ward"
  {:db/ident   :mtg/Ward,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Ward"})

(def Warlock
  "Warlock"
  {:db/ident   :mtg/Warlock,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Warlock"})

(def Warrior
  "Warrior"
  {:db/ident   :mtg/Warrior,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Warrior"})

(def Wasp
  "Wasp"
  {:db/ident   :mtg/Wasp,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wasp"})

(def Weird
  "Weird"
  {:db/ident   :mtg/Weird,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Weird"})

(def Werewolf
  "Werewolf"
  {:db/ident   :mtg/Werewolf,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Werewolf"})

(def Whale
  "Whale"
  {:db/ident   :mtg/Whale,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Whale"})

(def White
  "White"
  {:db/ident   :mtg/White,
   :rdf/type   [:mtg/Color :owl/NamedIndividual],
   :rdfs/label "White"})

(def WhiteBlackMana
  "{W/B}"
  {:db/ident   :mtg/WhiteBlackMana,
   :rdf/type   [:mtg/Mana :mtg/Black :mtg/White :owl/NamedIndividual],
   :rdfs/label "{W/B}"})

(def WhiteBlackPhyrexianMana
  "{W/B/P}"
  {:db/ident   :mtg/WhiteBlackPhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Black :mtg/White :owl/NamedIndividual],
   :rdfs/label "{W/B/P}"})

(def WhiteBlueMana
  "{W/U}"
  {:db/ident   :mtg/WhiteBlueMana,
   :rdf/type   [:mtg/Mana :mtg/Blue :mtg/White :owl/NamedIndividual],
   :rdfs/label "{W/U}"})

(def WhiteBluePhyrexianMana
  "{W/U/P}"
  {:db/ident   :mtg/WhiteBluePhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/Blue :mtg/White :owl/NamedIndividual],
   :rdfs/label "{W/U/P}"})

(def WhiteHybridMana
  "{2/W}"
  {:db/ident   :mtg/WhiteHybridMana,
   :rdf/type   [:mtg/Mana :mtg/White :owl/NamedIndividual],
   :rdfs/label "{2/W}"})

(def WhiteMana
  "{W}"
  {:db/ident   :mtg/WhiteMana,
   :rdf/type   [:mtg/White :mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{W}"})

(def WhitePhyrexianMana
  "{W/P}"
  {:db/ident   :mtg/WhitePhyrexianMana,
   :rdf/type   [:mtg/PhyrexianMana :mtg/White :owl/NamedIndividual],
   :rdfs/label "{W/P}"})

(def Wildfire
  "Wildfire"
  {:db/ident   :mtg/Wildfire,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wildfire"})

(def Will
  "Will"
  {:db/ident   :mtg/Will,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Will"})

(def WillOfTheCouncil
  "Will of the council"
  {:db/ident   :mtg/WillOfTheCouncil,
   :rdf/type   [:mtg/AbilityWord :owl/NamedIndividual],
   :rdfs/label "Will of the council"})

(def Windgrace
  "Windgrace"
  {:db/ident   :mtg/Windgrace,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Windgrace"})

(def Wither
  "Wither"
  {:db/ident   :mtg/Wither,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Wither"})

(def Wizard
  "Wizard"
  {:db/ident   :mtg/Wizard,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wizard"})

(def Wizardcycling
  "Wizardcycling"
  {:db/ident   :mtg/Wizardcycling,
   :rdf/type   [:mtg/KeywordAbility :owl/NamedIndividual],
   :rdfs/label "Wizardcycling"})

(def Wolf
  "Wolf"
  {:db/ident   :mtg/Wolf,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wolf"})

(def Wolverine
  "Wolverine"
  {:db/ident   :mtg/Wolverine,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wolverine"})

(def Wombat
  "Wombat"
  {:db/ident   :mtg/Wombat,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wombat"})

(def World
  "World"
  {:db/ident            :mtg/World,
   :prov/wasDerivedFrom :mtg.rules/|205_4f|,
   :rdf/type            :owl/Class,
   :rdfs/label          "World",
   :rdfs/subClassOf     :mtg/Supertype})

(def Worm
  "Worm"
  {:db/ident   :mtg/Worm,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Worm"})

(def Wraith
  "Wraith"
  {:db/ident   :mtg/Wraith,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wraith"})

(def Wrenn
  "Wrenn"
  {:db/ident   :mtg/Wrenn,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wrenn"})

(def Wurm
  "Wurm"
  {:db/ident   :mtg/Wurm,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Wurm"})

(def Xenagos
  "Xenagos"
  {:db/ident   :mtg/Xenagos,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Xenagos"})

(def Xerex
  "Xerex"
  {:db/ident   :mtg/Xerex,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Xerex"})

(def Yanggu
  "Yanggu"
  {:db/ident   :mtg/Yanggu,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Yanggu"})

(def Yanling
  "Yanling"
  {:db/ident   :mtg/Yanling,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Yanling"})

(def Yeti
  "Yeti"
  {:db/ident   :mtg/Yeti,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Yeti"})

(def Zariel
  "Zariel"
  {:db/ident   :mtg/Zariel,
   :rdf/type   [:mtg/Planeswalker :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Zariel"})

(def Zendikar
  "Zendikar"
  {:db/ident   :mtg/Zendikar,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Zendikar"})

(def ZeroMana
  "{0}"
  {:db/ident   :mtg/ZeroMana,
   :rdf/type   [:mtg/Mana :owl/NamedIndividual],
   :rdfs/label "{0}"})

(def Zhalfir
  "Zhalfir"
  {:db/ident   :mtg/Zhalfir,
   :rdf/type   [:mtg/Plane :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Zhalfir"})

(def Zombie
  "Zombie"
  {:db/ident   :mtg/Zombie,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Zombie"})

(def Zubera
  "Zubera"
  {:db/ident   :mtg/Zubera,
   :rdf/type   [:mtg/Creature :owl/NamedIndividual :mtg/CardType],
   :rdfs/label "Zubera"})

(def color
  "color"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :mtg/color,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "color",
   :rdfs/range     :mtg/Color})

(def colorIndicator
  "color indicator"
  {:db/cardinality      :db.cardinality/many,
   :db/ident            :mtg/colorIndicator,
   :db/valueType        :db.type/ref,
   :prov/wasDerivedFrom :mtg.rules/ColorIndicator,
   :rdf/type            :owl/ObjectProperty,
   :rdfs/domain         :mtg/Card,
   :rdfs/label          "color indicator",
   :rdfs/range          :mtg/Color})

(def defense
  "defense"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :mtg/defense,
   :db/valueType   :db.type/long,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "defense",
   :rdfs/range     :xsd/integer})

(def flavorText
  "flavorText"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :mtg/flavorText,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "flavorText",
   :rdfs/range     :xsd/string})

(def keywordAbility
  "keyword ability"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :mtg/keywordAbility,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "keyword ability",
   :rdfs/range     :mtg/KeywordAbility})

(def keywordAction
  "keyword action"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :mtg/keywordAction,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "keyword action",
   :rdfs/range     :mtg/KeywordAction})

(def loyalty
  "loyalty"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :mtg/loyalty,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "loyalty",
   :rdfs/range     :mtg/Loyalty})

(def manaCost
  "mana cost"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :mtg/manaCost,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "mana cost",
   :rdfs/range     :rdf/List})

(def name
  "name"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :mtg/name,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "name",
   :rdfs/range     :xsd/string})

(def oracleText
  "oracleText"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :mtg/oracleText,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/DatatypeProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "oracleText",
   :rdfs/range     :xsd/string})

(def power
  "power"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :mtg/power,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "power",
   :rdfs/range     :mtg/Power})

(def set
  "set code"
  {:db/cardinality      :db.cardinality/one,
   :db/ident            :mtg/set,
   :db/valueType        :db.type/string,
   :prov/wasDerivedFrom :mtg.rules/ExpansionSymbol,
   :rdf/type            :owl/DatatypeProperty,
   :rdfs/domain         :mtg/Card,
   :rdfs/label          "set code",
   :rdfs/range          :xsd/string})

(def subtype
  "subtype"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :mtg/subtype,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "subtype",
   :rdfs/range     :owl/Thing})

(def supertype
  "supertype"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :mtg/supertype,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "supertype",
   :rdfs/range     :mtg/Supertype})

(def toughness
  "toughness"
  {:db/cardinality :db.cardinality/one,
   :db/ident       :mtg/toughness,
   :db/valueType   :db.type/string,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "toughness",
   :rdfs/range     :mtg/Toughness})

(def type
  "type"
  {:db/cardinality :db.cardinality/many,
   :db/ident       :mtg/type,
   :db/valueType   :db.type/ref,
   :rdf/type       :owl/ObjectProperty,
   :rdfs/domain    :mtg/Card,
   :rdfs/label     "type",
   :rdfs/range     :mtg/CardType})