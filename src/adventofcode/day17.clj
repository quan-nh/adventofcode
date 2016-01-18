(ns adventofcode.day17
  (:require [clojure.math.combinatorics :as combo]))

(def input [33 14 18 20 45 35 16 35 1 13 18 13 50 44 48 6 24 41 30 42])

(->> (map-indexed vector input)
     combo/subsets
     (filter #(= 150 (reduce + (map second %))))
     count)

(->> (map-indexed vector input)
     combo/subsets
     (filter #(= 150 (reduce + (map second %))))
     (map count)
     frequencies)


