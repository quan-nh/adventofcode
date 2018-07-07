Red []

registers: #()
highest-val: 0

; parse
digit: charset [#"0" - #"9"]
alpha: charset [#"a" - #"z"]
num: [0 1 "-" some digit]

update-registers: func [a b n m compare] [
  if not b-val: registers/:b [b-val: 0]

  m: to-integer m
  if do reduce [b-val compare m] [
    put registers a n
    if n > highest-val [highest-val: n]
  ]
]

rule: [
  copy a some alpha
  [" inc "   copy n num (n: to-integer n
                         if registers/:a [n: registers/:a + n])
   | " dec " copy n num (n: negate to-integer n
                         if registers/:a [n: registers/:a + n])]
  " if "
  copy b some alpha
  [" > "    copy m num (update-registers a b n m '>)
   | " < "  copy m num (update-registers a b n m '<)
   | " >= " copy m num (update-registers a b n m '>=)
   | " <= " copy m num (update-registers a b n m '<=)
   | " == " copy m num (update-registers a b n m '=)
   | " != " copy m num (update-registers a b n m '<>)]
]

text: read/lines %../../resources/2017/day8

foreach line text [
  parse line rule
]

max-val: 0

foreach [_ val] to-block registers [
  if val > max-val [max-val: val]
]

print max-val
; 5215

print highest-val
; 6419
