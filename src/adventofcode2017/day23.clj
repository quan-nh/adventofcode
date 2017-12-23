(ns adventofcode2017.day23
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day23")))

(def instructions (->> (str/split-lines input)
                       (map #(str/split % #"\s"))))

(defn get-val [m k]
  (if (number? (read-string k))
    (read-string k)
    (get m k 0)))

;; part 1
(defmulti command first)

(defmethod command "set" [[_ x y]]
  (fn [state i]
    [(assoc state x (get-val state y))
     (inc i)]))

(defmethod command "sub" [[_ x y]]
  (fn [state i]
    [(update state x (fnil - 0) (get-val state y))
     (inc i)]))

(defmethod command "mul" [[_ x y]]
  (fn [state i]
    [(-> state
         (update x (fnil * 0) (get-val state y))
         (update :mul inc))
     (inc i)]))

(defmethod command "jnz" [[_ x y]]
  (fn [state i]
    (if-not (zero? (get-val state x))
      [state (+ i (get-val state y))]
      [state (inc i)])))

;; part 1
(loop [[state i] [{:mul 0} 0]]
  (if (< i (count instructions))
    (recur ((command (nth instructions i)) state i))
    (:mul state)))
; 8281

;; part 2
(loop [[state i] [{:mul 0 "a" 1} 0]
       n 0]
  (prn "curr-state:" state)
  (prn i (nth instructions i))
  (if (< n 100)
    (recur ((command (nth instructions i)) state i) (inc n))))

(defn prime? [n]
  (not-any? #(zero? (rem n %)) (range 2 (inc (int (Math/sqrt n))))))

(->> (range 109300 (inc 126300) 17)
     (filter (complement prime?))
     count)
; 911
