Red [url: https://adventofcode.com/2018/day/1]

input: load %../../resources/2018/day01

; part 1
probe sum input ; 470

; part 2
result: 0
list: make hash! [0]
duplicate: false

until [
  foreach freq input [
    result: result + to integer! freq
    either find list result [
      duplicate: true break
    ][
      append list result
    ]
  ]
  duplicate
]

probe result ; 790
