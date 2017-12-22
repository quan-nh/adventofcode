(ns adventofcode2017.day21
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day21")))

(def init [".#."
           "..#"
           "###"])

(def transpose #(apply map vector %))
(def flip #(map reverse %))
(def rotate-right (comp flip transpose))

(defn rotate-flips [matrix]
  (let [rotations (take 4 (iterate rotate-right matrix))]
    (concat rotations (map flip rotations))))

(def rules (->> (str/split-lines input)
                (map #(let [[k v] (str/split % #" => ")]
                        [(map seq (str/split k #"/")) (str/split v #"/")]))
                (into {})))

(defn transform [matrix]
  (first (keep rules (rotate-flips matrix))))

(defn split-matrix [matrix]
  (let [n (if (even? (count (first matrix))) 2 3)]
    (->> (partition n matrix)
         (map #(transpose (map (fn [mat] (partition n mat)) %))))))

(defn join-matrix [matrix]
  (->> (mapcat #(apply map vector %) matrix)
       (map #(apply concat %))))

(defn enhance [matrix]
  (->> (split-matrix matrix)
       (map #(map transform %))
       join-matrix))

(->> (iterate enhance init)
     (take 6)
     last
     (map #(filter #{\#} %))
     (map count)
     (apply +))
; 184

(->> (iterate enhance init)
     (take 19)
     last
     (map #(filter #{\#} %))
     (map count)
     (apply +))
; 2810258
