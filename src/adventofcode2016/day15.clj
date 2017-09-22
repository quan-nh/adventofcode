(ns adventofcode2016.day15)

(def disc1 (cycle (range 7)))
(def disc2 (cycle (range 13)))
(def disc3 (drop 2 (cycle (range 3))))
(def disc4 (drop 2 (cycle (range 5))))
(def disc5 (cycle (range 17)))
(def disc6 (drop 7 (cycle (range 19))))
(def disc7 (cycle (range 11)))

;; part1
(loop [time 0]
  (if (and (zero? (nth disc1 (+ 1 time)))
           (zero? (nth disc2 (+ 2 time)))
           (zero? (nth disc3 (+ 3 time)))
           (zero? (nth disc4 (+ 4 time)))
           (zero? (nth disc5 (+ 5 time)))
           (zero? (nth disc6 (+ 6 time))))
    time
    (recur (inc time))))

;; part2
(loop [time 121834]
  (if (and (zero? (nth disc1 (+ 1 time)))
           (zero? (nth disc2 (+ 2 time)))
           (zero? (nth disc3 (+ 3 time)))
           (zero? (nth disc4 (+ 4 time)))
           (zero? (nth disc5 (+ 5 time)))
           (zero? (nth disc6 (+ 6 time)))
           (zero? (nth disc7 (+ 7 time))))
    time
    (recur (inc time))))
