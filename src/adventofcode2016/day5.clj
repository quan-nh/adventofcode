(ns adventofcode2016.day5
  (:require [clojure.string :as str]
            [digest]))

(def input "cxdnnyjw")

(comment
  ;; part1
  (->> (range)
       (map #(digest/md5 (str input %)))
       (filter #(str/starts-with? % "00000"))
       (take 8)
       (map #(nth % 5))
       (apply str)))

(defn result [coll [_ pos pas]]
  (if (= (count (remove nil? coll)) 8)
    (reduced coll)
    (if (nth coll (read-string pos))
      coll
      (assoc coll (read-string pos) pas))))

(comment
  ;; part2
  (->> (range)
       (map #(digest/md5 (str input %)))
       (keep #(re-matches #"^00000([0-7])(.).*$" %))
       (reduce result (vec (repeat 8 nil)))
       (apply str)))