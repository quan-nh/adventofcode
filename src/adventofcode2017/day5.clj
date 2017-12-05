(ns adventofcode2017.day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [criterium.core :refer [quick-bench]]
            [primitive-math]))

(def input (slurp (io/resource "2017/day5")))

(def data (->> (str/split-lines input)
               (mapv #(Integer/parseInt %))))

;; part 1
(defn jump [[maze pos]]
  (when-some [offset (get maze pos)]
    [(update maze pos inc) (+ pos offset)]))

(->> (iterate jump [data 0])
     (take-while some?)
     count
     dec)
; 358309

;; part 2
(defn jump2 [[maze pos]]
  (when-some [offset (get maze pos)]
    [(update maze pos #(if (>= % 3) (dec %) (inc %))) (+ pos offset)]))

(->> (iterate jump2 [data 0])
     (take-while some?)
     count
     dec)
; 28178177

;; perf
(set! *warn-on-reflection* true)
(primitive-math/use-primitive-operators)

(quick-bench
  (let [maze (->> (io/resource "2017/day5")
                  slurp
                  str/split-lines
                  (map #(Integer/parseInt %))
                  int-array)
        len (alength maze)]
    (loop [maze maze
           pos 0
           steps 0]
      (if (< -1 pos len)
        (let [offset (aget maze pos)]
          (recur (doto maze
                   (aset pos (if (>= offset 3) (dec offset) (inc offset))))
                 (+ pos offset)
                 (inc steps)))
        steps)))
  )
; Execution time mean : 106.287963 ms
