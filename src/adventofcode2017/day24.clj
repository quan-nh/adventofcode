(ns adventofcode2017.day24
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day24")))

(def data (->> (str/split-lines input)
               (map #(let [[x y] (str/split % #"/")]
                       [(read-string x) (read-string y)]))))

(defn next-component [curr-port data]
  (first (keep #(cond
                  (= curr-port (first %)) [% (second %)]
                  (= curr-port (second %)) [% (first %)])
               data)))

(defn bridge-strength [bridge]
  (->> bridge
       (map #(apply + %))
       (apply +)))

(def bridges
  (loop [curr-port 0
         path []
         visit {[] #{}}
         paths []]
    (let [[next-comp next-port] (next-component curr-port
                                                (remove (into (get visit path #{}) path)
                                                        data))]
      (cond
        ; move next
        (some? next-comp)
        (recur next-port (conj path next-comp) (update visit path (fnil conj #{}) next-comp) paths)

        ; go back
        (seq path)
        (let [[x y] (last path)
              prev-port (if (= curr-port x) y x)]
          (recur prev-port (vec (drop-last path)) visit (conj paths path)))

        ; return
        :else
        paths))))

;; part 1
(apply max (map bridge-strength bridges))
; 1695

;; part 2
(->> (group-by count bridges)
     (apply max-key key)
     val
     (map bridge-strength)
     (apply max))
; 1673
