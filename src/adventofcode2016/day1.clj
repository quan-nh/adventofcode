(ns adventofcode2016.day1
  (:require [clojure.string :as str]
            [clojure.core.match :refer [match]]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2016/day1")))

(defn move [[x y p] [nav & step]]
  (let [n-step (read-string (apply str step))]
    (match [p nav]
           [\N \L] [(- x n-step) y \W]
           [\N \R] [(+ x n-step) y \E]
           [\S \L] [(+ x n-step) y \E]
           [\S \R] [(- x n-step) y \W]
           [\E \L] [x (+ y n-step) \N]
           [\E \R] [x (- y n-step) \S]
           [\W \L] [x (- y n-step) \S]
           [\W \R] [x (+ y n-step) \N])))

(comment
  ;; part 1
  (reduce move [0 0 \N] (str/split input #", ")))

;; part 2
(defn reduce-line [[[x1 y1 p1] [x2 y2 p2]]]
  (if (or (= \N p1) (= \S p1))
    [:y [x1 x2] y1]
    [:x x1 [y1 y2]]))

(defn in-range? [[a b] c]
  (or (<= a c b)
      (>= a c b)))

(defn cross [line1 line2]
  (match [(reduce-line line1) (reduce-line line2)]
         [[:x _ _] [:x _ _]] nil
         [[:y _ _] [:y _ _]] nil
         [[:x x [y1 y2]] [:y [x1 x2] y]] (if (and (in-range? [y1 y2] y) (in-range? [x1 x2] x))
                                           [x y]
                                           nil)
         [[:y [x1 x2] y] [:x x [y1 y2]]] (if (and (in-range? [y1 y2] y) (in-range? [x1 x2] x))
                                           [x y]
                                           nil)))

(defn first-cross [line lines]
  (first (remove nil? (map #(cross line %) lines))))

(comment
  ;; part 2
  (loop [input (str/split input #", ")
         pos [0 0 \N]
         lines []
         result nil]
    (if (nil? result)
      (let [new-pos (move pos (first input))]
        (recur (rest input)
               new-pos
               (conj lines [pos new-pos])
               (first-cross [pos new-pos] (drop-last lines))))
      result)))