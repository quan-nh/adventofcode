--- Day 4: Ceres Search ---
This puzzle is about finding all occurrences of the word "XMAS" in a letter grid (word search puzzle). The key points are:

1. The word "XMAS" can appear in multiple directions:
   - Horizontal (left to right or right to left)
   - Vertical (up to down or down to up)
   - Diagonal (in any direction)

2. Words can overlap with each other

3. The goal is to count the total number of times "XMAS" appears in the grid

The puzzle provides examples showing how "XMAS" might appear in different orientations and a sample 10x10 grid where "XMAS" appears 18 times. The challenge is to count all occurrences of "XMAS" in the actual puzzle input.

MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
In this word search, XMAS occurs a total of 18 times; here's the same word search again, but where letters not involved in any XMAS have been replaced with .:

....XXMAS.
.SAMXMS...
...S..A...
..A.A.MS.X
XMASAMX.MM
X.....XA.A
S.S.S.S.SS
.A.A.A.A.A
..M.M.M.MM
.X.X.XMASX
Take a look at the little Elf's word search. How many times does XMAS appear?

--- Part Two ---
This is part 2 of the puzzle, which changes the rules significantly:

1. Instead of looking for "XMAS" in any direction, we're now looking for an "X" shape made up of two "MAS" sequences
2. Each "MAS" in the X can be either forwards (MAS) or backwards (SAM)
3. The X pattern looks like this:
   ```
   M.S
   .A.
   M.S
   ```
   where the center 'A' is shared by both "MAS" sequences

4. The example shows that in the same grid from part 1, there are 9 X-MAS patterns when applying these new rules

.M.S......
..A..MSMS.
.M.S.MAA..
..A.ASMSM.
.M.S.M....
..........
S.S.S.S.S.
.A.A.A.A..
M.M.M.M.M.
..........

The challenge is to count how many times this X-shaped pattern appears in the grid, where each leg of the X must form a valid "MAS" (or "SAM") sequence.
