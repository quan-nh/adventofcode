Red [
  Problem: {You are helping out with your school's "Queen of School" contest.   You have the list of votes, where votes[i] is the name of the girl the ith person voted for. Your task is to find out who the winner is. If there are several girls who got the maximal number of votes, choose the winner by sorting the list of potential winners lexicographically and then choosing the last one.

Example

For votes = ["Laura", "Emily", "Louise", "Natasha", "Emily", "Lilly", "Louise", "Laura", "Mary", "Mary"], the output should be queenOfSchool(votes) = "Mary".
Emily, Louise, Laura, and Mary have 2 votes each, which is the maximal number of votes, but Mary is the last one in the sorted list.}
]

queen-of-school: function [votes [block!]] [
  count-votes: copy []
  max-votes: 1
  foreach girl votes [
    either c: find count-votes girl [
      c/2: c/2 + 1
      if c/2 > max-votes [max-votes: c/2]
    ] [
      repend count-votes [girl 1]
    ]
  ]

  queens: copy []
  foreach [girl n] count-votes [
    if n = max-votes [append queens girl]
  ]

  last sort queens
]

print "Mary" = queen-of-school ["Laura" "Emily" "Louise" "Natasha" "Emily" "Lilly" "Louise" "Laura" "Mary" "Mary"]
print "A" = queen-of-school ["A"]
print "Mya" = queen-of-school ["Jaquelinea" "Alice" "Mya" "Molly" "Mariah" "Martha" "Lucia"]
print "Helen" = queen-of-school ["Helen" "Evelyn" "Mariah" "Aimee" "Helen" "Evelyn" "Eloise" "Lucia" "Jaquelinea" "Helen"]
print "Makenna" = queen-of-school ["Layla" "Helen" "Ivy" "Hallie" "Evelyn" "Aimee" "Kasey" "Molly" "Hannah" "Lucia" "Eloise" "Alice" "Martha" "Alice" "Molly" "Makenna" "Zoe" "Martha" "Kynlee" "Hannah" "Zoe" "Hallie" "Tegan" "Helen" "Ella" "Helen" "Kasey" "Ivy" "Layla" "Myla" "Ivy" "Mariah" "Hannah" "Tamia" "Molly" "Tegan" "Kynlee" "Mya" "Eloise" "Ivy" "Aimee" "Layla" "Bella" "Ella" "Makenna" "Mariah" "Hannah" "Makenna" "Makenna" "Mya"]
