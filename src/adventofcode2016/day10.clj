(ns adventofcode2016.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (-> (io/resource "2016/day10")
               slurp
               str/split-lines))

(def init-bots (->> input
                    (keep #(re-matches #"^value\s(\d+)\s.+\s(\d+)$" %))
                    (map (fn [[_ v b]] [(keyword (str "bot" b)) (read-string v)]))
                    (reduce (fn [m [b v]]
                              (update m b #(conj % v))) {})))

(def rules (->> input
                (keep #(re-matches #"^bot\s(\d+)\sgives\slow\sto\s(.+)\s(\d+)\sand\shigh\sto\s(.+)\s(\d+)$" %))
                (reduce (fn [m [_ bot low low-no high high-no]]
                          (assoc m (keyword (str "bot" bot))
                                   {:low  (keyword (str low low-no))
                                    :high (keyword (str high high-no))})) {})))

(defn apply-rule [bots]
  (let [[bot [a b]] (first (filter (fn [[k v]] (= 2 (count v))) bots))]
    (-> bots
        (update (get-in rules [bot :low]) #(conj % (min a b)))
        (update (get-in rules [bot :high]) #(conj % (max a b)))
        (dissoc bot))))

(defn finish? [bots]
  (->> (keys bots)
       (map name)
       (filter #(re-matches #"bot\d+" %))
       empty?))

(defn have-values? [bots]
  (or (.contains (vals bots) '(61 17))
      (.contains (vals bots) '(17 61))))

(comment
  ;; part1
  (->> (iterate apply-rule init-bots)
       (take-while (complement finish?))
       (filter have-values?)))

(comment
  ;; part2
  (let [output (->> (iterate apply-rule init-bots)
                    (filter finish?)
                    first)]
    (* (first (:output0 output))
       (first (:output1 output))
       (first (:output2 output)))))
