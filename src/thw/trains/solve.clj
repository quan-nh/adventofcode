(ns thw.trains.solve
  (:require [clojure.string :as str])
  (:import (clojure.lang PersistentQueue)))

(def input "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7")

(def graph (->> (str/split input #",\s*")
                (map #(let [[_ a b distance] (re-matches #"([A-Z])([A-Z])(\d+)" %)]
                        [[a b] (Integer/parseInt distance)]))
                (into {})))

(defn distance
  "Return distance of route.
  If no such route exists, output 'NO SUCH ROUTE'."
  [graph route]
  (try
    (->> (partition 2 1 route)
         (map graph)
         (apply +))
    (catch NullPointerException _
      "NO SUCH ROUTE")))

(distance graph ["A" "B" "C"])
;=> 9
(distance graph ["A" "D"])
;=> 5
(distance graph ["A" "D" "C"])
;=> 13
(distance graph (str/split "A-E-B-C-D" #"-"))
;=> 22
(distance graph ["A" "E" "D"])
;=> "NO SUCH ROUTE"

(defn children
  "Return all towns have direct route from given town."
  [graph town]
  (->> (keys graph)
       (filter #(= town (first %)))
       (map second)))

; PersistentQueue: take from the front and add to the end
;;; (peek PQueue) -> head
;;; (pop PQueue) -> but head
;;; (conj PQueue x) -> append

;; number of trips starting at C and ending at C with a maximum of 3 stops
(loop [ret 0
       queue (conj PersistentQueue/EMPTY ["C"])]            ; queue stores path
  (let [route (peek queue)
        town (last route)
        towns (children graph town)
        routes (map #(conj route %) towns)]
    (if (> (count route) 4)
      ret
      (recur (if (and (> (count route) 1) (= "C" town))
               (inc ret)
               ret)
             (into (pop queue) routes)))))
; 2

;; number of trips starting at A and ending at C with exactly 4 stops
(loop [ret 0
       queue (conj PersistentQueue/EMPTY ["A"])]
  (let [route (peek queue)
        town (last route)
        towns (children graph town)
        routes (map #(conj route %) towns)]
    (if (> (count route) 6)
      ret
      (recur (if (and (= (count route) 5) (= "C" town))
               (inc ret)
               ret)
             (into (pop queue) routes)))))
; 3

(defn shortest-route
  "Length of the shortest route (in terms of distance to travel) from start to
  end."
  [graph start end]
  (loop [ret Integer/MAX_VALUE
         queue (conj PersistentQueue/EMPTY [start])]
    (if (empty? queue)
      ret
      (let [route (peek queue)
            town (last route)
            towns (children graph town)
            routes (map #(conj route %) towns)]
        (cond
          (and (> (count route) 1) (= end town))
          (recur (min ret (distance graph route)) (pop queue))

          (> (distance graph route) ret)
          (recur ret (pop queue))

          :else
          (recur ret (into (pop queue) routes)))))))

(shortest-route graph "A" "C")
; 9
(shortest-route graph "B" "B")
; 9

;; number of different routes from C to C with a distance of less than 30
(loop [ret 0
       queue (conj PersistentQueue/EMPTY ["C"])]
  (if (empty? queue)
    ret
    (let [route (peek queue)
          town (last route)
          towns (children graph town)
          routes (map #(conj route %) towns)]
      (if (< (distance graph route) 30)
        (if (and (> (count route) 1) (= "C" town))
          (recur (inc ret) (into (pop queue) routes))
          (recur ret (into (pop queue) routes)))
        (recur ret (pop queue))))))
; 7
