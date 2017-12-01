(ns adventofcode2017.day1
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day1")))

(reduce + (map (fn [a b]
                 (if (= a b)
                   (Integer/parseInt (str a))
                   0))
               input
               (drop 1 (cycle input))))
; 1253

(let [half (/ (count input) 2)]
  (reduce + (map (fn [a b]
                   (if (= a b)
                     (Integer/parseInt (str a))
                     0))
                 input
                 (drop half (cycle input)))))
; 1278
