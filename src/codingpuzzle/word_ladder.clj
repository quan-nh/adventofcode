(ns codingpuzzle.word-ladder)
;; Word Ladder is a fun and interesting word game that involves transforming one word into another, by changing exactly one letter at a time and maintaining a valid word at each step.

;; In this task, you'll be given startWord and goalWord, and your task is to find a word ladder that connects them, using words from the usableWords list.

;; Normally the goal would be to find the shortest ladder that connects startWord and goalWord, but in this case, we're interested in finding the longest possible word ladder. We're not allowed to re-use words from the list (otherwise the ladder could end up being infinitely long), but some words might appear more than once in usableWords, in which case we can use them as many times as they appear.

;; If it's not possible to form a word ladder with the given inputs, return []. Otherwise, return the word ladder as an array of strings, beginning with startWord ending with goalWord, and with all the selected words from usableWords in between.

;; If there are multiple possible answers, return the lexicographically smallest one.

;; Example

;; For startWord = "code", goalWord = "dope", and usableWords = ["cone", "bonk", "none", "dole", "tune", "node", "mode", "nope", "mole"], the output should be

;; longestWordLadder(startWord, goalWord, usableWords) = [
;;   "code",
;;   "cone",
;;   "none",
;;   "nope",
;;   "node",
;;   "mode",
;;   "mole",
;;   "dole",
;;   "dope"
;; ]

;; Although it's possible to get from startWord to goalWord in just 3 moves ("code" -> "node" -> "nope" -> "dope"), the ladder can be a lot longer than that. Also note that even though we're trying to find the lexicographically smallest option amongst the longest word ladders, this doesn't necessarily mean they'll be in order - for example, "nope" comes before "node", so that it can connect with "mode".

;; For startWord = "functional", goalWord = "javascript", and usableWords = ["incredible", "programmer"], the output should be longestWordLadder(startWord, goalWord, usableWords) = [].

;; There's no possible way to form a ladder with these inputs.

(defn remove-indexed [coll idx]
  (into (subvec coll 0 idx) (subvec coll (inc idx))))

(defn differ? [a b]
  (= 1 (count (remove zero? (map #(bit-xor (int %1) (int %2)) a b)))))

(defn next-nodes [a d coll]
  (keep-indexed (fn [idx b]
                  (when (differ? a b)
                    [[b (inc d)] (remove-indexed coll idx)]))
                coll))

(defn path-to [goal]
  (fn dfs [path result stack]
    (if (empty? stack) result
                       (let [[[node level] remain] (peek stack)
                             new-result (if (differ? (last path) goal)
                                          (conj result (conj path goal))
                                          result)]
                         (if (= (inc (count path)) level)
                           (dfs (conj path node)
                                new-result
                                (apply conj (pop stack) (next-nodes node level remain)))
                           (dfs (pop path)
                                new-result
                                stack))))))

(defn word-ladder [start goal words]
  ((path-to goal) [] #{} [[[start 1] words]]))


(word-ladder "code" "dope" ["cone", "bonk", "none", "dole", "tune", "node", "mode", "nope", "mole"])
;;#{["code" "node" "nope" "dope"]
;;  ["code" "mode" "node" "none" "nope" "dope"]
;;  ["code" "mode" "node" "nope" "dope"]
;;  ["code" "node" "mode" "mole" "dole" "dope"]
;;  ["code" "node" "none" "nope" "dope"]
;;  ["code" "cone" "none" "nope" "dope"]
;;  ["code" "cone" "none" "node" "nope" "dope"]
;;  ["code" "mode" "mole" "dole" "dope"]
;;  ["code" "cone" "none" "nope" "node" "mode" "mole" "dole" "dope"]}

(word-ladder "functional" "javascript" ["incredible", "programmer"])
;; #{}
