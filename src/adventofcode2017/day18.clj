(ns adventofcode2017.day18
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day18")))

(defn get-val [m k]
  (if (number? (read-string k))
    (read-string k)
    (get m k 0)))

;; part 1
(def instructions
  (->> (str/split-lines input)
       (map #(condp re-matches %
               #"snd .+"
               (let [[_ x] (re-matches #"snd (.+)" %)]
                 (fn [state i]
                   [(assoc state :snd (get-val state x))
                    (inc i)]))

               #"set .+ .+"
               (let [[_ x y] (re-matches #"set (.+) (.+)" %)]
                 (fn [state i]
                   [(assoc state x (get-val state y))
                    (inc i)]))

               #"add .+ .+"
               (let [[_ x y] (re-matches #"add (.+) (.+)" %)]
                 (fn [state i]
                   [(assoc state x (+ (get state x 0)
                                      (get-val state y)))
                    (inc i)]))

               #"mul .+ .+"
               (let [[_ x y] (re-matches #"mul (.+) (.+)" %)]
                 (fn [state i]
                   [(assoc state x (* (get state x 0)
                                      (get-val state y)))
                    (inc i)]))

               #"mod .+ .+"
               (let [[_ x y] (re-matches #"mod (.+) (.+)" %)]
                 (fn [state i]
                   [(assoc state x (mod (get state x 0)
                                        (get-val state y)))
                    (inc i)]))

               #"rcv .+"
               (let [[_ x] (re-matches #"rcv (.+)" %)]
                 (fn [state i]
                   (if (zero? (get-val state x))
                     [state (inc i)]
                     [(assoc state :rcv true) i])))

               #"jgz .+ .+"
               (let [[_ x y] (re-matches #"jgz (.+) (.+)" %)]
                 (fn [state i]
                   (if (pos? (get-val state x))
                     [state (+ i (get-val state y))]
                     [state (inc i)])))))))

(loop [state {}
       i 0]
  (let [[new-state j] ((nth instructions i) state i)]
    (if (:rcv new-state)
      (:snd new-state)
      (recur new-state j))))
; 3423

;; part 2
(def instructions2
  (->> (str/split-lines input)
       (map #(condp re-matches %
               #"snd .+"
               (let [[_ x] (re-matches #"snd (.+)" %)]
                 (fn [state queue rcv-queue i]
                   [(update state :snd inc)
                    (conj queue (get-val state x))
                    rcv-queue
                    (inc i)]))

               #"set .+ .+"
               (let [[_ x y] (re-matches #"set (.+) (.+)" %)]
                 (fn [state queue rcv-queue i]
                   [(assoc state x (get-val state y))
                    queue rcv-queue
                    (inc i)]))

               #"add .+ .+"
               (let [[_ x y] (re-matches #"add (.+) (.+)" %)]
                 (fn [state queue rcv-queue i]
                   [(assoc state x (+ (get state x 0)
                                      (get-val state y)))
                    queue rcv-queue
                    (inc i)]))

               #"mul .+ .+"
               (let [[_ x y] (re-matches #"mul (.+) (.+)" %)]
                 (fn [state queue rcv-queue i]
                   [(assoc state x (* (get state x 0)
                                      (get-val state y)))
                    queue rcv-queue
                    (inc i)]))

               #"mod .+ .+"
               (let [[_ x y] (re-matches #"mod (.+) (.+)" %)]
                 (fn [state queue rcv-queue i]
                   [(assoc state x (mod (get state x 0)
                                        (get-val state y)))
                    queue rcv-queue
                    (inc i)]))

               #"rcv .+"
               (let [[_ x] (re-matches #"rcv (.+)" %)]
                 (fn [state queue rcv-queue i]
                   (if-some [val (first rcv-queue)]
                     [(-> state
                          (assoc x val)
                          (assoc :rcv false))
                      queue
                      (vec (rest rcv-queue))
                      (inc i)]
                     [(assoc state :rcv true) queue rcv-queue i])))

               #"jgz .+ .+"
               (let [[_ x y] (re-matches #"jgz (.+) (.+)" %)]
                 (fn [state queue rcv-queue i]
                   (if (pos? (get-val state x))
                     [state queue rcv-queue (+ i (get-val state y))]
                     [state queue rcv-queue (inc i)])))))))

(loop [p0 {"p" 0 :snd 0}
       p1 {"p" 1 :snd 0}
       q0 []
       q1 []
       i 0
       j 0]
  (let [[new-p0 q00 q10 a] ((nth instructions2 i) p0 q0 q1 i)
        [new-p1 q11 q01 b] ((nth instructions2 j) p1 q10 q00 j)]
    (if (and (:rcv new-p0) (:rcv new-p1))
      (:snd new-p1)
      (recur new-p0 new-p1 q01 q11 a b))))
; 7493
