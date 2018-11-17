Red []

differ?: function [a b] [
  count: 0
  repeat i length? a [
    if a/:i <> b/:i [count: count + 1]
    if count > 1    [break/return false]
  ]
  count = 1
]

next-nodes: function [node level coll] [
  nodes: copy []
  forall coll [
    if differ? node first coll [repend/only nodes [first coll level + 1]]
  ]
  nodes
]

word-ladder: function [start goal words] [
  path: copy []
  st: copy []
  result: copy []


]

a: ["cone" "bonk" "none" "dole" "tune" "node" "mode" "nope" "mole"]
probe next-nodes "code" 1 a
probe a
