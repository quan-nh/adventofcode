(ns adventofcode2018.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2018/day01")))

(def data (->> (str/split-lines input)
               (map #(Integer/parseInt %))))

; part1
(reduce + data)
; 470

; part2
(reduce
  (fn [[result x] y]
    (let [z (+ x y)]
      (if (contains? result z)
        (reduced z)
        [(conj result z) z])))
  [#{} 0]
  (apply concat (repeat data)))
; 790
