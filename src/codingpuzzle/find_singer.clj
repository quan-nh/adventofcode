(ns codingpuzzle.find-singer)

(defn find-singer [n s]
  (first
    (reduce (fn [a i]
              (->> (concat a a)
                   (drop (rem s i))
                   (take (dec i))))
            (range n)
            (range n 1 -1))))

(find-singer 10 3)
(find-singer 3 2)
(find-singer 3 3)
(find-singer 3 10)
