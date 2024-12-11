This puzzle involves predicting the path of a guard in a 1518 laboratory setting. Here's the key points:

1. You're given a map with:
   - `^` representing the guard (showing their initial direction)
   - `#` representing obstacles (crates, desks, etc.)
   - `.` representing empty spaces

2. The guard follows two simple rules:
   - If there's an obstacle directly ahead, turn right 90 degrees
   - If the path is clear, move forward one step

3. The goal is to calculate how many unique positions the guard visits before leaving the mapped area.

4. The guard starts at a specific position and continues moving according to these rules until they eventually exit the map boundaries.

In the example provided, the guard visits 41 distinct positions before leaving the area. The challenge is to calculate this number for the actual puzzle input.

This is essentially a path-tracking problem where you need to simulate the guard's movement following the given rules and count unique visited positions.

--- Part Two ---
1. The goal is to find all possible positions where placing a single new obstacle (`O`) would cause the guard to get stuck in an infinite loop.

2. The new obstacle:
   - Cannot be placed at the guard's starting position
   - Must create a closed patrol loop for the guard
   - Should follow the same rules as Part One (guard turns right at obstacles, moves forward otherwise)

3. In the example provided, there are exactly 6 different valid positions where placing an obstacle would trap the guard in a loop.

4. The challenge is to count how many different positions could be used to create such a loop in the actual puzzle input.

The key difference from Part One is that instead of tracking the guard's exit path, we need to find all possible positions where adding an obstacle would create an infinite patrol loop.
