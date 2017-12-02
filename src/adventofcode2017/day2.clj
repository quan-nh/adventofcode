(ns adventofcode2017.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day2")))

(def data (->> (str/split-lines input)
               (map #(str/split % #"\t"))
               (map (fn [a] (map #(Integer/parseInt %) a)))))

;; part 1
(->> data
     (map (fn [a] (- (apply max a)
                     (apply min a))))
     (reduce +))
; 50376

;; part 2
(defn divide [coll]
  (first (for [i coll
               j coll
               :when (> i j)
               :when (zero? (mod i j))]
           (/ i j))))

(->> data
     (map divide)
     (reduce +))
; 267
