(ns thw.trains.solve-zipper
  (:require [clojure.zip :as zip]
            [thw.trains.solve :refer [graph children distance]])
  (:import (clojure.lang PersistentQueue)))

(defn zp [graph root]
  (zip/zipper (comp seq (partial children graph))
              (partial children graph)
              zip/make-node
              root))

(defn- zip-children [loc]
  (when-let [child (zip/down loc)]
    (take-while (comp not nil?) (iterate zip/right child))))

(defn trips [graph start end max-stops]
  ((fn bfs [queue ret]
     (let [loc (peek queue)
           node (zip/node loc)
           path (zip/path loc)
           route (conj path node)
           children (zip-children loc)]
       (if (<= (count path) max-stops)
         (bfs (into (pop queue) children)
              (if (and (some? path) (= end node))
                (conj ret route)
                ret))
         ret)))
    (conj PersistentQueue/EMPTY (zp graph start))           ; queue stores loc
    []))

;; number of trips starting at C and ending at C with a maximum of 3 stops
(count (trips graph "C" "C" 3))
; 2

;; number of trips starting at A and ending at C with exactly 4 stops
(->> (trips graph "A" "C" 4)
     (filter #(= 5 (count %)))
     count)
; 3

(defn shortest-route
  "Length of the shortest route (in terms of distance to travel) from start to
  end."
  [graph start end]
  ((fn bfs [queue ret]
     (if (seq queue)
       (let [loc (peek queue)
             node (zip/node loc)
             path (zip/path loc)
             route (conj path node)
             children (zip-children loc)]
         (cond
           (and (some? path) (= end node))
           (bfs (pop queue) (min ret (distance graph route)))

           (> (distance graph route) ret)
           (bfs (pop queue) ret)

           :else
           (bfs (into (pop queue) children) ret)))
       ret))
    (conj PersistentQueue/EMPTY (zp graph start))
    Integer/MAX_VALUE))

(shortest-route graph "A" "C")
; 9
(shortest-route graph "B" "B")
; 9

;; number of different routes from C to C with a distance of less than 30
(loop [queue (conj PersistentQueue/EMPTY (zp graph "C"))
       ret 0]
  (if (seq queue)
    (let [loc (peek queue)
          node (zip/node loc)
          path (zip/path loc)
          route (conj path node)
          children (zip-children loc)]
      (if (< (distance graph route) 30)
        (if (and (some? path) (= "C" node))
          (recur (inc ret) (into (pop queue) children))
          (recur ret (into (pop queue) children)))
        (recur ret (pop queue))))
    ret))
; 7
