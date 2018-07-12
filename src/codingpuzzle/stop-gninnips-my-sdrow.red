Red [
  url: https://www.codewars.com/kata/stop-gninnips-my-sdrow
]

parser: context [
  letters: charset [#"a" - #"z" #"A" - #"Z"]
  word:    [some letters]

  spin-words: function [s] [
    parse s [
      some [pos1: word pos2: (l: (index? pos2) - (index? pos1)
                              if l >= 5 [
                                sub: copy/part pos1 l
                                change pos1 reverse sub
                              ])
            | space]
    ]
  ]
]

s: "Hey fellow warriors"
parser/spin-words s
print s = "Hey wollef sroirraw"

s: "This is a test"
parser/spin-words s
print s = "This is a test"

s: "This is another test"
parser/spin-words s
print s = "This is rehtona test"
