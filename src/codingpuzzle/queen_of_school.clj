(ns codingpuzzle.queen-of-school)

(defn queen-of-school [votes]
  (let [count-votes (frequencies votes)
        max-votes (apply max (vals count-votes))]
    (->> count-votes
         (filter (fn [[_ v]]
                   (= v max-votes)))
         keys
         sort
         last)))

(queen-of-school ["Laura", "Emily", "Louise", "Natasha", "Emily", "Lilly", "Louise", "Laura", "Mary", "Mary"])
