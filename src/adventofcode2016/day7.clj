(ns adventofcode2016.day7
  (:require [clojure.string :as str]
            [adventofcode2016.util :as util]
            [clojure.java.io :as io]
            [clojure.set :refer [intersection]]))

(def input (-> (io/resource "2016/day7")
               slurp
               str/split-lines))

(def any? (complement not-any?))

(def hypernet-regex #"\[.*?\]")

(defn hypernets [s] (->> (re-seq hypernet-regex s)
                         (map #(str/replace % #"(\[|\])" ""))))

(defn supernets [s] (str/split s hypernet-regex))

(defn abba? [s]
  (->> (partition 4 1 s)
       (any? (fn [[a b c d]] (and (= a d)
                                  (= b c)
                                  (not= a b))))))

(defn tls? [s]
  (and (any? abba? (supernets s))
       (not-any? abba? (hypernets s))))

(comment
  ;; part1
  (count (filter tls? input)))

(defn aba [s]
  (->> (partition 3 1 s)
       (filter (fn [[a b c]] (and (= a c)
                                  (not= a b))))
       (map #(apply str %))))

(defn invert-aba [[a b _]]
  (str b a b))

(defn ssl? [s]
  (let [abas (->> (hypernets s)
                  (mapcat aba)
                  set)
        invert-abas (->> (supernets s)
                         (mapcat aba)
                         (map invert-aba)
                         set)]
    (seq (intersection abas invert-abas))))

(comment
  ;; part2
  (count (filter ssl? input)))
