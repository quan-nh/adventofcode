(ns adventofcode.day6
  (:require [clojure.string :as str]))

(defn init-grid [n]
  (into {} (for [x (range n)
                 y (range n)]
             {[x y] 0})))

;; part 1
(defn turn-on [grid [x1 y1] [x2 y2]]
  (into grid (for [x (range x1 (inc x2))
                   y (range y1 (inc y2))]
               {[x y] 1})))

(defn turn-off [grid [x1 y1] [x2 y2]]
  (into grid (for [x (range x1 (inc x2))
                   y (range y1 (inc y2))]
               {[x y] 0})))

(defn toggle [grid [x1 y1] [x2 y2]]
  (into grid (for [x (range x1 (inc x2))
                   y (range y1 (inc y2))]
               {[x y] (- 1 (get grid [x y]))})))

(defn exec-instruction [grid instruct]
  (let [[_ command x1 y1 x2 y2] (re-find #"^(.+)\s(\d+),(\d+)\sthrough\s(\d+),(\d+)$" instruct)]
    (case command
      "turn on" (turn-on grid [(read-string x1) (read-string y1)] [(read-string x2) (read-string y2)])
      "turn off" (turn-off grid [(read-string x1) (read-string y1)] [(read-string x2) (read-string y2)])
      "toggle" (toggle grid [(read-string x1) (read-string y1)] [(read-string x2) (read-string y2)])
      )))

(defn count-lit [s]
  (->> (loop [grid (init-grid 1000)
              instructs (str/split s #"\n")]
         (if (nil? instructs)
           grid
           (let [[first & rest] instructs]
             (recur (exec-instruction grid first) rest))))
       vals
       (filter #(= 1 %))
       count))

;; part 2
(defn turn-on-2 [grid [x1 y1] [x2 y2]]
  (into grid (for [x (range x1 (inc x2))
                   y (range y1 (inc y2))]
               {[x y] (inc (get grid [x y]))})))

(defn turn-off-2 [grid [x1 y1] [x2 y2]]
  (into grid (for [x (range x1 (inc x2))
                   y (range y1 (inc y2))]
               {[x y] (if (> (get grid [x y]) 0)
                        (dec (get grid [x y]))
                        0)})))

(defn toggle-2 [grid [x1 y1] [x2 y2]]
  (into grid (for [x (range x1 (inc x2))
                   y (range y1 (inc y2))]
               {[x y] (+ 2 (get grid [x y]))})))

(defn exec-instruction-2 [grid instruct]
  (let [[_ command x1 y1 x2 y2] (re-find #"^(.+)\s(\d+),(\d+)\sthrough\s(\d+),(\d+)$" instruct)]
    (case command
      "turn on" (turn-on-2 grid [(read-string x1) (read-string y1)] [(read-string x2) (read-string y2)])
      "turn off" (turn-off-2 grid [(read-string x1) (read-string y1)] [(read-string x2) (read-string y2)])
      "toggle" (toggle-2 grid [(read-string x1) (read-string y1)] [(read-string x2) (read-string y2)])
      )))

(defn total-brightness [s]
  (->> (loop [grid (init-grid 1000)
              instructs (str/split s #"\n")]
         (if (nil? instructs)
           grid
           (let [[first & rest] instructs]
             (recur (exec-instruction-2 grid first) rest))))
       vals
       (reduce +)))