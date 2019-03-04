(def input (slurp "../../resources/2018/day07"))

(def nodes @{})
(def edges @{})

# parse input
(def instructions
 ~{:letter (range "AZ")
   :instruction (* "Step " ':letter " must be finished before step " ':letter " can begin.\n")
   :main (some (cmt :instruction
                    ,(fn [a b]
                      (put nodes a true)
                      (put nodes b true)
                      (put edges [a b] true))))})

(peg/match instructions input)


(defn start-nodes
  "nodes with no incoming edge"
  [_nodes _edges]
  (let [tmp-nodes (table ;(kvs _nodes))]
    (loop [[_ a] :keys _edges]
      (put tmp-nodes a nil))
    (keys tmp-nodes)))
  
(defn neighbors [n _edges]
  (->> (keys _edges)
       (filter (fn [[a _]] (= a n)))
       (map last)))
  

(defn no-incoming? [n _edges]
  (empty? (filter (fn [[_ a]] (= a n)) (keys _edges))))

(def q (start-nodes nodes edges))


(def steps @[])
(def tmp-edges (table ;(kvs edges)))

(while (not (empty? q))
  (let [n (array/pop (sort q order>))]
    (array/push steps n)
    (each m (neighbors n edges)
      (put tmp-edges [n m] nil)
      (if (no-incoming? m tmp-edges) (array/push q m)))))

(pp (string/join steps)) # GJFMDHNBCIVTUWEQYALSPXZORK
