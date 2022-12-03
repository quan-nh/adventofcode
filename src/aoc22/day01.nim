import strutils

var total, t1, t2, t3 = 0

for calo in readFile("../../resources/2022/day01").splitLines:
  if calo != "":
    total += calo.parseInt
  else:
    if total >= t1:
      (t1, t2, t3) = (total, t1, t2)
    elif total >= t2:
      (t2, t3) = (total, t2)
    elif total >= t3:
      t3 = total
    total = 0

echo t1            # 69795
echo t1 + t2 + t3  # 208437