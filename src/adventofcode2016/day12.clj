(ns adventofcode2016.day12
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.core.match :refer [match]]))

(def input (-> (io/resource "2016/day12")
               slurp
               str/split-lines))

(loop [i 0
       result {"a" 0 "b" 0 "c" 0 "d" 0}]
  (if (>= i (count input))
    result
    (match (str/split (nth input i) #" ")
           ["cpy" (x :guard #(re-matches #"\d+" %)) y] (recur (inc i) (assoc result y (Integer. x)))
           ["cpy" x y] (recur (inc i) (assoc result y (get result x)))
           ["inc" x] (recur (inc i) (update result x inc))
           ["dec" x] (recur (inc i) (update result x dec))
           ["jnz" (x :guard #(re-matches #"\d+" %)) y] (if (zero? (read-string x))
                                                         (recur (inc i) result)
                                                         (recur (+ i (read-string y)) result))
           ["jnz" x y] (if (zero? (get result x))
                         (recur (inc i) result)
                         (recur (+ i (read-string y)) result)))))
