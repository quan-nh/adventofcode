Red [
  Url: https://adventofcode.com/2017/day/11
]

input: split read %../../resources/2017/day11 ","

direction: reduce [
  "n"  (make vector! [0 1 -1])
  "ne" (make vector! [1 0 -1])
  "se" (make vector! [1 -1 0])
  "s"  (make vector! [0 -1 1])
  "sw" (make vector! [-1 0 1])
  "nw" (make vector! [-1 1 0])
]

pos: make vector! [0 0 0]
curr-steps: 0
max-steps: 0

foreach dir input [
  pos: pos + select direction dir
  curr-steps: max max absolute pos/1 absolute pos/2 absolute pos/3
  max-steps: max max-steps curr-steps
]

print curr-steps
; 764

print max-steps
; 1532
