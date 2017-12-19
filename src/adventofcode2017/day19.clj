(ns adventofcode2017.day19
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day19")))
(def data (str/split-lines input))

(defn move [[i j] direction]
  (case direction
    :up [(dec i) j]
    :down [(inc i) j]
    :left [i (dec j)]
    :right [i (inc j)]))

(loop [[i j] [0 (str/index-of (data 0) \|)]
       direction :down
       result []
       steps 0]
  (case (get-in data [i j])
    (\| \-)
    (recur (move [i j] direction) direction result (inc steps))

    (\A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X
      \Y \Z)
    (recur (move [i j] direction) direction (conj result (get-in data [i j])) (inc steps))

    \+
    (case direction
      (:up :down)
      (case (get-in data (move [i j] :left))
        (\space nil) (recur (move [i j] :right) :right result (inc steps))
        (recur (move [i j] :left) :left result (inc steps)))

      (:left :right)
      (case (get-in data (move [i j] :up))
        (\space nil) (recur (move [i j] :down) :down result (inc steps))
        (recur (move [i j] :up) :up result (inc steps))))

    [(apply str result) steps]))
; ["BPDKCZWHGT" 17728]
