Red []

zigzag: function [a] [
  m: n: 0

  forall a [
    if a/3 [
      either ((a/2 > a/1) and (a/2 > a/3))
          or ((a/2 < a/1) and (a/2 < a/3)) [
        n: n + 1  
      ] [
        m: max m n
        n: 0
      ]
    ]
  ]

  last-eq?: either a/3 [a/2 = a/3] [a/1 = a/2]
  head a
  first-eq?: a/1 = a/2
  either all [m = 0 n = 0 first-eq? last-eq?] [
    1
  ] [
    (max m n) + 2
  ]
]

print zigzag [4 4]
;= 1
print zigzag [4 5]
;= 2
print zigzag [4 5 6]
;= 2
print zigzag [4 5 3]
;= 3
print zigzag [9 8 8 5 3 5 3 2 8 6]
;= 4
print zigzag [2 1 4 4 1 4 4 1 2 0 1 0 0 3 1 3 4 1 3 4]
;= 6
