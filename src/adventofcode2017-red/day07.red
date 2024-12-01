Red []

text: read/lines %../../resources/2017/day7

programs: []
weights: #()
parents: #()

parser: context [
  digit:    charset [#"0" - #"9"]
  weight:   [some digit]
  alphabet: charset [#"a" - #"z"]
  program:  [some alphabet]

  rule: [
    copy p program
    space
    "(" copy w weight (put weights p to-integer w) ")"
    opt [
      space "->" space
      some [
        copy c program (append children c) (put parents c p)
        any ["," space]
      ]
      (repend programs [p children])
    ]
  ]

  parse-towers: func [txt] [
    children: copy []
    parse txt rule
  ]
]

foreach line text [
  parser/parse-towers line
]

; find root
root: first programs
forever [
  either parents/:root [
    root: parents/:root
  ] [break]
]

print root
; mwzaxaj

;--- Part Two ---
st: reduce [root]
visit: []
new-weights: copy weights

update-parent: function [
  "Add current weight to parent."
  p
] [
  parent: parents/:p
  put new-weights parent (new-weights/:parent + new-weights/:p)  
]

balance?: function [
  "Check if its children have same weights."
  p
] [
  either children: select programs p [
    w: copy []
    foreach child children [append w new-weights/:child]
    (length? unique w) = 1
  ] [true]
]

forever [
  if not p: last st [break]

  either find visit p [
    either balance? p [
      update-parent p
      take/last st
    ] [
      unbalance-node: p
      break
    ]    
  ] [
    append visit p

    either children: select programs p [
      foreach child children [append st child]
    ] [
      update-parent p
      take/last st
    ]
  ]
]

foreach child select programs unbalance-node [
  print [child new-weights/:child]
]
{
vrgxe 2166
shnqfh 2159
auzded 2159
hkhsc 2159
jwddn 2159
mcxki 2159
lhwyt 2159
}

print (select weights "vrgxe") - 2166 + 2159
; 1219
