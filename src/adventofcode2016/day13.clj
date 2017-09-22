(ns adventofcode2016.day13)

(def input 1364)

(defn decode [x y]
  (let [no-bits (->> (Integer/toString (+ input (* x x) (* 3 x) (* 2 x y) y (* y y)) 2)
                     (filter #(= \1 %))
                     (count))]
    (if (even? no-bits) :space :wall)))

(def layout (for [x (range 50)
                  y (range 50)]
              (decode x y)))

(defn pos [[x y]]
  (nth layout (+ (* x 50) y)))

(defn neighbors [[x y]]
  (->> [[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]]
       (remove (fn [[x y]] (or (< x 0) (< y 0))))
       (filter #(= :space (pos %)))))

(def start [1 1])
(def target [31 39])

(loop [dist {[1 1] 0}
       [u & r] [[1 1]]
       color (assoc (into {} (for [x (range 50)
                                   y (range 50)]
                               [[x y] :white])) [1 1] :gray)]
  (if (= target u)
    (get dist u)
    (recur (reduce (fn[m v] (assoc m v (inc (get dist u)))) dist (neighbors u))
           (concat (vec r) (filter #(= :white (get color %)) (neighbors u)))
           (assoc (loop [coll (filter #(= :white (get color %)) (neighbors u))
                         c color]
                    (if (empty? coll)
                      c
                      (recur (rest coll) (assoc c (first coll) :gray))))
             u :black))))

(loop [dist {[1 1] 0}
       [u & r] [[1 1]]
       color (assoc (into {} (for [x (range 50)
                                   y (range 50)]
                               [[x y] :white])) [1 1] :gray)]
  (if (= 50 (get dist u))
    (count dist)
    (recur (reduce (fn[m v] (assoc m v (inc (get dist u)))) dist (neighbors u))
           (concat (vec r) (filter #(= :white (get color %)) (neighbors u)))
           (assoc (loop [coll (filter #(= :white (get color %)) (neighbors u))
                         c color]
                    (if (empty? coll)
                      c
                      (recur (rest coll) (assoc c (first coll) :gray))))
             u :black))))