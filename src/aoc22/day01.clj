(ns aoc22.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2022/day01")))

(->> (str/split input #"\n\n")
     (map str/split-lines)
     (map #(map parse-long %))
     (map #(apply + %))
     (sort >)
     (take 3)
     ;(apply +)
     )
;=> (69795 69434 69208)
