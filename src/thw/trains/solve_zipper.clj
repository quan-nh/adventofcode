(ns thw.trains.solve-zipper
  (:require [clojure.zip :as zip]))

(defn zp [graph root]
  (zip/zipper (comp seq (partial connected-towns graph))
              (partial connected-towns graph)
              zip/make-node
              root))

(defn- zip-children [loc]
  (when-let [child (zip/down loc)]
    (take-while (comp not nil?) (iterate zip/right child))))

#_(loop [ret []
         queue (conj PersistentQueue/EMPTY (zp graph "C"))]
    (if (seq queue)
      (let [[node children] ((juxt zip/node zip-children) (peek queue))]
        (recur (conj ret node) (into (pop queue) children)))
      ret))
