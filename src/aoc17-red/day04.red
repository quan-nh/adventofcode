Red []

input: read/lines %../../resources/2017/day4

valid?: function [passphrase] [
  words: split passphrase space

  forall words [
    next-words: next words

    while [not tail? next-words] [
      if (first words) = (first next-words) [return false]

      next-words: next next-words
    ]
  ]

  return true
]

count: 0

foreach line input [
  if valid? line [count: count + 1]
]

print count
; 455

;--- Part Two ---
valid2?: function [passphrase] [
  words: split passphrase space

  forall words [
    next-words: next words

    while [not tail? next-words] [
      if empty? difference (first words) (first next-words) [
        return false
      ]

      next-words: next next-words
    ]
  ]

  return true
]

count: 0

foreach line input [
  if valid2? line [count: count + 1]
]

print count
; 186
