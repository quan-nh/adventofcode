(ns adventofcode2016.day9
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "2016/day9")))

(def regex #"^([A-Z]*)\((\d+)x(\d+)\)(.*)$")

(defn decompress-length [s]
  (if-let [[_ left chars times remain] (re-matches regex s)]
    (+ (count left)
       (* (Integer. chars) (Integer. times))
       (decompress-length (apply str (drop (Integer. chars) remain))))
    (count s)))

(decompress-length input)

(defn decompress-length-2 [s]
  (if-let [[_ left chars times remain] (re-matches regex s)]
    (let [[a b] (->> (split-at (Integer. chars) remain)
                     (map #(apply str %)))]
      (+ (count left)
         (* (Integer. times) (decompress-length-2 a))
         (decompress-length-2 b)))
    (count s)))

(decompress-length-2 input)
