(ns adventofcode.day7
  (:require [clojure.string :as str]))

(def instructions (atom []))
(def result (atom {}))

(defn signal->wire [instruction]
  (let [[_ signal wire :as all] (re-find #"^(\d+) -> (\w+)$" instruction)]
    (when-not (nil? all)
      (swap! result assoc wire (read-string signal))
      (swap! instructions (fn [coll] (remove #(= instruction %) coll))))))

(defn wire->wire [instruction]
  (let [[_ wire-in wire-out :as all] (re-find #"^(\w+) -> (\w+)$" instruction)]
    (when (and (not (nil? all))
               (contains? @result wire-in))
      (swap! result assoc wire-out (get @result wire-in))
      (swap! instructions (fn [coll] (remove #(= instruction %) coll))))))

(defn not-wire->wire [instruction]
  (let [[_ wire-in wire-out :as all] (re-find #"^NOT (\w+) -> (\w+)$" instruction)]
    (when (and (not (nil? all))
               (contains? @result wire-in))
      (swap! result assoc wire-out (bit-not (get @result wire-in)))
      (swap! instructions (fn [coll] (remove #(= instruction %) coll))))))

(defn wire-shift->wire [instruction]
  (let [[_ wire-in operator signal wire-out :as all] (re-find #"^(\w+) ([LR]SHIFT) (\d+) -> (\w+)$" instruction)]
    (when (and (not (nil? all))
               (contains? @result wire-in))
      (case operator
        "LSHIFT" (swap! result assoc wire-out (bit-shift-left (get @result wire-in) (read-string signal)))
        "RSHIFT" (swap! result assoc wire-out (bit-shift-right (get @result wire-in) (read-string signal))))
      (swap! instructions (fn [coll] (remove #(= instruction %) coll))))))

(defn wire-bitwise-wire->wire [instruction]
  (let [[_ wire-in-1 operator wire-in-2 wire-out :as all] (re-find #"^(\w+) ([A-Z]+) (\w+) -> (\w+)$" instruction)]
    (when (and (not (nil? all))
               (contains? @result wire-in-1)
               (contains? @result wire-in-2))
      (case operator
        "AND" (swap! result assoc wire-out (bit-and (get @result wire-in-1) (get @result wire-in-2)))
        "OR" (swap! result assoc wire-out (bit-or (get @result wire-in-1) (get @result wire-in-2))))
      (swap! instructions (fn [coll] (remove #(= instruction %) coll))))))

(defn signal-bitwise-wire->wire [instruction]
  (let [[_ signal operator wire-in wire-out :as all] (re-find #"^(\d+) ([A-Z]+) (\w+) -> (\w+)$" instruction)]
    (when (and (not (nil? all))
               (contains? @result wire-in))
      (case operator
        "AND" (swap! result assoc wire-out (bit-and (read-string signal) (get @result wire-in)))
        "OR" (swap! result assoc wire-out (bit-or (read-string signal) (get @result wire-in))))
      (swap! instructions (fn [coll] (remove #(= instruction %) coll))))))

(defn calculate-signals [s]
  (reset! instructions (str/split s #"\n"))
  (while (not (contains? @result "a"))
    (doseq [instruction @instructions]
      (signal->wire instruction)
      (wire->wire instruction)
      (not-wire->wire instruction)
      (wire-shift->wire instruction)
      (wire-bitwise-wire->wire instruction)
      (signal-bitwise-wire->wire instruction)))
  (get @result "a"))