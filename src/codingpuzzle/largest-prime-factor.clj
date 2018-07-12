(ns project-euler.problem-3
  "Largest prime factor
  The prime factors of 13195 are 5, 7, 13 and 29.
  What is the largest prime factor of the number 600851475143 ?")

(defn prime-factors [n]
  (loop [n n
         d 2
         factors #{}]
    (if (= 1 n)
      factors
      (if (zero? (mod n d))
        (recur (/ n d) d (conj factors d))
        (recur n (inc d) factors)))))

(prime-factors 13195)

(apply max (prime-factors 600851475143))
; 6857
