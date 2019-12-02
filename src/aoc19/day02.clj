(ns aoc19.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (mapv #(Integer/valueOf %)
                 (-> (io/resource "2019/day02")
                     slurp
                     str/trim
                     (str/split #","))))

(defn output [noun verb]
  (first
   (loop [input (-> input
                    (assoc 1 noun)
                    (assoc 2 verb))
          pos 0]
     (let [p1 (input (+ pos 1))
           p2 (input (+ pos 2))
           p3 (input (+ pos 3))]
       (case (input pos)
         1  (recur (assoc input p3 (+ (input p1) (input p2))) (+ pos 4))
         2  (recur (assoc input p3 (* (input p1) (input p2))) (+ pos 4))
         99 input)))))

(output 12 2)
; 11590668

(for [noun (range 100)
      verb (range 100)
      :when (= (output noun verb) 19690720)]
  (+ (* 100 noun) verb))
; (2254)
