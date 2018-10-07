Red []

; We have a group of students stay in the circle, everyone sing a song.
; Who is the person that sing the last word of the song will get out of the 
; circle immediately.
; We repeat this rule until there is only one student stay in the circle.

; Let imagine a group of students is array start with index 0:  
;  A[N]:   2  <= N  <= 50

; The song with S words:   2  <=  S  <= 100

; For example: 
;     A =  ['tony', 'eric', 'xuna'].  S = 'Hello World'
; We will have:
;    tony  -----> sing 'Hello'
;    eric   -----> sing  'World' : Get removed from the circle.
;    xuna -----> sing  'Hello'.
;    tony  -----> sing  'World' : Get removed from the circle.
; So the winner is xuna (A[2]).

; Requirement:  
;    find_singer(N, S) : return the index of the winner
; Example:    
;    find_singer(3,  2) => return 2
;    find_singer(3,  3) => return 1

do https://raw.githubusercontent.com/tentamen/red-utils/master/range.red

skip-circle: function [a n][
  l: length? head a
  i: index? a
  n: remainder n l
  either tail? skip a n [
    skip head a n - (l - i + 1)
  ][
    skip a n 
  ]
]

find-singer: function [n s][
  a: 0 .. (n - 1)
  while [(length? head a) > 1][
    a: skip-circle a s - 1
    remove a
  ]
  first head a
]

probe find-singer 10 3
probe find-singer 3 2
probe find-singer 3 3
probe find-singer 3 10

;find-singer 10 2
;probe skip-circle skip [1 2 4 5 7 8 10] 6 2
