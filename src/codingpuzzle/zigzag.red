Red []

zigzag: function [a] [
  l: length? a  
  case [
    (l = 2) and (a/1 = a/2)  1
    (l = 2) and (a/1 <> a/2) 2
    true [
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
      (max m n) + 2
    ]
  ]
]

print zigzag [9 8 8 5 3 5 3 2 8 6]
;= 4
print zigzag [4 4]
;= 1
print zigzag [2 1 4 4 1 4 4 1 2 0 1 0 0 3 1 3 4 1 3 4]
;= 6
