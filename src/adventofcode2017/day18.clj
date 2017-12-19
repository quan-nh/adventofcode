(ns adventofcode2017.day18
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day18")))

(def instructions (->> (str/split-lines input)
                       (map #(str/split % #"\s"))))

(defn get-val [m k]
  (if (number? (read-string k))
    (read-string k)
    (get m k 0)))

;; part 1
(defmulti command first)

(defmethod command "snd" [[_ x]]
  (fn [state i]
    [(assoc state :snd (get-val state x))
     (inc i)]))

(defmethod command "set" [[_ x y]]
  (fn [state i]
    [(assoc state x (get-val state y))
     (inc i)]))

(defmethod command "add" [[_ x y]]
  (fn [state i]
    [(update state x (fnil + 0) (get-val state y))
     (inc i)]))

(defmethod command "mul" [[_ x y]]
  (fn [state i]
    [(update state x (fnil * 0) (get-val state y))
     (inc i)]))

(defmethod command "mod" [[_ x y]]
  (fn [state i]
    [(update state x (fnil rem 0) (get-val state y))
     (inc i)]))

(defmethod command "rcv" [[_ x]]
  (fn [state i]
    (if (zero? (get-val state x))
      [state (inc i)]
      [(assoc state :rcv true) i])))

(defmethod command "jgz" [[_ x y]]
  (fn [state i]
    (if (pos? (get-val state x))
      [state (+ i (get-val state y))]
      [state (inc i)])))

(loop [state {}
       i 0]
  (let [[new-state j] ((command (nth instructions i)) state i)]
    (if (:rcv new-state)
      (:snd new-state)
      (recur new-state j))))
; 3423

;; part 2
(comment
  (defmethod command "snd" [[_ x]]
    (fn [state i]
      [(-> state
           (update :snd inc)
           (update :snd-queue #(conj % (get-val state x))))
       (inc i)]))

  (defmethod command "rcv" [[_ x]]
    (fn [state i]
      (if-some [val (first (:rcv-queue state))]
        [(-> state
             (assoc x val)
             (assoc :rcv false)
             (update :rcv-queue #(vec (rest %))))
         (inc i)]
        [(assoc state :rcv true) i])))

  (defn transfer [state-a state-b]
    (-> state-b
        (assoc :snd-queue (:rcv-queue state-a))
        (assoc :rcv-queue (:snd-queue state-a))))

  (loop [p0 {"p" 0 :snd 0 :rcv false :snd-queue [] :rcv-queue []}
         p1 {"p" 1 :snd 0 :rcv false :snd-queue [] :rcv-queue []}
         i 0
         j 0]
    (let [[new-p0 a] ((command (nth instructions i)) p0 i)
          [new-p1 b] ((command (nth instructions j)) (transfer new-p0 p1) j)]
      (if (every? :rcv [new-p0 new-p1])
        (:snd new-p1)
        (recur (transfer new-p1 new-p0) new-p1 a b))))
  ; 7493
  )
