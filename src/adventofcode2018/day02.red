Red []

input: read/lines %../../resources/2018/day02

; part 1
frequency: function [s] [
  result: copy []
  foreach ch s [
    either freq: find result ch [
      freq/2: freq/2 + 1
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

print twos * threes ; 3952

; part 2
common-letters: function [s1 s2] [
  result: copy ""
  no-diff: 0
  repeat i length? s1 [
    either s1/:i = s2/:i [
      append result s1/:i
    ] [
      no-diff: no-diff + 1
    ]
    if no-diff > 1 [result: none break]
  ]
  either zero? no-diff [none] [result]
]

l: length? input
repeat i l [
  j: 1 done?: no
  until [
    if result: common-letters input/:i input/:j [
      print result
      done?: yes
      break
    ]
    j: j + 1
    i + j = l
  ]
  if done? [break]
]
;vtnikorkulbfejvyznqgdxpaw
