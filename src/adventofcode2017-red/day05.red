Red []

input: read/lines %../../resources/2017/day5

steps: 0

while [not tail? input] [
  a: to-integer first input
  change input (a + 1)
  input: skip input a
  steps: steps + 1
]

print steps
; 358309

;--- Part Two ---
input: read/lines %../../resources/2017/day5
steps: 0

while [not tail? input] [
  a: to-integer first input

  either a >= 3 [
    change input (a - 1)
  ] [
    change input (a + 1)
  ]
  
  input: skip input a
  steps: steps + 1
]

print steps
; 28178177
