Red []

do https://raw.githubusercontent.com/tentamen/red-utils/master/range.red

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
    copy width some digit (width: to integer! width)
    "x"
    copy height some digit (height: to integer! height)
    (repend claims [id x y width height])
  ]
]

; part 1
m: make hash! []

foreach [id x y width height] claims [
  foreach i x .. (x + width - 1) [
    foreach j y .. (y + height - 1) [
      either n: find m square: as-pair i j [
        append n/2 id
      ] [
        append m square  
        repend/only m [id]
      ]
    ]
  ]
]

count: 0
foreach [square ids] m [
  if (length? ids) > 1 [count: count + 1]
]

print count ; 124850
