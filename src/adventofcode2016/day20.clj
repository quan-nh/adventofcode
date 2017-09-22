(ns adventofcode2016.day20
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (-> (io/resource "2016/day20")
               slurp
               str/split-lines))

(defn remove-block-ips [coll blacklist]
  (let [[a b] (str/split blacklist #"-")]
    (concat (subseq coll < (read-string a))
            (subseq coll > (read-string b)))))

#_(apply min (reduce remove-block-ips (range 0 4294967296) input))
