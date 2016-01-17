(ns adventofcode.day16
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :refer [subset?]]))

(def input
  (line-seq (io/reader (io/resource "day16"))))

(def ticker-tape
  {(symbol "children")    3
   (symbol "cats")        7
   (symbol "samoyeds")    2
   (symbol "pomeranians") 3
   (symbol "akitas")      0
   (symbol "vizslas")     0
   (symbol "goldfish")    5
   (symbol "trees")       3
   (symbol "cars")        2
   (symbol "perfumes")    1})

(defn string->map [compounds]
  (read-string (str "{"
                    (str/replace compounds #":" "")
                    "}")))

(defn submap? [a b]
  (subset? (set a) (set b)))

(defn match? [a]
  (and (or (nil? (a (symbol "children")))
           (= 3 (a (symbol "children"))))
       (or (nil? (a (symbol "cats")))
           (> (a (symbol "cats")) 7))
       (or (nil? (a (symbol "samoyeds")))
           (= 2 (a (symbol "samoyeds"))))
       (or (nil? (a (symbol "pomeranians")))
           (< (a (symbol "pomeranians")) 3))
       (or (nil? (a (symbol "akitas")))
           (= 0 (a (symbol "akitas"))))
       (or (nil? (a (symbol "vizslas")))
           (= 0 (a (symbol "vizslas"))))
       (or (nil? (a (symbol "goldfish")))
           (< (a (symbol "goldfish")) 5))
       (or (nil? (a (symbol "trees")))
           (> (a (symbol "trees")) 3))
       (or (nil? (a (symbol "cars")))
           (= 2 (a (symbol "cars"))))
       (or (nil? (a (symbol "perfumes")))
           (= 1 (a (symbol "perfumes"))))))

(def sues
  (->> input
       (map #(let [[_ no compounds] (re-find #"^Sue (\d+): (.+)$" %)]
              [no (string->map compounds)]))
       (into {})))

(filter (fn [[_ v]] (submap? v ticker-tape)) sues)
(filter (fn [[_ v]] (match? v)) sues)

