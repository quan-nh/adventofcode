(ns adventofcode2017.day17)

(def steps 303)

;; part 1
(defn spinlock [[coll curr-pos] val]
  (let [insert-pos (inc (rem (+ curr-pos steps) (count coll)))]
    [(concat (take insert-pos coll) [val] (drop insert-pos coll))
     insert-pos]))

(->> (reduce spinlock [[0] 0] (range 1 2018))
     first
     (drop-while #(not= 2017 %))
     second)
; 1971

;; part 2
(defn spinlock2 [[after-zero-val curr-pos] val]
  (let [insert-pos (inc (rem (+ curr-pos steps) val))]
    [(if (= 1 insert-pos) val after-zero-val)
     insert-pos]))

(first (reduce spinlock2 [nil 0] (range 1 50000001)))
; 17202899
