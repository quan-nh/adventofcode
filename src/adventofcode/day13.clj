(ns adventofcode.day13
  (:require
    [clojure.java.io :as io]
    [clojure.set :refer [union]]
    [clojure.math.combinatorics :as combo]
    [clojure.string :as str]))

(def input
  (line-seq (io/reader (io/resource "day13"))))

(def happiness-map
  (->> input
       (map #(str/replace % #"would lose " "-"))
       (map #(str/replace % #"(would gain |happiness units by sitting next to |.$)" ""))
       (map #(read-string (str "(" % ")")))
       (map (fn [[a b c]] [[a c] b]))
       (into {})))

(def people (reduce union (map set (keys happiness-map))))

(->> (combo/permutations people)
     (map #(conj % (last %)))
     (map #(map (fn [[a b]] [(happiness-map [a b]) (happiness-map [b a])])
                (partition 2 1 %)))
     (map flatten)
     (map #(reduce + %))
     (reduce max))

(->> (combo/permutations (conj people "myshelf"))
     (map #(conj % (last %)))
     (map #(map (fn [[a b]]
                  (if (or (= a "myshelf") (= b "myshelf"))
                    [0 0]
                    [(happiness-map [a b]) (happiness-map [b a])]))
                (partition 2 1 %)))
     (map flatten)
     (map #(reduce + %))
     (reduce max))
