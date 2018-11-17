Red [
  problem: {Let's define digit degree of some positive integer as the number of times we need to replace this number with the sum of its digits until we get to a one digit number.

            Given an integer, find its digit degree.

            Example

            For n = 5, the output should be
            digitDegree(n) = 0;
            For n = 100, the output should be
            digitDegree(n) = 1.
            1 + 0 + 0 = 1.
            For n = 91, the output should be
            digitDegree(n) = 2.
            9 + 1 = 10 -> 1 + 0 = 1.}  
  ]

digit-degree: function [n] [
  steps: sum-digits: 0
  while [n >= 10] [
    sum-digits: sum-digits + n % 10
    n: n / 10
    if n < 10 [
      n: sum-digits + n
      sum-digits: 0
      steps: steps + 1  
    ]
  ]
  steps
]

print digit-degree 5
print digit-degree 100
print digit-degree 91
print digit-degree 99
