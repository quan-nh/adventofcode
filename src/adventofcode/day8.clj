(ns adventofcode.day8
  (:require [clojure.string :as str]))

(defn decrease [s]
  (- (count s)
     (-> s
         (str/replace #"^\"" "")
         (str/replace #"\"$" "")
         (str/replace #"\\\"" "\"")
         (str/replace #"\\\\" "q")
         (str/replace #"\\x[0-9a-f]{2}" "q")
         count)))

(defn increase [s]
  (- (-> s
         (str/replace #"^\"" "qqq")
         (str/replace #"\"$" "qqq")
         (str/replace #"\\\"" "qqqq")
         (str/replace #"\\\\" "qqqq")
         (str/replace #"\\x[0-9a-f]{2}" "qqqqq")
         count)
     (count s)))

(defn total [s f]
  (->> (str/split s #"\n")
       (map f)
       (reduce +)))