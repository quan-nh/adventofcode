(ns adventofcode.day5
  (:require [clojure.string :as str]))

(defn num-vowels [s]
  (->> s
       (map #(contains? #{\a \e \i \o \u} %))
       (filter true?)
       count))

(defn nice-part1? [s]
  (and (>= (num-vowels s) 3)
       (boolean (re-find #"(\w)\1+" s))
       (not-any? true? [(.contains s "ab")
                        (.contains s "cd")
                        (.contains s "pq")
                        (.contains s "xy")])))

(defn nice-part2? [s]
  (and (boolean (re-find #"(\w\w).*\1+" s))
       (boolean (re-find #"(\w).\1+" s))))

(defn count-nice [s nice?]
  (->> (str/split s #"\n")
       (map nice?)
       (filter true?)
       count))
