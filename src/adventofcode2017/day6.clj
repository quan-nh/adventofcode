(ns adventofcode2017.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day6")))

(def data (->> (str/split input #"\t")
               (mapv #(Integer/parseInt %))))

(defn redistribute [blocks]
  (let [val (apply max blocks)
        pos (.indexOf blocks val)]
    (reduce #(update %1 %2 inc)
            (assoc blocks pos 0)
            (->> (cycle (range (count blocks)))
                 (drop (inc pos))
                 (take val)))))

(take 5 (iterate redistribute [0 2 7 0]))
;([0 2 7 0]
; [2 4 1 2]
; [3 1 2 3]
; [0 2 3 4]
; [1 3 4 1])

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
