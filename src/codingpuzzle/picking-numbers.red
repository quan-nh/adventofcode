Red [
  url: https://www.hackerrank.com/challenges/picking-numbers/problem  
]

picking-numbers: function [a] [
  sort a
  p: copy reduce [first a none]
  len: copy [1 0]
  max-len: 1
  foreach i next a [
    switch/default i - p/1 [
      0 [len/1: len/1 + 1]
      1 [len/2: len/2 + 1 p/2: i]
    ] [
      max-len: max len/1 + len/2 max-len
      either all [p/2 i - p/2 = 1] [
        p/1: p/2 p/2: i
        len/1: len/2 len/2: 1
      ] [
        p/1: i p/2: none
        len/1: 1 len/2: 0
      ]
    ]
  ]
  max len/1 + len/2 max-len
]

print 4 = picking-numbers [1 2 2 3 3]
print 5 = picking-numbers [1 1 2 2 4 4 5 5 5]
print 3 = picking-numbers [4 6 5 3 3 1]
print 5 = picking-numbers [1 2 2 3 1 2]
