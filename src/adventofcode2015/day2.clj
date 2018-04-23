(ns adventofcode.day2
  (:require [clojure.string :as str]))

(defn parse-dimension [d]
  (let [[l w h] (str/split d #"x")]
    [(read-string l) (read-string w) (read-string h)]))

;; part 1
(defn square-feet [d]
  (let [[l w h] (parse-dimension d)
        l*w (* l w)
        w*h (* w h)
        h*l (* h l)
        extra (min l*w w*h h*l)]
    (+ (* 2 l*w) (* 2 w*h) (* 2 h*l) extra)))

(defn total-square-feet [s]
  (->> (str/split s #"\n")
       (map square-feet)
       (reduce +)))

;; part 2
(defn feet-of-ribbon [d]
  (let [[x y z] (sort (parse-dimension d))]
    (+ x x y y (* x y z))))

(defn total-feet-of-ribbon [s]
  (->> (str/split s #"\n")
       (map feet-of-ribbon)
       (reduce +)))
