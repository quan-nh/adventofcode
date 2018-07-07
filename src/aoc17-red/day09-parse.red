Red [
  Url: https://adventofcode.com/2017/day/9
]

score: 0
level: 0
count: 0

; parse
group:   ["{" (level: level + 1)
          any [group | "," | garbage]
          "}" (score: score + level
               level: level - 1)]
garbage: ["<"
          any [copy chars some char (count: count + length? chars)
               | ignored]
          ">"]
char:    charset [not "!>"]
ignored: ["!" skip]

parse read %../../resources/2017/day09 group

print score
; 14204

print count
; 6622
