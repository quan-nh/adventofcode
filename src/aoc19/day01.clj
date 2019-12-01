(ns aoc19.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (->> (io/resource "2019/day01")
                slurp
                str/split-lines
                (map #(Integer/valueOf %))))

(defn fuel [mass] (- (quot mass 3) 2))

(defn total-fuel [mass]
  (->> (iterate fuel mass)
       (take-while pos?)
       rest
       (apply +)))

; part 1
(apply + (map fuel input))
; 3399394

; part 2
(apply + (map total-fuel input))
; 15295091
