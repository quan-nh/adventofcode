(ns adventofcode2017.day1
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day1")))

(->> (str input (first input))
     (partition 2 1)
     (filter (fn [[a b]] (= a b)))
     (map (fn [[a _]] (Character/getNumericValue a)))
     (reduce +))
; 1253

(let [step (/ (count input) 2)]
  (->> (str input (subs input 0 step))
       (partition (inc step) 1)
       (filter #(= (first %) (last %)))
       (map #(Character/getNumericValue (first %)))
       (reduce +)))
; 1278
