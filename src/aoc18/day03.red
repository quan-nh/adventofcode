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

add-item: function [m k v] [
  either val: select m k [
    append val v
  ] [
    append m k
    repend/only m [v]
  ]
]

m: make hash! []
overlaps: make hash! []

foreach [id x y width height] claims [
  foreach i x .. (x + width - 1) [
    foreach j y .. (y + height - 1) [
      inch: as-pair i j
      add-item m inch id
      ids: select m inch
      foreach item ids [
        if id <> item [
          add-item overlaps item id
          add-item overlaps id item
        ]
      ]
    ]
  ]
]

; part 1
count: 0
foreach [_ ids] m [
  if (length? ids) > 1 [count: count + 1]
]

print count ; 124850

; part 2
foreach [id _ _ _ _] claims [
  if not select overlaps id [print id break] ; 1097
]
