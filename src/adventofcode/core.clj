(ns adventofcode.core)

(defn day1-floor [s]
  (let [up (->> s
                (re-seq #"\(")
                count)
        down (->> s
                  (re-seq #"\)")
                  count)]
    (- up down)))

(defn day1-position [s]
  (loop [str s
         up 0
         down 0
         pos 0]
    (let [[c & rest] str]
      (if (= -1 (- up down))
        pos
        (if (= \( c)
          (recur rest (inc up) down (inc pos))
          (recur rest up (inc down) (inc pos)))))))
