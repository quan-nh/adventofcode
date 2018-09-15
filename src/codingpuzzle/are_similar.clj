(ns codingpuzzle.are-similar)

; https://app.codesignal.com/arcade/intro/level-4/xYXfzQmnhBvEKJwXP
(defn similar? [a b]
  (if (not= (count a) (count b))
    false
    (let [c (remove nil? (map (fn [x y]
                                (if (= x y) nil [x y]))
                              a b))]
      (if (> (count c) 2)
        false
        (= (set (first c)) (set (second c)))))))
