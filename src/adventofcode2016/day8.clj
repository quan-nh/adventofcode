(ns adventofcode2016.day8
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(defn init-screen [wide tall]
  (repeat tall (repeat wide 0)))

(defn partial-apply [coll n f]
  (let [[a b] (split-at n coll)]
    (concat (map f a) b)))

(defn rect [screen a b]
  (partial-apply screen b
                 (fn [coll]
                   (partial-apply coll a (constantly 1)))))

(defn circle-drop [coll n]
  (->> coll
       cycle
       (drop (- (count coll) n))
       (take (count coll))))

(defn rotate-row [screen a b]
  (update (vec screen) a #(circle-drop % b)))

(defn transpose [coll]
  (apply map vector coll))

(defn rotate-column [screen a b]
  (-> screen
      transpose
      (rotate-row a b)
      transpose))

(defn parse-input [input]
  (if-let [[_ a b] (re-matches #"^rect\s(\d+)x(\d+)$" input)]
    [:rect (read-string a) (read-string b)]
    (if-let [[_ a b] (re-matches #"^rotate\srow\sy=(\d+)\sby\s(\d+)$" input)]
      [:rotate-row (read-string a) (read-string b)]
      (if-let [[_ a b] (re-matches #"^rotate\scolumn\sx=(\d+)\sby\s(\d+)$" input)]
        [:rotate-column (read-string a) (read-string b)]))))

(defn apply-instruction [screen input]
  (match (parse-input input)
         [:rect a b] (rect screen a b)
         [:rotate-row a b] (rotate-row screen a b)
         [:rotate-column a b] (rotate-column screen a b)))

(defn count-lit [coll]
  (count (filter #(= 1 %) coll)))

(->> (io/resource "2016/day8")
     slurp
     str/split-lines
     (reduce apply-instruction (init-screen 50 6))
     (map count-lit)
     (reduce +))