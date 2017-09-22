(ns adventofcode.day14
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (line-seq (io/reader (io/resource "2015/day14"))))

(defn distance [reindeer seconds]
  (let [{:keys [speed time rest]} reindeer
        a (quot seconds (+ time rest))
        b (mod seconds (+ time rest))
        c (min b time)]
    (* (+ (* a time) c) speed)))

(def reindeers
  (->> input
       (map #(str/replace % #"(can fly |km/s for |seconds, but then must rest for | seconds.$)" ""))
       (map #(read-string (str "(" % ")")))
       (map (fn [[_ speed time rest]] {:speed speed :time time :rest rest}))))

(reduce max (map #(distance % 2503) reindeers))

(->> (for [x (range 1 2504)]
       (map #(distance % x) reindeers))
     (map (fn [coll]
            (let [max (apply max coll)]
              (map #(if (= max %) 1 0) coll))))
     (apply map +)
     (reduce max))
