(ns adventofcode.day16
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :refer [subset?]]))

(def input
  (line-seq (io/reader (io/resource "day16"))))

(def ticker-tape
  {'children    3
   'cats        7
   'samoyeds    2
   'pomeranians 3
   'akitas      0
   'vizslas     0
   'goldfish    5
   'trees       3
   'cars        2
   'perfumes    1})

(defn string->map [compounds]
  (read-string (str "{"
                    (str/replace compounds #":" "")
                    "}")))

(defn submap? [a b]
  (subset? (set a) (set b)))

(defn match? [a]
  (and (or (nil? (a 'children))
           (= 3 (a 'children)))
       (or (nil? (a 'cats))
           (> (a 'cats) 7))
       (or (nil? (a 'samoyeds))
           (= 2 (a 'samoyeds)))
       (or (nil? (a 'pomeranians))
           (< (a 'pomeranians) 3))
       (or (nil? (a 'akitas))
           (= 0 (a 'akitas)))
       (or (nil? (a 'vizslas))
           (= 0 (a 'vizslas)))
       (or (nil? (a 'goldfish))
           (< (a 'goldfish) 5))
       (or (nil? (a 'trees))
           (> (a 'trees) 3))
       (or (nil? (a 'cars))
           (= 2 (a 'cars)))
       (or (nil? (a 'perfumes))
           (= 1 (a 'perfumes)))))

(def sues
  (->> input
       (map #(let [[_ no compounds] (re-find #"^Sue (\d+): (.+)$" %)]
              [no (string->map compounds)]))
       (into {})))

(filter (fn [[_ v]] (submap? v ticker-tape)) sues)
(filter (fn [[_ v]] (match? v)) sues)

