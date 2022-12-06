import strutils

let input = readFile("../../resources/2022/day02").splitLines

type
  RPS = enum
    rock = 1, paper = 2, scissors = 3

# part1
proc toEnum(shape: string): RPS =
  case shape:
    of "A", "X":
      result = RPS.rock
    of "B", "Y":
      result = RPS.paper
    else:
      result = RPS.scissors

proc outcomeScore(p1, p2: RPS): int =
  if p1 == p2:
    result = 3
  elif (p1 == RPS.rock and p2 == RPS.paper) or (p1 == RPS.paper and p2 == RPS.scissors) or (p1 == RPS.scissors and p2 == RPS.rock):
    result = 6
  else:
    result = 0

var score = 0

for line in input:
  let s = line.split(" ")
  score += outcomeScore(s[0].toEnum, s[1].toEnum) + ord(s[1].toEnum)

echo score # 13005


# part2
score = 0

for line in input:
  let s = line.split(" ")
  case s[1]:
    of "X": # lose
      case s[0].toEnum:
        of RPS.rock:
          score += ord(RPS.scissors)
        of RPS.paper:
          score += ord(RPS.rock)  
        else:
          score += ord(RPS.paper)
    of "Y": # draw
      score += ord(s[0].toEnum) + 3
    of "Z": # win
      score += 6
      case s[0].toEnum:
        of RPS.rock:
          score += ord(RPS.paper)
        of RPS.paper:
          score += ord(RPS.scissors)  
        else:
          score += ord(RPS.rock)
    else: discard

echo score # 11373    