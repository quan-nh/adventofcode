(ns adventofcode2017.day14
  (:require [clojure.string :as str]
            [adventofcode2017.day10]))

(def input "jxqlasbh")
;(def input "flqrgnkx")

(defn hexdigit->binary [digit]
  (str/replace (format "%4s"
                       (-> digit str (Integer/parseInt 16) Integer/toBinaryString))
               " " "0"))

(defn hex->binary [s]
  (apply str (map hexdigit->binary s)))

(def grid (->> (range 128)
               (map #(str input "-" %))
               (map adventofcode2017.day10/knot-hashes)
               (mapv hex->binary)))

;; part 1
(-> (apply str grid)
    frequencies
    (get \1))
; 8140

;; part 2
(defn travel [path visit]
  (let [[i j] (last path)
        next-visit (->> [[(inc i) j] [i (inc j)] [(dec i) j] [i (dec j)]]
                        (filter #(= \1 (get-in grid %)))
                        (remove visit)
                        first)]
    (cond
      (some? next-visit)
      (travel (conj path next-visit) (conj visit next-visit))

      (not (empty? (drop-last path)))
      (travel (drop-last path) visit)

      :else
      visit)))

(loop [[i j] [0 0]
       v #{[0 0]}
       regions 1]
  (let [visit (travel [[i j]] v)]
    (if-some [next-elm (->> (for [a (range 128)
                                  b (range 128)
                                  :when (or (and (= a i)
                                                 (> b j))
                                            (> a i))]
                              [a b])
                            (remove visit)
                            (filter #(= \1 (get-in grid %)))
                            first)]
      (recur next-elm (conj visit next-elm) (inc regions))
      regions)))
; 1182
