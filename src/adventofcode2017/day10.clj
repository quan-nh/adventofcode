(ns adventofcode2017.day10
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day10")))

(def n 256)

(defn hash-fn [lengths]
  (fn [[input pos skip]]
    (loop [l input
           curr-pos pos
           skip-size skip
           idx (rem skip (count lengths))]
      (if-some [slt-lengths (get lengths idx)]
        (let [l2 (take n (drop curr-pos (cycle l)))
              selects (take slt-lengths l2)
              remains (drop slt-lengths l2)
              l3 (concat (reverse selects) remains)
              l4 (take n (drop (- n curr-pos) (cycle l3)))
              next-pos (rem (+ curr-pos slt-lengths skip-size) n)]
          (recur l4 next-pos (inc skip-size) (inc idx)))
        [l curr-pos skip-size]))))

;; part 1
(def lengths-part1 (read-string (str "[" input "]")))
(def hash-part1 (hash-fn lengths-part1))
(let [[[f s] _ _] (hash-part1 [(range n) 0 0])]
  (* f s))
; 6909

;; part 2
(defn knot-hashes [s]
  (let [lengths (conj (mapv int s) 17 31 73 47 23)
        [l _ _] (last (take 65 (iterate (hash-fn lengths) [(range n) 0 0])))]
    (->> (partition 16 l)
         (map #(apply bit-xor %))
         (map #(format "%02x" %))
         (apply str))))

(= "a2582a3a0e66e6e86e3812dcb672a272"
   (knot-hashes ""))

(= "33efeb34ea91902bb2f59c9920caa6cd"
   (knot-hashes "AoC 2017"))

(= "3efbe78a8d82f29979031a4aa0b16a9d"
   (knot-hashes "1,2,3"))

(= "63960835bcdc130f0b66d7ff4f6a5a8e"
   (knot-hashes "1,2,4"))

(knot-hashes input)
; 9d5f4561367d379cfbf04f8c471c0095
