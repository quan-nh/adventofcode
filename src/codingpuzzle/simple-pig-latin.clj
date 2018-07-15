(ns piglatin
  "http://www.codewars.com/kata/simple-pig-latin/")

(defn pig-it [s]
  (clojure.string/replace s #"(?i)([a-z])([a-z]*)" "$2$1ay"))
