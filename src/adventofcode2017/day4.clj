(ns adventofcode2017.day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day4")))

;; part 1
(defn valid? [passphrase]
  (apply distinct? (str/split passphrase #"\s")))

(->> (str/split-lines input)
     (filter valid?)
     count)
; 455

;; part 2
(defn valid2? [passphrase]
  (->> (str/split passphrase #"\s")
       (map frequencies)
       (apply distinct?)))

(->> (str/split-lines input)
     (filter valid2?)
     count)
; 186
