Red [
  url: https://projecteuler.net/problem=3
]

largest-prime-factor: function [n] [
  if n < 2 [return none]
  p: 2
  while [n <> 1] [
    either zero? n // p [n: n / p] [p: p + 1]
  ]
  p
]

print largest-prime-factor 600851475143
; 6857
