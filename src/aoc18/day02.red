Red []

input: read/lines %../../resources/2018/day02
;input: ["abcdef" "bababc" "abbcde" "abcccd" "aabcdd" "abcdee" "ababab"]
frequency: function [s] [
  result: copy []
  foreach ch s [
    either freq: find result ch [
      change next freq freq/2 + 1
    ] [
      repend result [ch 1] 
    ]
  ]
  result
]

twos: threes: 0
foreach s input [
  count-two?: count-three?: no
  foreach [ch n] frequency s [
    if count-two? and count-three? [break]
    if n = 2 and not count-two? [twos: twos + 1 count-two?: yes]
    if n = 3 and not count-three? [threes: threes + 1 count-three?: yes]
  ]
]

print twos * threes
