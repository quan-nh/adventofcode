(ns adventofcode2017.day7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (slurp (io/resource "2017/day7")))

(def towers (->> (str/split-lines input)
                 (map #(let [[_ parent weight _ children] (re-matches #"([a-z]+)\s\((\d+)\)( -> )?(.+)?" %)]
                         [parent {:weight   (Integer/parseInt weight)
                                  :children (some-> children (str/split #", "))}]))
                 (into {})))

(defn parent [node]
  (first (keep (fn [[p {:keys [children]}]]
                 (when (some #{node} children) p))
               towers)))

;; part 1
(def root (last (take-while some?
                            (iterate parent (first (keys towers))))))
; "mwzaxaj"

;; part 2
(defn travel [node weights]
  (let [children (get-in towers [node :children])
        visit (set (keys weights))
        unvisit (first (remove visit children))]
    (cond
      (some? unvisit)
      (travel unvisit (assoc weights unvisit (get-in towers [unvisit :weight])))

      (or (nil? children)
          (apply = (vals (select-keys weights children))))
      (travel (parent node) (update weights (parent node) + (weights node)))

      :else
      (select-keys weights children))))

(travel root {root (get-in towers [root :weight])})
; {"vrgxe" 2166,
;  "shnqfh" 2159,
;  "auzded" 2159,
;  "hkhsc" 2159,
;  "jwddn" 2159,
;  "mcxki" 2159,
;  "lhwyt" 2159}

(let [{correct      :correct
       [wrong node] :wrong} (->> (travel root {root (get-in towers [root :weight])})
                                 (group-by second)
                                 (map (fn [[k v]]
                                        (if (> (count v) 1)
                                          [:correct k]
                                          [:wrong [k (ffirst v)]])))
                                 (into {}))]
  (- (get-in towers [node :weight]) (- wrong correct)))
; 1219
