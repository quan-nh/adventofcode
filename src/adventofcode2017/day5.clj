(ns adventofcode2017.day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day5")))

(def data (->> (str/split-lines input)
               (mapv #(Integer/valueOf %))))

;; part 1
(defn jump [[offsets pos]]
  (when-some [offset (get offsets pos)]
    [(update offsets pos inc) (+ pos offset)]))

(->> (iterate jump [data 0])
     (take-while some?)
     count
     dec)
; 358309

;; part 2
(defn jump2 [[offsets pos]]
  (when-some [offset (get offsets pos)]
    [(update offsets pos #(if (>= % 3) (dec %) (inc %))) (+ pos offset)]))

(->> (iterate jump2 [data 0])
     (take-while some?)
     count
     dec)
; 28178177
