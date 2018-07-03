Red [
  Title: "Spiral Memory"
]

comment {
Each square on the grid is allocated in a spiral pattern starting at a location marked 1 and then counting up while spiraling outward. For example, the first few squares are allocated like this:

17  16  15  14  13
18   5   4   3  12
19   6   1   2  11
20   7   8   9  10
21  22  23---> ...

While this is very space-efficient (no squares are skipped), requested data must be carried back to square 1 (the location of the only access port for this memory system) by programs that can only move up, down, left, or right. They always take the shortest path: the Manhattan Distance between the location of the data and square 1.

For example:

- Data from square 1 is carried 0 steps, since it's at the access port.
- Data from square 12 is carried 3 steps, such as: down, left, left.
- Data from square 23 is carried only 2 steps: up twice.
- Data from square 1024 must be carried 31 steps.

How many steps are required to carry the data from the square identified in your puzzle input all the way to the access port?
}

input: 325489

north: [0 1]
east: [1 0]
south: [0 -1]
west: [-1 0]

direction: reduce [east north west south]

i: 1
n: 1
pos: [0 0]
forever [
  loop 2 [
    loop i [
      n: n + 1
      dir: first direction
      pos: reduce [pos/1 + dir/1  pos/2 + dir/2]

      if n = input [break]
    ]
    if n = input [break]

    direction: next direction
    if tail? direction [direction: head direction]
  ]
  if n = input [break]

  i: i + 1
]

print (absolute pos/1) + (absolute pos/2)
; 552
