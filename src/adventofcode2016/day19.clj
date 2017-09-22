(ns adventofcode2016.day19)

(loop [coll (range 1 3014388)]
  (let [l (count coll)]
    (cond
      (= 1 l) coll
      (even? l) (recur (take-nth 2 coll))
      (odd? l) (recur (rest (take-nth 2 coll))))))


