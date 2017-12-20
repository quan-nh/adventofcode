(ns adventofcode2017.day9
  (:require [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day9")))

(def data
  (loop [s input
         open-garbage? false
         ignored? false
         result ""
         count 0]
    (if (empty? s)
      [result count]
      (cond
        ignored?
        (recur (rest s) open-garbage? false result count)

        (= \! (first s))
        (recur (rest s) open-garbage? true result count)

        open-garbage?
        (case (first s)
          \> (recur (rest s) false false result count)
          (recur (rest s) true false result (inc count)))

        (= \< (first s))
        (recur (rest s) true false result count)

        :else
        (recur (rest s) false false (str result (first s)) count)))))

;; part 1
(->> (loop [s (first data)
            curr-level 1
            result {}]
       (if (empty? s)
         result
         (case (first s)
           \{ (recur (rest s)
                     (inc curr-level)
                     (update result curr-level (fnil inc 0)))

           \} (recur (rest s)
                     (dec curr-level)
                     result)

           (recur (rest s) curr-level result))))
     (map (fn [[k v]] (* k v)))
     (apply +))
; 14204

;; part 2
(second data)
; 6622
