(ns adventofcode2017.day3)

(def input 325489)

; part 1
(def north [0 1])
(def east [1 0])
(def south [0 -1])
(def west [-1 0])

(def direction-pattern
  (cycle [east north west south]))

(def distance-pattern
  (mapcat
    (fn [x] [x x])
    (iterate inc 1)))

(def pattern
  (mapcat
    (fn [distance direction]
      (repeat distance direction))
    distance-pattern
    direction-pattern))

(defn next-pos [current-pos dir]
  (mapv + current-pos dir))

(def grid
  (reductions
    next-pos
    [0 0]
    pattern))

(let [[x y] (nth grid (dec input))]
  (+ (Math/abs x) (Math/abs y)))
; 552

;; part 2
(defn neighbors [[x y]]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= 0 dx dy)]
    [(+ x dx) (+ y dy)]))

(defn pos-val [m pos]
  (apply +
         (for [p (neighbors pos)]
           (get m p 0))))

(reduce (fn [m [x y]]
          (let [v (pos-val m [x y])]
            (if (> v input)
              (reduced v)
              (assoc m [x y] v))))
        {[0 0] 1}
        (rest grid))
; 330785
