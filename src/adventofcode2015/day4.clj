(ns adventofcode.day4
  (:import (java.security MessageDigest)))

(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        size (* 2 (.getDigestLength algorithm))
        raw (.digest algorithm (.getBytes s))
        sig (.toString (BigInteger. 1 raw) 16)
        padding (apply str (repeat (- size (count sig)) "0"))]
    (str padding sig)))

(defn mining-advent-coins [secret-key exp]
  (loop [x 1]
    (if (.startsWith (md5 (str secret-key x)) exp)
      x
      (recur (inc x)))))
