(ns adventofcode2017.day3)

(def input 325489)

; part 1
(defn next-pos [[x y]]
  (let [abs-x (Math/abs x)
        abs-y (Math/abs y)]
    (cond
      (and (= x y)
           (neg? x))
      [(inc x) y]

      (and (= x y)
           (pos? x))
      [(dec x) y]

      (and (zero? (+ x y))
           (neg? x))
      [x (dec y)]

      (and (zero? (+ x y))
           (>= x 0))
      [(inc x) y]

      (and (= abs-x (max abs-x abs-y))
           (pos? x))
      [x (inc y)]

      (and (= abs-y (max abs-x abs-y))
           (pos? y))
      [(dec x) y]

      (and (= abs-x (max abs-x abs-y))
           (neg? x))
      [x (dec y)]

      (and (= abs-y (max abs-x abs-y))
           (neg? y))
      [(inc x) y])))

(def grid (iterate next-pos [0 0]))

(let [[x y] (nth grid (dec input))]
  (+ (Math/abs x) (Math/abs y)))
; 552

;; part 2
(defn total-around [m [x y]]
  (+ (get m [x (inc y)] 0)
     (get m [x (dec y)] 0)
     (get m [(inc x) y] 0)
     (get m [(inc x) (inc y)] 0)
     (get m [(inc x) (dec y)] 0)
     (get m [(dec x) y] 0)
     (get m [(dec x) (inc y)] 0)
     (get m [(dec x) (dec y)] 0)))

(reduce (fn [m [x y]]
          (let [v (total-around m [x y])]
            (if (> v input)
              (reduced v)
              (assoc m [x y] v))))
        {[0 0] 1}
        (rest grid))
; 330785
