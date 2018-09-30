(ns codingpuzzle.box-blur
  "https://app.codesignal.com/arcade/intro/level-5/5xPitc3yT3dqS7XkP")

(defn group-x [x]
  (->> x
       (partition 3 1)
       (map #(reduce + %))))

(defn group-y [[a b c]]
  (map #(quot (+ %1 %2 %3) 9) a b c))

(defn boxBlur [image]
  (->> image
       (map group-x)
       (partition 3 1)
       (map group-y)))
