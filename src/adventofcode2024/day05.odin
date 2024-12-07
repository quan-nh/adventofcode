package aoc24_05

import "core:fmt"
import "core:os"
import "core:strconv"
import "core:strings"

Rule :: struct {
	before, after: int,
}

check_update :: proc(rules: []Rule, update: []int) -> bool {
	// For each pair of numbers in the update, check if they satisfy all rules
	for i := 0; i < len(update); i += 1 {
		for j := i + 1; j < len(update); j += 1 {
			a, b := update[i], update[j]
			// Check if there's a rule requiring b before a
			for rule in rules {
				if rule.before == b && rule.after == a {
					return false
				}
			}
		}
	}
	return true
}

get_middle_number :: proc(update: []int) -> int {
	return update[len(update) / 2]
}

parse_rules :: proc(input: string) -> ([]Rule, [][]int) {
	rules: [dynamic]Rule
	updates: [dynamic][]int

	lines := strings.split(input, "\n")
	parsing_rules := true

	for line in lines {
		if len(line) == 0 {
			parsing_rules = false
			continue
		}

		if parsing_rules {
			// Parse rule like "47|53"
			parts := strings.split(line, "|")
			if len(parts) == 2 {
				before, _ := strconv.parse_int(parts[0])
				after, _ := strconv.parse_int(parts[1])
				append(&rules, Rule{before, after})
			}

		} else {
			// Parse update like "75,47,61,53,29"
			nums: [dynamic]int
			num_strs := strings.split(line, ",")
			for num_str in num_strs {
				num, _ := strconv.parse_int(strings.trim_space(num_str))
				append(&nums, num)
			}
			append(&updates, nums[:])
		}
	}

	return rules[:], updates[:]
}

main :: proc() {
	input, ok := os.read_entire_file("resources/2024/day05-input.txt")
	if !ok {
		fmt.println("Error reading input file")
		return
	}
	defer delete(input)

	input_str := string(input)

	rules, updates := parse_rules(input_str)
	sum := 0

	for update in updates {
		if check_update(rules, update) {
			middle := get_middle_number(update)
			sum += middle
			fmt.printf("Valid update: %v, middle: %d\n", update, middle)
		}
	}

	fmt.printf("Sum of middle numbers: %d\n", sum)
}
