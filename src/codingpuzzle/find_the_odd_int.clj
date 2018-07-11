(ns find-the-odd-int
  "https://www.codewars.com/kata/find-the-odd-int")

(defn find-odd [xs]
  (reduce bit-xor xs))
