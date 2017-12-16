(ns adventofcode2017.day15)

(defn gen-fn [factor]
  (fn [n]
    (rem (* n factor) 2147483647)))

(def gen-a (gen-fn 16807))
(def gen-b (gen-fn 48271))

(defn match? [a b]
  (= (bit-and 2r1111111111111111 a)
     (bit-and 2r1111111111111111 b)))

;; part 1
(->> (map match?
          (iterate gen-a 277)
          (iterate gen-b 349))
     (take 40000000)
     (filter true?)
     count)
; 592

;; part 2
(->> (map match?
          (->> (iterate gen-a 277)
               (filter #(zero? (rem % 4))))
          (->> (iterate gen-b 349)
               (filter #(zero? (rem % 8)))))
     (take 5000000)
     (filter true?)
     count)
; 320
