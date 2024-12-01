Red []

input: [2 8 8 5 4 2 3 1 5 5 1 2 15 13 5 14]

redistribute: function[in] [
  max-in: in
  max-val: first in
  forall in [
    if (first in) > max-val [
      max-val: first in
      max-in: in
    ]
  ]
  
  change max-in 0

  loop max-val [
    max-in: next max-in
    if tail? max-in [max-in: head max-in]

    change max-in (first max-in) + 1
  ]
]

states: reduce [input]

forever [
  state: copy last states
  redistribute state

  either find/only states state [
    idx: index? find/only states state
    break
  ] [
    append/only states state
  ]
]

print length? states
; 3156
print (length? states) - idx + 1
; 1610
