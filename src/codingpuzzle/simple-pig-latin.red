Red []

letters: charset [#"a" - #"z" #"A" - #"Z"]

rule: [
  some [
    copy a p1: letters
    copy b any letters p2:
    (remove/part p1 (length? b) + 1
     insert p1 reduce [b a "ay"]
     p2: skip p2 2)
     :p2
    [space | end]
  ]
]

s: "O tempora o mores !" 
parse s rule
print s
; Oay emporatay oay oresmay !
