(ns codingpuzzle.array-max-consecutive-sum
  (:require [net.cgrand.xforms :as x]))

;; Given array of integers, find the maximal possible sum of some of its k consecutive elements.

;; Example

;; For inputArray = [2, 3, 5, 1, 6] and k = 2, the output should be
;; arrayMaxConsecutiveSum(inputArray, k) = 8.
;; All possible sums of 2 consecutive elements are:

;; 2 + 3 = 5;
;; 3 + 5 = 8;
;; 5 + 1 = 6;
;; 1 + 6 = 7.
;; Thus, the answer is 8.

(transduce (x/partition 2 1 (x/reduce +)) max 0 [2, 3, 5, 1, 6])
