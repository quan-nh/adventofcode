(ns adventofcode.day15
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (line-seq (io/reader (io/resource "day15"))))

(def ingredients
  (->> input
       (map #(str/replace % #":" ""))
       (map #(read-string (str "(" % ")")))
       (map (fn [[ingredient _ capacity _ durability _ flavor _ texture _ calories]]
              [(keyword ingredient) {:capacity capacity :durability durability :flavor flavor :texture texture :calories calories}]))
       (into {})))

(apply max (for [a (range 1 100)
                 b (range 1 (- 100 a))
                 c (range 1 (- 100 a b))
                 :let [d (- 100 a b c)
                       total-capacity (+ (* a (get-in ingredients [:Frosting :capacity]))
                                         (* b (get-in ingredients [:Candy :capacity]))
                                         (* c (get-in ingredients [:Butterscotch :capacity]))
                                         (* d (get-in ingredients [:Sugar :capacity])))
                       total-durability (+ (* a (get-in ingredients [:Frosting :durability]))
                                           (* b (get-in ingredients [:Candy :durability]))
                                           (* c (get-in ingredients [:Butterscotch :durability]))
                                           (* d (get-in ingredients [:Sugar :durability])))
                       total-flavor (+ (* a (get-in ingredients [:Frosting :flavor]))
                                       (* b (get-in ingredients [:Candy :flavor]))
                                       (* c (get-in ingredients [:Butterscotch :flavor]))
                                       (* d (get-in ingredients [:Sugar :flavor])))
                       total-texture (+ (* a (get-in ingredients [:Frosting :texture]))
                                        (* b (get-in ingredients [:Candy :texture]))
                                        (* c (get-in ingredients [:Butterscotch :texture]))
                                        (* d (get-in ingredients [:Sugar :texture])))
                       total-calories (+ (* a (get-in ingredients [:Frosting :calories]))
                                         (* b (get-in ingredients [:Candy :calories]))
                                         (* c (get-in ingredients [:Butterscotch :calories]))
                                         (* d (get-in ingredients [:Sugar :calories])))]
                 :when (= 500 total-calories)]
             (if (some neg? [total-capacity total-durability total-flavor total-texture])
               0
               (* total-capacity total-durability total-flavor total-texture))))