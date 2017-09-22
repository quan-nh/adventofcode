(ns adventofcode2016.day4
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [adventofcode2016.util :as util]))

(def input (-> (io/resource "2016/day4")
               slurp
               str/split-lines))

(defn parse-room [room]
  (let [[_ name id checksum] (re-matches #"^(.+)-(\d+)\[([a-z]+)\]$" room)]
    [name (read-string id) checksum]))

(defn real? [[name _ checksum]]
  (let [freq-name (frequencies (str/replace name #"-" ""))
        actual-checksum (->> (util/sorted-map-by-vals freq-name)
                             keys
                             (take 5)
                             (apply str))]
    (= actual-checksum checksum)))

(comment
  ;; part1
  (->> input
       (map parse-room)
       (filter real?)
       (map #(nth % 1))
       (reduce +)))

(defn shift-char [chr num]
  (if (= chr \-)
    \space
    (let [a (int \a)
          z (int \z)
          c (+ (int chr)
               (mod num 26))]
      (if (<= c z)
        (char c)
        (char (+ (- c z 1) a))))))

(defn shift-name [name num]
  (apply str (map #(shift-char % num) name)))

(comment
  ;; part2
  (->> input
       (map parse-room)
       (filter real?)
       (map (fn [[name id _]] [(shift-name name id) id]))
       (filter (fn [[name _]] (= name "northpole object storage")))))
