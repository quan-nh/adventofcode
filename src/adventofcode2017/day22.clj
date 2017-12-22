(ns adventofcode2017.day22
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (str/split-lines (slurp (io/resource "2017/day22"))))
(def len (count input))
(def data (into {} (for [x (range len)
                         y (range len)]
                     [[x (- y)] (case (get-in input [y x])
                                  \# :infected
                                  :clean)])))

(def center [(quot len 2) (- (quot len 2))])

(defn move [[x y] direction turn]
  (case [direction turn]
    ([:north :left] [:south :right] [:west nil] [:east :back])
    [[(dec x) y] :west]

    ([:north :right] [:south :left] [:east nil] [:west :back])
    [[(inc x) y] :east]

    ([:east :left] [:west :right] [:north nil] [:south :back])
    [[x (inc y)] :north]

    ([:east :right] [:west :left] [:south nil] [:north :back])
    [[x (dec y)] :south]))

;; part 1
(loop [[curr-node direction] [center :north]
       nodes data
       bursts 0
       infections 0]
  (if (= 10000 bursts)
    infections
    (case (get nodes curr-node)
      :infected
      (recur (move curr-node direction :right)
             (assoc nodes curr-node :clean)
             (inc bursts) infections)

      (recur (move curr-node direction :left)
             (assoc nodes curr-node :infected)
             (inc bursts) (inc infections)))))
; 5406

;; part 2
(loop [[curr-node direction] [center :north]
       nodes data
       bursts 0
       infections 0]
  (if (= 10000000 bursts)
    infections
    (case (get nodes curr-node)
      :weakened
      (recur (move curr-node direction nil)
             (assoc nodes curr-node :infected)
             (inc bursts) (inc infections))

      :infected
      (recur (move curr-node direction :right)
             (assoc nodes curr-node :flagged)
             (inc bursts) infections)

      :flagged
      (recur (move curr-node direction :back)
             (assoc nodes curr-node :clean)
             (inc bursts) infections)

      (recur (move curr-node direction :left)
             (assoc nodes curr-node :weakened)
             (inc bursts) infections))))
; 2511640
