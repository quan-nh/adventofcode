(ns adventofcode2017.day11
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (str/split (slurp (io/resource "2017/day11")) #","))

;; ref https://www.redblobgames.com/grids/hexagons/
(defn move [[x y z] dir]
  (case dir
    "n" [x (inc y) (dec z)]
    "ne" [(inc x) y (dec z)]
    "se" [(inc x) (dec y) z]
    "s" [x (dec y) (inc z)]
    "sw" [(dec x) y (inc z)]
    "nw" [(dec x) (inc y) z]))

(defn steps [p]
  (apply max (map #(Math/abs %) p)))

;; part 1
(steps (reduce move [0 0 0] input))
; 764

(->> (reductions move [0 0 0] input)
     (map steps)
     (apply max))
; 1532
