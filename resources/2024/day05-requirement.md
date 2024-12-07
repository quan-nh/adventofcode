--- Day 5: Print Queue ---

This puzzle is about validating print orders for safety manual updates. Here's the breakdown:

Key Elements:
1. **Input Format**
   - Rules section: Format "X|Y" meaning page X must come before page Y
   - Updates section: Lists of page numbers to be printed

2. **Task Requirements**
   - Check if each update sequence follows all applicable rules
   - Find updates that are in correct order
   - Get the middle number from each correct update
   - Sum all these middle numbers

3. **Example Case**
   - Given 21 rules and 6 update sequences
   - Only 3 updates were valid: [75,47,61,53,29], [97,61,53,29,13], [75,29,13]
   - Middle numbers: 61, 53, 29
   - Sum = 143

4. **Key Rules**
   - Pages must be printed in order according to rules
   - Only rules involving pages in the current update apply
   - Pages don't need to be immediately adjacent

The goal is to find the sum of middle numbers from all correctly ordered updates in a larger dataset.
