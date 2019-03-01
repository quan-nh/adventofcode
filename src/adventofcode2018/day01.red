Red []

input: load %../../resources/2018/day01

; part 1
print sum input ; 470

; part 2
result: 0
list: make hash! [0]
duplicate: false

until [
  foreach freq input [
    result: result + freq
    either find list result [
      duplicate: true break
    ][
      append list result
    ]
  ]
  duplicate
]

print result ; 790
