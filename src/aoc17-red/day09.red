Red []

input: read %../../resources/2017/day09

st: []
score: 0
result: 0
count: 0
while [not tail? input] [
  ch: first input

  switch/default ch [
    #"!" [input: next input]
    #">" [take/last st]
  ] [
    either (last st) = #"<" [
      count: count + 1
    ] [
      switch ch [
        #"{" [
          append st ch
          score: score + 1
        ]

        #"}" [
          result: result + score
          take/last st
          score: score - 1
        ]

        #"<" [append st ch]
      ]
    ]
  ]

  input: next input
]

print result
; 14204
print count
; 6622
