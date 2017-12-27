(ns adventofcode2017.day09-instaparse
  (:require [instaparse.core :as insta]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day09")))

(def p
  (insta/parser
    "group = <'{'> (stuff (<','> stuff)*)? <'}'>
     <stuff> = group | garbage
     garbage = <'<'> (CHARS | <IGNORED>)* <'>'>
     <CHARS> = #'[^!>]+'
     IGNORED = #'!.'"))

(p "<>" :start :garbage)
; [:garbage]
(p "<random characters>" :start :garbage)
; [:garbage "random characters"]
(p "<<<<>" :start :garbage)
; [:garbage "<<<"]
(p "<{!>}>" :start :garbage)
; [:garbage "{" "}"]
(p "<!!>" :start :garbage)
; [:garbage]
(p "<!!!>>" :start :garbage)
; [:garbage]
(p "<{o\"i!a,<{i<a>" :start :garbage)
; [:garbage "{o\"i" ",<{i<a"]

(p "{}")
; [:group]
(p "{{{}}}")
; [:group [:group [:group]]]
(p "{{},{}}")
; [:group [:group] [:group]]
(p "{{{},{},{{}}}}")
; [:group [:group [:group] [:group] [:group [:group]]]]
(p "{<{},{},{{}}>}")
; [:group [:garbage "{},{},{{}}"]]
(p "{<a>,<a>,<a>,<a>}")
; [:group [:garbage "a"] [:garbage "a"] [:garbage "a"] [:garbage "a"]]
(p "{{<a>},{<a>},{<a>},{<a>}}")
; [:group
;  [:group [:garbage "a"]]
;  [:group [:garbage "a"]]
;  [:group [:garbage "a"]]
;  [:group [:garbage "a"]]]
(p "{{<!>},{<!>},{<!>},{<a>}}")
; [:group [:group [:garbage "},{<" "},{<" "},{<a"]]]

(def tree (p input))

;; part 1
(defn score [level [node & children]]
  (if (= :group node)
    (apply + level (map (partial score (inc level)) children))
    0))

(score 1 tree)
; 14204

;; part 2
(insta/transform
  {:garbage (comp count str)
   :group   +}
  tree)
; 6622
