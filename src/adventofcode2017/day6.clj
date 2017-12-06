(ns adventofcode2017.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day6")))

(def data (->> (str/split input #"\t")
               (mapv #(Integer/parseInt %))))

(defn redistribute [blocks]
  (let [len (count blocks)
        val (apply max blocks)
        pos (.indexOf blocks val)]
    (reduce (fn [[blks n] idx]
              (if (zero? n)
                (reduced blks)
                [(-> blks
                     (update pos dec)
                     (update idx inc))
                 (dec n)]))
            [blocks val]
            (drop (inc pos) (cycle (range len))))))

;; part 1
(reduce (fn [r i]
          (if (contains? r i)
            (reduced (count r))
            (conj r i)))
        #{}
        (iterate redistribute data))
; 3156

;; part 2
(reduce (fn [r i]
          (if (.contains r i)
            (reduced (- (count r) (.indexOf r i)))
            (conj r i)))
        []
        (iterate redistribute data))
; 1610
