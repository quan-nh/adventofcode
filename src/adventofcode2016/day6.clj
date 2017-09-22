(ns adventofcode2016.day6
  (:require [adventofcode2016.util :as util]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (-> (io/resource "2016/day6")
               slurp
               str/split-lines))

(comment
  ;; part1
  (->> input
       (apply map vector)
       (map (comp key first util/sorted-map-by-vals frequencies))
       (apply str)))

(comment
  ;; part2
  (->> input
       (apply map vector)
       (map (comp key last util/sorted-map-by-vals frequencies))
       (apply str)))