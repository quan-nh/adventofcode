(ns adventofcode2017.day12
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def input (slurp (io/resource "2017/day12")))

(def data (->> (str/split-lines input)
               (map #(let [[a b] (str/split % #" <-> ")]
                       [(read-string a) (map read-string (str/split b #", "))]))
               (into {})))

(defn group [pid]
  (loop [path [pid]
         visit {pid #{}}]
    (let [id (last path)
          next-id (first (remove (get visit id #{}) (data id)))]
      (cond
        (some? next-id)
        (recur (conj path next-id) (update visit id (fnil conj #{}) next-id))

        (> (count path) 1)
        (recur (vec (drop-last path)) visit)

        :else
        (apply clojure.set/union (vals visit))))))

;; part 1
(count (group 0))
; 283

;; part 2
(loop [programs (keys data)
       group-count 0]
  (if (empty? programs)
    group-count
    (recur (remove (group (first programs)) programs) (inc group-count))))
; 195
