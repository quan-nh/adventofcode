Red []

input: read/lines %../../resources/2018/day03

digit: charset "0123456789"

claims: []

; parse
foreach line input [
  parse line [
    "#"
    copy id some digit (id: to integer! id)
    space "@" space 
    copy x some digit (x: to integer! x) 
    ","
    copy y some digit (y: to integer! y)
    ":" space
    copy w some digit (w: to integer! w)
    "x"
    copy h some digit (h: to integer! h)
    (repend claims [id x y w h])
  ]
]

; part 1
m: make hash! []

foreach [id x y w h] claims [
  i: x
  while [i < (x + w)] [
    j: y
    while [j < (y + h)] [
      square: as-pair i j
      either n: find m square [
        append n/2 id
      ] [
        append m square  
        repend/only m [id]
      ]
      j: j + 1
    ]  
    i: i + 1  
  ]
]

count: 0
foreach [square ids] m [
  if (length? ids) > 1 [count: count + 1]
]

print count ; 124850
