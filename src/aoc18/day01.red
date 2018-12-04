Red []

input: read/lines %../../resources/2018/day01

; part 1
result: 0
foreach freq input [
  result: result + to integer! freq
]

probe result ; 470

; part 2
result: 0
list: make hash! [0]

forever [
  duplicate: false
  foreach freq input [
    result: result + to integer! freq
    either find list result [
      duplicate: true break
    ][
      append list result
    ]
  ]
  if duplicate [break]
]

probe result ; 790
