Red [
  url: https://www.codewars.com/kata/53db96041f1a7d32dc0004d2  
]

row: function [board num] [
  copy/part skip board num - 1 * 9 9
]

column: function [board num] [
  extract (at board num) 9  
]

region: function [board num] [
  x: num - 1 // 3 + 1
  y: (to-integer num - 1 / 3) * 3 + 1
  out: copy []
  foreach z reduce [y (y + 1) (y + 2)] [
    append out copy/part skip row board z (x - 1 * 3) 3
  ]
  out
]

valid?: function [blk] [
  repeat x 9 [
	pos: blk
	if pos: find blk x [
	  if find next pos x [return false]
	] 
  ]
  true  
]

done-or-not: function [board] [
  repeat x 9 [
	if not all [
	  valid? row board x
	  valid? column board x
	  valid? region board x
	] [return "Try again!"] 
  ]
  "Finished!"  
]

print done-or-not [5 3 4 6 7 8 9 1 2 
                   6 7 2 1 9 5 3 4 8
                   1 9 8 3 4 2 5 6 7
                   8 5 9 7 6 1 4 2 3
                   4 2 6 8 5 3 7 9 1
                   7 1 3 9 2 4 8 5 6
                   9 6 1 5 3 7 2 8 4
                   2 8 7 4 1 9 6 3 5
                   3 4 5 2 8 6 1 7 9]

print done-or-not [5 3 4 6 7 8 9 1 2 
                   6 7 2 1 9 0 3 4 9
                   1 0 0 3 4 2 5 6 0
                   8 5 9 7 6 1 0 2 0
                   4 2 6 8 5 3 7 9 1
                   7 1 3 9 2 4 8 5 6
                   9 0 1 5 3 7 2 1 4
                   2 8 7 4 1 9 6 3 5
                   3 0 0 4 8 1 1 7 9]
