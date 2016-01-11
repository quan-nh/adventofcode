(ns adventofcode.day10)

(defn look-and-say [x]
  (->> x
       (partition-by identity)
       (mapcat (juxt count first))
       (apply str)))

(-> (iterate look-and-say "3113322113")
    (nth 40)
    count)
