Red [
  url: https://www.codewars.com/kata/find-the-odd-int
]

find-odd: function [xs] [
  x: 0
  forall xs [x: x xor xs/1]
]

print  5 = find-odd [20 1 -1 2 -2 3 3 5 5 1 2 4 20 4 -1 -2 5]
print -1 = find-odd [1 1 2 -2 5 2 4 4 -1 -2 5]
print  5 = find-odd [20 1 1 2 2 3 3 5 5 4 20 4 5]
print 10 = find-odd [10]
print 10 = find-odd [1 1 1 1 1 1 10 1 1 1 1]
print  1 = find-odd [5 4 3 2 1 5 4 3 2 10 10]
