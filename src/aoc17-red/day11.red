Red [
  Url: https://adventofcode.com/2017/day/11
]

input: split read %../../resources/2017/day11 ","

move: function [pos dir][
  cases: [
    "n"  [0 1 -1]
    "ne" [1 0 -1]
    "se" [1 -1 0]
    "s"  [0 -1 1]
    "sw" [-1 0 1]
    "nw" [-1 1 0]
  ]

  pos + make vector! select cases dir
]

steps: function [pos] [
  max max absolute pos/1 absolute pos/2 absolute pos/3
]

pos: make vector! [0 0 0]
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
