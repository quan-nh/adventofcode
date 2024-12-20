1. You're given a corrupted text input that contains multiplication instructions.

2. Valid multiplication instructions:
   - Must be in the format `mul(X,Y)` exactly
   - X and Y must be 1-3 digit numbers
   - No spaces or other characters allowed in the format

3. Invalid examples to ignore:
   - `mul(4*`
   - `mul(6,9!`
   - `?(12,34)`
   - `mul ( 2 , 4 )`
   - Any other format that doesn't match exactly

4. Task:
   - Find all valid `mul(X,Y)` instructions in the input
   - Calculate each multiplication result
   - Sum up all the results

5. Example:
   In: `xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)do()?mul(8,5))`
   Only valid ones: `mul(2,4)`, `mul(5,5)`, `mul(11,8)`, `mul(8,5)`
   Sum: `(2*4) + (5*5) + (11*8) + (8*5) = 161`

The goal is to find the sum of all valid multiplication results in the corrupted input.

--- Part Two ---
1. Two new instructions are added:
   - `do()`: Enables multiplication instructions
   - `don't()`: Disables multiplication instructions

2. Key rules:
   - At program start, multiplications are enabled by default
   - Only the most recent `do()`/`don't()` instruction affects the state
   - Only enabled `mul()` instructions should be calculated

3. Example:
   ```
   xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)do()?mul(8,5))
   ```
   - `mul(2,4)` is counted (enabled by default)
   - `don't()` disables multiplications
   - `mul(5,5)` and `mul(11,8)` are ignored (disabled)
   - `do()` re-enables multiplications
   - `mul(8,5)` is counted
   - Final sum: `(2*4) + (8*5) = 48`

The task is to modify our previous solution to track the enabled/disabled state and only sum up the results of enabled multiplications.
