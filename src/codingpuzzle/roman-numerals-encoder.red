Red [
   Url: http://www.codewars.com/kata/roman-numerals-encoder
]

roman-numerals: [
  M  1000
  CM 900
  D  500
  CD 400
  C  100
  XC 90
  L  50
  XL 40
  X  10
  IX 9
  V  5
  IV 4
  I  1
]

solution: function [n] [
  s: copy []
  foreach [a b] roman-numerals [
    while [n >= b] [
      append s a
      n: n - b
    ]
  ]
  to-string s
]

print "I" = solution 1
print "IV" = solution 4
print "VI" = solution 6
print "XIV" = solution 14
print "XXI" = solution 21
print "LXXXIX" = solution 89
print "XCI" = solution 91
print "CMLXXXIV" = solution 984
print "M" = solution 1000
print "MDCCCLXXXIX" = solution 1889
print "MCMLXXXIX" = solution 1989
