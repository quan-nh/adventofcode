(ns adventofcode2017.day8
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day8")))

(def data (str/split-lines input))

(defmulti command (fn [cmd _] cmd))
(defmethod command "inc" [_ val] #(+ % (read-string val)))
(defmethod command "dec" [_ val] #(- % (read-string val)))

(defmulti condition (fn [con _] con))
(defmethod condition ">" [_ val] #(> % (read-string val)))
(defmethod condition "<" [_ val] #(< % (read-string val)))
(defmethod condition ">=" [_ val] #(>= % (read-string val)))
(defmethod condition "<=" [_ val] #(<= % (read-string val)))
(defmethod condition "==" [_ val] #(= % (read-string val)))
(defmethod condition "!=" [_ val] #(not= % (read-string val)))

(defn instruct [register instruction]
  (let [[a cmd x _ b con y] (str/split instruction #"\s")]
    (if ((condition con y) (get register b 0))
      (update register a (fnil (command cmd x) 0))
      register)))

;; part 1
(->> (vals (reduce instruct {} data))
     (apply max))
; 5215

;; part 2
(->> (reductions instruct {} data)
     rest
     (map #(apply max (vals %)))
     (apply max))
; 6419
