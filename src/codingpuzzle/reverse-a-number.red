Red [
  url: http://www.codewars.com/kata/reverse-a-number
]

reverse-number: function [n] [
  s: to-string n
  either (first s) = #"-" [reverse next s] [reverse s]
  to-integer s
]

probe reverse-number 123
probe reverse-number -456
probe reverse-number 1000
