Red [
  Url: https://adventofcode.com/2017/day/11
]

input: split read %../../resources/2017/day11 ","

move: function [pos dir][
  switch dir [
    "n"  [reduce [pos/1 (pos/2 + 1) (pos/3 - 1)]]
    "ne" [reduce [(pos/1 + 1) pos/2 (pos/3 - 1)]]
    "se" [reduce [(pos/1 + 1) (pos/2 -1) pos/3]]
    "s"  [reduce [pos/1 (pos/2 -1) (pos/3 + 1)]]
    "sw" [reduce [(pos/1 - 1) pos/2 (pos/3 + 1)]]
    "nw" [reduce [(pos/1 - 1) (pos/2 + 1) pos/3]]
  ]
]

steps: function [pos] [
  result: 0
  foreach x pos [
    result: max result absolute x
  ]
]

pos: copy [0 0 0]
max-steps: 0

foreach dir input [
  pos: move pos dir
  curr-steps: steps pos
  max-steps: max max-steps curr-steps
]

print curr-steps
; 764

print max-steps
; 1532
