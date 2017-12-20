(ns adventofcode2017.day20
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day20")))

(def data (->> (str/split-lines input)
               (map #(read-string (str "{"
                                       (-> %
                                           (str/replace "=<" " [")
                                           (str/replace ">" "]")
                                           (str/replace "p" ":p")
                                           (str/replace "v" ":v")
                                           (str/replace "a" ":a"))
                                       "}")))))

(defn update-particle [{:keys [p v a]}]
  {:p (map + p v a)
   :v (map + v a)
   :a a})

;; part 1
(defn manhattan-distance [{:keys [p]}]
  (->> p
       (map #(Math/abs %))
       (apply +)))

(loop [d data
       i 0]
  (if (= 1000 i)
    (->> d
         (map manhattan-distance)
         (map-indexed vector)
         (apply min-key second)
         first)
    (recur (map update-particle d) (inc i))))
; 150

;; part 2
(defn remove-collide [particles]
  (->> particles
       (group-by :p)
       (remove (fn [[_ v]] (> (count v) 1)))
       (mapcat second)))

(loop [d data
       i 0]
  (if (= 1000 i)
    (count d)
    (recur (map update-particle (remove-collide d))
           (inc i))))
; 657
