(ns thw.romannumerals.solve
  (:require [instaparse.core :as insta :refer [defparser]]))

(defparser p
           "roman = thousands? hundreds? tens? ones?
            thousands = M | MM | MMM
            hundreds = hunRep | CD | CM | D hunRep*
            <hunRep> = C | CC | CCC
            tens = tenRep | XL | XC | L tenRep*
            <tenRep> = X | XX | XXX
            ones = oneRep | IV | IX | V oneRep*
            <oneRep> = I | II | III

            (* based symbols *)
            I = 'I'
            V = 'V'
            X = 'X'
            L = 'L'
            C = 'C'
            D = 'D'
            M = 'M'

            (* The symbols I, X, C, and M can be repeated three times in succession, but no more. *)
            II = 'II'
            III = 'III'
            XX = 'XX'
            XXX = 'XXX'
            CC = 'CC'
            CCC = 'CCC'
            MM = 'MM'
            MMM = 'MMM'

            (* I can be subtracted from V and X only *)
            IV = 'IV'
            IX = 'IX'

            (* X can be subtracted from L and C only *)
            XL = 'XL'
            XC = 'XC'

            (* C can be subtracted from D and M only *)
            CD = 'CD'
            CM = 'CM'")

(def transform #(do (clojure.pprint/pprint %)
                    (insta/transform
                      {:I         (constantly 1)
                       :II        (constantly 2)
                       :III       (constantly 3)
                       :IV        (constantly 4)
                       :V         (constantly 5)
                       :IX        (constantly 9)
                       :X         (constantly 10)
                       :XX        (constantly 20)
                       :XXX       (constantly 30)
                       :XL        (constantly 40)
                       :L         (constantly 50)
                       :XC        (constantly 90)
                       :C         (constantly 100)
                       :CC        (constantly 200)
                       :CCC       (constantly 300)
                       :CD        (constantly 400)
                       :D         (constantly 500)
                       :CM        (constantly 900)
                       :M         (constantly 1000)
                       :MM        (constantly 2000)
                       :MMM       (constantly 3000)
                       :thousands +
                       :hundreds  +
                       :tens      +
                       :ones      +
                       :roman     +}
                      %)))

(transform (p "MMVI"))
(transform (p "XXXIX"))
(transform (p "MCMIII"))
(transform (p "MCMXLIV"))
