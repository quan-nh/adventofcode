Puzzle Objective:

Determine how many reports are "safe" based on specific criteria.

Safety Rules for a Report:
1. Levels must be either:
- Entirely increasing, OR
- Entirely decreasing
2. Adjacent levels must differ by:
- At least 1
- At most 3

Detailed Checking Process:
- Examine each adjacent pair of levels in a report
- Ensure the change is consistent (all increasing or all decreasing)
- Validate that each level change is between 1 and 3

Example Breakdown:
- `7 6 4 2 1`: Safe ✓
  - Consistently decreasing
  - Changes are 1 or 2
- `1 2 7 8 9`: Unsafe ✗
  - Inconsistent changes
  - 2 to 7 is a jump of 5
- `1 3 6 7 9`: Safe ✓
  - Consistently increasing
  - Changes are between 1 and 3

Solving Steps:
1. Parse input reports
2. Check each report against safety rules
3. Count number of safe reports

Input Format:
- Multiple lines
- Each line is a list of space-separated numbers representing levels
- Each line represents one report
