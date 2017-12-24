(ns adventofcode2017.day13
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day13")))
;(def input "0: 3\n1: 2\n4: 4\n6: 4")
(def data (->> (str/split-lines input)
               (map #(let [[depth range] (str/split % #": ")]
                       [(read-string depth) (read-string range)]))
               (into {})))

(def last-layer (apply max (keys data)))

(defn scanner-pos [depth step]
  (if-let [range (some-> (data depth) dec)]
    (if (even? (long (/ step range)))
      (rem step range)
      (- range (rem step range)))
    -1))

;; part 1
(def caught-layers
  (reduce (fn [caught layer]
            (if (zero? (scanner-pos layer layer))
              (conj caught layer)
              caught))
          []
          (range (inc last-layer))))

(->> caught-layers
     (map #(* % (data %)))
     (apply +))
; 1844

;; part 2
(loop [delay 1
       layer 0]
  (cond
    (> layer last-layer)
    delay

    (zero? (scanner-pos layer (+ delay layer)))
    (recur (inc delay) 0)

    :else
    (recur delay (inc layer))))
; 3897604
