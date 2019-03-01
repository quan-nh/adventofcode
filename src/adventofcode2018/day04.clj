(ns aoc18.day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import (java.time.format DateTimeFormatter)
           (java.time LocalDateTime)
           (java.time.temporal ChronoUnit)))

(def input (slurp (io/resource "2018/day04")))

(def guard-pattern #"\[(.+)\] Guard #(\d+) begins shift")
(def asleep-pattern #"\[(.+)\] falls asleep")
(def awake-pattern #"\[(.+)\] wakes up")
(def dt-formatter (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm"))

(defn parse-line [s]
  (condp re-matches s
    guard-pattern
    (let [[_ time guard-id] (re-matches guard-pattern s)]
      [(LocalDateTime/parse time dt-formatter) (Integer/parseInt guard-id)])

    asleep-pattern
    (let [[_ time] (re-matches asleep-pattern s)]
      [(LocalDateTime/parse time dt-formatter) :falls-asleep])

    awake-pattern
    (let [[_ time] (re-matches awake-pattern s)]
      [(LocalDateTime/parse time dt-formatter) :wakes-up])))

(def records (->> (str/split-lines input)
                  (map parse-line)
                  (sort-by first)))

(defn minutes [start-time stop-time]
  (loop [time start-time
         results []]
    (if (= time stop-time)
      results
      (recur (.plusMinutes time 1) (conj results (.getMinute time))))))

(def m (first
         (reduce (fn [[m current-guard] [dt id]]
                   (case id
                     :falls-asleep
                     [(update m current-guard conj dt) current-guard]

                     :wakes-up
                     (let [dts (m current-guard)]
                       [(assoc m current-guard (apply conj (pop (update dts 0 + (.between ChronoUnit/MINUTES (last dts) dt)))
                                                      (minutes (last dts) dt)))
                        current-guard])

                     (if (m id)
                       [m id]
                       [(assoc m id [0]) id])))
                 [{} nil]
                 records)))

;; part 1
(let [[id [_ & mins]] (last (sort-by (comp first val) m))
      [min _] (last (sort-by val (frequencies mins)))]
  (prn (* id min)))
; 76357

;; part 2
(let [[id [min _]]
      (->> m
           (map (fn [[k v]]
                  [k (last (sort-by val (frequencies (rest v))))]))
           (sort-by (comp second second))
           last)]
  (* id min))
; 41668
