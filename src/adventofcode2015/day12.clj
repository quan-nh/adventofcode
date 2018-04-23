(ns adventofcode.day12
  (:require [clojure.string :as str]
            [clojure.walk :refer [prewalk]]))

(defn sum-nums [s]
  (->> (re-seq #"[-]{0,1}\d+" s)
       (map read-string)
       (reduce +)))

(defn has-red? [m]
  (some (fn [me] ((set me) "red")) m))

(defn sum-nums-2 [s]
  (as-> s a
        (str/replace a ":" " ")
        (read-string a)
        (prewalk #(cond
                   (and (map? %) (has-red? %)) nil
                   (map? %) (seq %)
                   :else %) a)
        (flatten a)
        (filter integer? a)
        (reduce + a)))
