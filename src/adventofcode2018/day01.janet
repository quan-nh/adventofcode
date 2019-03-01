(def input (->> (slurp "../../resources/2018/day01")
                (string/split "\n")
                (map scan-number)))
(array/pop input) # remove last nil value

# part 1
(pp (+ ;input)) #470

# part 2
(def list-frequency @{})
(var frequency 0)
(var i 0)

(while (not (list-frequency frequency))
  (put list-frequency frequency true)
  (+= frequency (input i))
  (++ i)
  (if (= i (length input)) (set i 0)))

(pp frequency) #790
