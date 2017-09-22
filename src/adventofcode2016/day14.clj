(ns adventofcode2016.day14
  (:require [digest]
            [clojure.string :as str]
            [adventofcode2016.util :as util]))

(def input "ahsbgdzn")

(def stream (->> (range)
                 (map (fn [idx] (digest/md5 (str input idx))))))

(defn md5 [s] (nth (iterate digest/md5 s) 2017))

(def stream2 (->> (range)
                 (map (fn [idx] (md5 (str input idx))))))

(defn triple [s]
  (re-find #"(.)\1{2}" s))

(loop [i 0
       key 0]
  (if (= 64 key)
    (dec i)
    (if-some [[_ c] (triple (nth stream2 i))]
      (if (->> stream2
               (drop (inc i))
               (take 1000)
               (util/any? #(str/includes? % (apply str (repeat 5 c)))))
        (recur (inc i) (inc key))
        (recur (inc i) key))
      (recur (inc i) key))))
