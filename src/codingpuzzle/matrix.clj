(ns codingpuzzle.matrix
  (:import (clojure.lang PersistentQueue)))

;You are given an M by N matrix consisting of booleans that represents a board. Each True boolean represents a wall. Each False boolean represents a tile you can walk on.
;
;Given this matrix, a start coordinate, and an end coordinate, return the minimum number of steps required to reach the end coordinate from the start. If there is no possible path, then return null. You can move up, left, down, and right. You cannot move through walls. You cannot wrap around the edges of the board.
;
;For example, given the following board:
;
;[[f, f, f, f],
;[t, t, f, t],
;[f, f, f, f],
;[f, f, f, f]]
;and start = (3, 0) (bottom left) and end = (0, 0) (top left), the minimum number of steps required to reach the end is 7, since we would need to go through (1, 2) because there is a wall everywhere else on the second row.

(def t false)
(def f true)

(def board [[f, f, f, f],
            [t, t, f, t],
            [f, f, f, f],
            [f, f, f, f]])

(def start [3, 0])
(def end [0, 0])

(loop [paths (conj PersistentQueue/EMPTY [start])]
  (when-not (empty? paths)
    (let [path (peek paths)
          [x y] (last path)
          next-pos (->> [[(dec x) y] [(inc x) y] [x (dec y)] [x (inc y)]]
                        (remove (set path))
                        (filter #(get-in board %)))]
      (cond
        (some #{end} next-pos) (count path)
        (seq next-pos) (recur (apply conj (pop paths) (map #(conj path %) next-pos)))
        :else (recur (pop paths))))))
; 7
