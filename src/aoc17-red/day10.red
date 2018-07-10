Red [
  Url: https://adventofcode.com/2017/day/10
]

input-length: 256

input: []
repeat i input-length [append input i - 1]

lengths: split read %../../resources/2017/day10 ","
forall lengths [change lengths to-integer lengths/1]

skip-size: 0

knot-hash: func [lengths] [
  foreach len lengths [
    idx: index? input

    sublist: copy []
    loop len [
      append sublist input/1
      if tail? input: next input [input: head input]
    ]
    
    reverse sublist

    input: skip head input idx - 1

    forall sublist [
      change input sublist/1
      if tail? input: next input [input: head input]
    ]
    
    skip-size: skip-size // input-length
    loop skip-size [
      if tail? input: next input [input: head input]
    ]

    skip-size: skip-size + 1
  ]
]

knot-hash lengths

input: head input

print input/1 * input/2
; 6909

;--- Part Two ---
clear input
clear lengths
skip-size: 0

repeat i input-length [append input i - 1]

s: read %../../resources/2017/day10
forall s [append lengths to-integer s/1]
append lengths [17 31 73 47 23]

loop 64 [
  knot-hash lengths
]

input: head input

dense-hash: []
loop 16 [
  sub: copy/part input 16
  a: 0
  forall sub [a: a xor sub/1]
  append dense-hash a

  input: skip input 16
]

forall dense-hash [
  change dense-hash lowercase form to-hex/size dense-hash/1 2
]

print rejoin dense-hash
; 9d5f4561367d379cfbf04f8c471c0095
