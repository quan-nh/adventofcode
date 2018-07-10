Red [
  Url: https://www.interviewcake.com/question/ruby/recursive-str-permutations
]

permutations: function [str] [
  l: length? str
  either l = 1 [
    reduce [str]
  ] [
    sub-str: copy/part str l - 1
    c: last str

    result: copy []
    foreach s permutations sub-str [
      forall s [
        append result copy head insert s c
        remove s
      ]
      append result append s c
    ]

    result
  ]
]

probe permutations "cats"
