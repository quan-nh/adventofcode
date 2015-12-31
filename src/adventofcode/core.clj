(ns adventofcode.core)

(defn day1-floor [s]
  (let [up (->> s
                (re-seq #"\(")
                count)
        down (->> s
                  (re-seq #"\)")
                  count)]
    (- up down)))
