(ns aoc23.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2023/day01")))

; part1
(->> (str/split-lines input)
     (map #(re-seq #"\d" %))
     (map #(str (first %) (last %)))
     (map parse-long)
     (apply +))
;=> 54561

; part2
(->> (str/split-lines input)
     (map #(re-seq #"\d|one|two|three|four|five|six|seven|eight|nine" %))
     (map (fn [s]
            (map #(case % "one" 1 "two" 2 "three" 3 "four" 4 "five" 5 "six" 6 "seven" 7 "eight" 8 "nine" 9 %) s)))
     (map #(str (first %) (last %)))
     (map parse-long)
     (apply +))
;=> 54076
