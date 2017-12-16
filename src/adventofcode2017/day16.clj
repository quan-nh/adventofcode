(ns adventofcode2017.day16
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def programs (mapv char (range (int \a) (inc (int \p)))))

(defn spin [n coll]
  (vec (concat (take-last n coll) (drop-last n coll))))

(defn exchange [x y coll]
  (-> coll
      (assoc x (coll y))
      (assoc y (coll x))))

(defn partner [a b coll]
  (exchange (.indexOf coll a) (.indexOf coll b) coll))

(def input (slurp (io/resource "2017/day16")))

(def input-fns (->> (str/split input #",")
                    (map #(cond
                            (re-matches #"^s\d+$" %)
                            (let [[_ n] (re-matches #"^s(\d+)$" %)]
                              (partial spin (Integer/parseInt n)))

                            (re-matches #"^x\d+/\d+$" %)
                            (let [[_ x y] (re-matches #"^x(\d+)/(\d+)$" %)]
                              (partial exchange (Integer/parseInt x) (Integer/parseInt y)))

                            :else
                            (let [[_ a b] (re-matches #"^p([a-p])/([a-p])$" %)]
                              (partial partner (first a) (first b)))))))

(def input-fn (apply comp (reverse input-fns)))

;; part 1
(apply str (input-fn programs))
; bijankplfgmeodhc

;; part 2
(def dance-seq (iterate input-fn programs))

(def period (->> (take-while #(not= (first dance-seq) %)
                             (rest dance-seq))
                 count
                 inc))

(apply str (nth dance-seq (mod 1000000000 period)))
; bpjahknliomefdgc
