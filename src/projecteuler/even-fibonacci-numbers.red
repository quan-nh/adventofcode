Red [
  url: https://projecteuler.net/problem=2
]

a: b: 1 sum: 0
while [a <= 4000000] [
  set [a b] reduce [a + b a]
  if even? a [sum: sum + a]
]
print sum ; 4613732
