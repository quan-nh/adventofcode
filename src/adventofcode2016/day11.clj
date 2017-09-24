(ns adventofcode2016.day11
  (:require [clojure.math.combinatorics :as combo]
            [clojure.pprint :refer [pprint]]))

(defn next-floors [floor]
  (case floor
    :f1 [:f2]
    :f2 [:f3 :f1]
    :f3 [:f4 :f2]
    :f4 [:f3]))

(def safe?
  (memoize
    (fn [[_ chip] data]
      (contains? (set data) [:generator chip]))))

(def valid-data?
  (memoize
    (fn [data]
      (cond
        (empty? data) true
        (empty? (remove #(= :microchip (first %)) data)) true
        (empty? (remove #(= :generator (first %)) data)) true
        :else (->> (filter #(= :microchip (first %)) data)
                   (remove #(safe? % data))
                   empty?)))))

(defn valid-step? [{:keys [state floor]} [next-floor carry-data]]
  (and (valid-data? (remove #(contains? (set carry-data) %) (state floor)))
       (valid-data? (concat (state next-floor) carry-data))))

(defn all-steps [{:keys [state floor]}]
  (for [f (next-floors floor)
        carry-data (->> (state floor)
                        combo/subsets
                        (filter #(<= 1 (count %) 2)))]
    [f carry-data]))

(defn next-steps [state]
  (->> (all-steps state)
       (filter #(valid-step? state %))))

(defn next-state
  "Given current state & next step, return next state data."
  [{:keys [state floor]} [next-floor carry-data]]
  {:state (-> state
              (assoc floor (remove #(contains? (set carry-data) %) (state floor)))
              (assoc next-floor (concat (state next-floor) carry-data)))
   :floor next-floor})

(defn next-states
  "Given current state data, return all possible next states that not seen yet (nil if there is no valid next step)."
  [state seen-states]
  (let [nxt-steps (next-steps state)]
    (when-not (empty? nxt-steps)
      (->> (map #(next-state state %) nxt-steps)
           (remove #(contains? seen-states %))))))

(defn complete? [states]
  (seq (filter #(and (empty? (get-in % [:state :f1]))
                     (empty? (get-in % [:state :f2]))
                     (empty? (get-in % [:state :f3]))) states)))

(defn bfs [step states seen-states]
  (println "step" step)
  (println "no states" (count states))
  (println "seen states" (count seen-states))
  (if (complete? states)
    (println "result" step)
    (let [nxt-states (distinct (mapcat #(next-states % seen-states) states))]
      (bfs (inc step) nxt-states (into seen-states nxt-states)))))

(def input #_{:state {:f1 '([:microchip :hydrogen] [:microchip :lithium])
                      :f2 '([:generator :hydrogen])
                      :f3 '([:generator :lithium])
                      :f4 '()}
              :floor :f1}
  {:state {:f1 '([:generator :thulium] [:microchip :thulium] [:generator :plutonium] [:generator :strontium])
           :f2 '([:microchip :plutonium] [:microchip :strontium])
           :f3 '([:generator :promethium] [:microchip :promethium] [:generator :ruthenium] [:microchip :ruthenium])
           :f4 '()}
   :floor :f1})

(bfs 0 [input] #{input})