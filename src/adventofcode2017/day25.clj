(ns adventofcode2017.day25)

(loop [[tape cursor state] [{} 0 :A]
       steps 0]
  (if (= 12481997 steps)
    (count (filter #(= 1 %) (vals tape)))
    (case state
      :A
      (recur (if (zero? (get tape cursor 0))
               [(assoc tape cursor 1) (inc cursor) :B]
               [(assoc tape cursor 0) (dec cursor) :C])
             (inc steps))

      :B
      (recur (if (zero? (get tape cursor 0))
               [(assoc tape cursor 1) (dec cursor) :A]
               [(assoc tape cursor 1) (inc cursor) :D])
             (inc steps))

      :C
      (recur (if (zero? (get tape cursor 0))
               [(assoc tape cursor 0) (dec cursor) :B]
               [(assoc tape cursor 0) (dec cursor) :E])
             (inc steps))

      :D
      (recur (if (zero? (get tape cursor 0))
               [(assoc tape cursor 1) (inc cursor) :A]
               [(assoc tape cursor 0) (inc cursor) :B])
             (inc steps))

      :E
      (recur (if (zero? (get tape cursor 0))
               [(assoc tape cursor 1) (dec cursor) :F]
               [(assoc tape cursor 1) (dec cursor) :C])
             (inc steps))

      :F
      (recur (if (zero? (get tape cursor 0))
               [(assoc tape cursor 1) (inc cursor) :D]
               [(assoc tape cursor 1) (inc cursor) :A])
             (inc steps)))))
; 3362
