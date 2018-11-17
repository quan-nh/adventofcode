Red [
  problem: {
    Given array of integers, find the maximal possible sum of some of its k consecutive elements.

    Example

    For inputArray = [2, 3, 5, 1, 6] and k = 2,
    the output should be arrayMaxConsecutiveSum(inputArray, k) = 8.
    
    All possible sums of 2 consecutive elements are:
     2 + 3 = 5;
     3 + 5 = 8;
     5 + 1 = 6;
     1 + 6 = 7.
    Thus, the answer is 8.
    }
]

max-consecutive-sum: function [blk k] [
  i: 1
  sum: max-val: 0

  repeat i length? blk [
    either i <= k [
      sum: sum + blk/:i
    ][
      max-val: max sum max-val
      j: i - k
      sum: sum + blk/:i - blk/:j  
    ]
  ]
  max sum max-val
]

print max-consecutive-sum [2 3 5 1 6] 2 ;= 8
print max-consecutive-sum [2 4 10 1] 2  ;= 14
print max-consecutive-sum [1 3 2 4] 3   ;= 9
print max-consecutive-sum [3 2 1 1] 1   ;= 3
