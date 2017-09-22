(ns adventofcode2016.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2016/day2")))

;; part 1
(defn point->num [p]
  (case p
    [0 0] 1
    [1 0] 2
    [2 0] 3
    [0 1] 4
    [1 1] 5
    [2 1] 6
    [0 2] 7
    [1 2] 8
    [2 2] 9))

(defn move [[x y] moving]
  (case moving
    \L [(max 0 (dec x)) y]
    \R [(min 2 (inc x)) y]
    \U [x (max 0 (dec y))]
    \D [x (min 2 (inc y))]))

(comment
  ;; part 1
  (->> (for [line (str/split-lines input)]
         (reduce move [1 1] line))
       (map point->num)
       (apply str)))

;; part 2
(defn point->keypad [p]
  (case p
    [0 0] 7
    [1 0] 8
    [2 0] 9
    [-1 0] 6
    [-2 0] 5
    [0 1] 3
    [1 1] 4
    [-1 1] 2
    [0 2] 1
    [0 -1] \B
    [1 -1] \C
    [-1 -1] \A
    [0 -2] \D))

(defn move-or-stand [p [x y]]
  (if (> (+ (Math/abs x) (Math/abs y)) 2)
    p
    [x y]))

(defn move2 [[x y] moving]
  (case moving
    \L (move-or-stand [x y] [(dec x) y])
    \R (move-or-stand [x y] [(inc x) y])
    \U (move-or-stand [x y] [x (inc y)])
    \D (move-or-stand [x y] [x (dec y)])))

(comment
  ;; part2
  (->> (for [line (str/split-lines input)]
         (reduce move2 [0 -2] line))
       (map point->keypad)
       (apply str)))
