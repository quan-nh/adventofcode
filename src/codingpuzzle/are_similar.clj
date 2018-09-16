(ns codingpuzzle.are-similar)

; https://app.codesignal.com/arcade/intro/level-4/xYXfzQmnhBvEKJwXP
(defn areSimilar [a b]
  (let [[x y :as c] (remove nil? (map #(when (not= %1 %2) [%1 %2]) a b))]
    (if (> (count c) 2)
      false
      (= (set x) (set y)))))
