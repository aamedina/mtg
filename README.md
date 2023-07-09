# net.wikipunk/mtg
Unofficial Magic: The Gathering RDF vocabulary

![plotoptix render](resources/mtg.png)

## :rdfs/seeAlso
* [Scryfall](https://scryfall.com/docs/api)
* [Comprehensive
  Rules](https://magic.wizards.com/en/rules)
* [Wizards of the Coast's Fan Content Policy](https://company.wizards.com/en/legal/fancontentpolicy)

## :dev

``` shell
clojure -A:dev
```

``` clojure
(reset)
```

## :usage

``` clojure
(require '[net.wikipunk.mtg :as mtg])
(mtg/text-search "blue phyrexian")
;; queries are passed to the standard lucene analyzer 
(mtg/text-search "elesh~")
;; use pr-str to escape literal strings 
(mtg/text-search (pr-str "{W}"))
```

## License
Copyright (c) 2023 Adrian Medina

Permission to use, copy, modify, and/or distribute this software for
any purpose with or without fee is hereby granted, provided that the
above copyright notice and this permission notice appear in all
copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL
WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL
DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR
PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
PERFORMANCE OF THIS SOFTWARE.
