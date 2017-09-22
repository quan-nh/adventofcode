(ns adventofcode2016.util
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def any? (complement not-any?))

(defn sorted-map-by-vals [m]
  (into (sorted-map-by (fn [key1 key2]
                         (compare [(get m key2) key1]
                                  [(get m key1) key2])))
        m))
