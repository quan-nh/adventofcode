Red [Title: "Inverse Captcha"]

comment {
  The captcha requires you to review a sequence of digits (your puzzle input) and find the sum of all digits that match the next digit in the list. The list is circular, so the digit after the last digit is the first digit in the list.

  For example:
    - 1122 produces a sum of 3 (1 + 2) because the first digit (1) matches the second digit and the third digit (2) matches the fourth digit.
    - 1111 produces 4 because each digit (all 1) matches the next.
    - 1234 produces 0 because no digit matches the next.
    - 91212129 produces 9 because the only digit that matches the next one is the last digit, 9.

  What is the solution to your captcha?
}

digits: read %../../resources/2017/day1

sum: 0

forall digits [
  next-digits: next digits
  if tail? next-digits [next-digits: head digits]

  a: first digits
  b: first next-digits 

  if a = b [sum: sum + to-integer form a]
]

print sum
; 1253

;--- Part Two ---
comment {
  Now, instead of considering the next digit, it wants you to consider the digit halfway around the circular list. That is, if your list contains 10 items, only include a digit in your sum if the digit 10/2 = 5 steps forward matches it. Fortunately, your list has an even number of elements.

  For example:

  - 1212 produces 6: the list contains 4 items, and all four digits match the digit 2 items ahead.
  - 1221 produces 0, because every comparison is between a 1 and a 2.
  - 123425 produces 4, because both 2s match each other, but no other digit has a match.
  - 123123 produces 12.
  - 12131415 produces 4.

  What is the solution to your new captcha?
}

sum: 0

next-digits: skip digits (length? digits) / 2
forall digits [
  a: first digits
  b: first next-digits 

  if a = b [sum: sum + to-integer form a]

  next-digits: next next-digits
  if tail? next-digits [next-digits: head digits]
]

print sum
; 1278
