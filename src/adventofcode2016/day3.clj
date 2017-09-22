(ns adventofcode2016.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2016/day3")))

(defn possible? [[a b c]]
  (and (> (+ a b) c)
       (> (+ a c) b)
       (> (+ b c) a)))

(defn parse-triangle [triangle]
  (let [[a b c] (str/split (str/trim triangle) #"\s+")]
    [(read-string a) (read-string b) (read-string c)]))

(comment
  ;; part1
  (->> (str/split-lines input)
       (map parse-triangle)
       (filter possible?)
       count))

(comment
  ;; part2
  (->> (str/split-lines input)
       (map parse-triangle)
       (apply mapcat vector)
       (partition 3)
       (filter possible?)
       count))
