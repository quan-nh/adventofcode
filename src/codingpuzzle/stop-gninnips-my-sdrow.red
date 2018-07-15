Red [
  url: https://www.codewars.com/kata/stop-gninnips-my-sdrow
]

spin-words: function [s] [
  letters: charset [#"a" - #"z" #"A" - #"Z"]
  word:    [some letters]

  parse s [
    some [copy w p: word (if (length? w) >= 5 [change p reverse w])
          [space | end]]
  ]
]

s: "Hey fellow warriors"
spin-words s
print s = "Hey wollef sroirraw"

s: "This is a test"
spin-words s
print s = "This is a test"

s: "This is another test"
spin-words s
print s = "This is rehtona test"
