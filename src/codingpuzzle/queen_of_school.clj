(ns codingpuzzle.queen-of-school
  (:require [clojure.test :refer :all]))

(defn queen-of-school [votes]
  (let [count-votes (frequencies votes)
        max-votes (apply max (vals count-votes))]
    (->> count-votes
         (filter (fn [[_ v]]
                   (= v max-votes)))
         keys
         sort
         last)))

(deftest test-queen-of-school
  (is (= "Mary"
         (queen-of-school ["Laura", "Emily", "Louise", "Natasha", "Emily", "Lilly", "Louise", "Laura", "Mary", "Mary"]))))

(run-tests)
