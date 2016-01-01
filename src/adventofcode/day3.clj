(ns adventofcode.day3)

(defn num-houses [s]
  (loop [str s
         result #{[0 0]}
         [x y] [0 0]]
    (if (nil? str)
      (count result)
      (let [[first & rest] str]
        (case first
          \^ (recur rest (conj result [x (inc y)]) [x (inc y)])
          \v (recur rest (conj result [x (dec y)]) [x (dec y)])
          \> (recur rest (conj result [(inc x) y]) [(inc x) y])
          \< (recur rest (conj result [(dec x) y]) [(dec x) y]))))))

(defn num-houses-with-robot [s]
  (loop [str s
         santa-result #{[0 0]}
         robo-result #{[0 0]}
         [santa-x santa-y] [0 0]
         [robo-x robo-y] [0 0]]
    (if (nil? str)
      (count (into santa-result robo-result))
      (let [[first second & rest] str]
        (case [first second]
          [\^ \^] (recur rest (conj santa-result [santa-x (inc santa-y)]) (conj robo-result [robo-x (inc robo-y)]) [santa-x (inc santa-y)] [robo-x (inc robo-y)])
          [\^ \v] (recur rest (conj santa-result [santa-x (inc santa-y)]) (conj robo-result [robo-x (dec robo-y)]) [santa-x (inc santa-y)] [robo-x (dec robo-y)])
          [\^ \>] (recur rest (conj santa-result [santa-x (inc santa-y)]) (conj robo-result [(inc robo-x) robo-y]) [santa-x (inc santa-y)] [(inc robo-x) robo-y])
          [\^ \<] (recur rest (conj santa-result [santa-x (inc santa-y)]) (conj robo-result [(dec robo-x) robo-y]) [santa-x (inc santa-y)] [(dec robo-x) robo-y])
          [\v \^] (recur rest (conj santa-result [santa-x (dec santa-y)]) (conj robo-result [robo-x (inc robo-y)]) [santa-x (dec santa-y)] [robo-x (inc robo-y)])
          [\v \v] (recur rest (conj santa-result [santa-x (dec santa-y)]) (conj robo-result [robo-x (dec robo-y)]) [santa-x (dec santa-y)] [robo-x (dec robo-y)])
          [\v \>] (recur rest (conj santa-result [santa-x (dec santa-y)]) (conj robo-result [(inc robo-x) robo-y]) [santa-x (dec santa-y)] [(inc robo-x) robo-y])
          [\v \<] (recur rest (conj santa-result [santa-x (dec santa-y)]) (conj robo-result [(dec robo-x) robo-y]) [santa-x (dec santa-y)] [(dec robo-x) robo-y])
          [\> \^] (recur rest (conj santa-result [(inc santa-x) santa-y]) (conj robo-result [robo-x (inc robo-y)]) [(inc santa-x) santa-y] [robo-x (inc robo-y)])
          [\> \v] (recur rest (conj santa-result [(inc santa-x) santa-y]) (conj robo-result [robo-x (dec robo-y)]) [(inc santa-x) santa-y] [robo-x (dec robo-y)])
          [\> \>] (recur rest (conj santa-result [(inc santa-x) santa-y]) (conj robo-result [(inc robo-x) robo-y]) [(inc santa-x) santa-y] [(inc robo-x) robo-y])
          [\> \<] (recur rest (conj santa-result [(inc santa-x) santa-y]) (conj robo-result [(dec robo-x) robo-y]) [(inc santa-x) santa-y] [(dec robo-x) robo-y])
          [\< \^] (recur rest (conj santa-result [(dec santa-x) santa-y]) (conj robo-result [robo-x (inc robo-y)]) [(dec santa-x) santa-y] [robo-x (inc robo-y)])
          [\< \v] (recur rest (conj santa-result [(dec santa-x) santa-y]) (conj robo-result [robo-x (dec robo-y)]) [(dec santa-x) santa-y] [robo-x (dec robo-y)])
          [\< \>] (recur rest (conj santa-result [(dec santa-x) santa-y]) (conj robo-result [(inc robo-x) robo-y]) [(dec santa-x) santa-y] [(inc robo-x) robo-y])
          [\< \<] (recur rest (conj santa-result [(dec santa-x) santa-y]) (conj robo-result [(dec robo-x) robo-y]) [(dec santa-x) santa-y] [(dec robo-x) robo-y]))))))
