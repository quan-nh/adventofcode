(ns adventofcode.day9
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def locations (atom #{}))
(def distances (atom {}))

(defn parse-line! [l]
  (let [[_ arrv dest dist] (re-find #"^(\w+) to (\w+) = (\d+)$" l)]
    (swap! locations conj arrv)
    (swap! locations conj dest)
    (swap! distances assoc #{arrv dest} (read-string dist))))

(defn shortest-route [s]
  (doseq [l (str/split s #"\n")]
    (parse-line! l))
  (->> (combo/permutations @locations)
       (map #(reduce + (map (comp @distances set)
                            (partition 2 1 %))))
       (reduce min #_max)))

