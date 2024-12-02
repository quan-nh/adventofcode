package aoc24

import "core:fmt"
import "core:os"
import "core:strconv"
import "core:strings"
import "core:testing"

main :: proc() {
	data, ok := os.read_entire_file("resources/2024/day02-input.txt")
	if !ok do return
	defer delete(data)

	lines := strings.split_lines(strings.trim_right_space(string(data)))

	// Count safe reports
	safe_report_count := 0
	for line in lines {
		if len(line) == 0 do continue

		// Parse and check report
		report := parse_report(line)
		defer delete(report)

		if is_safe_report(report) {
			safe_report_count += 1
		}
	}
	fmt.println(safe_report_count)
}

parse_report :: proc(line: string) -> []int {
	// Split line by whitespace
	number_strs := strings.split(line, " ")
	defer delete(number_strs)

	// Convert to integers
	report := make([]int, len(number_strs))
	for num_str, i in number_strs {
		report[i] = strconv.atoi(num_str)
	}

	return report
}

is_safe_report :: proc(report: []int) -> bool {
	// Not enough levels to check
	if len(report) <= 1 {
		return false
	}

	// Determine initial direction
	is_increasing := report[1] > report[0]
	is_decreasing := report[1] < report[0]

	// Check each adjacent pair of levels
	for i in 1 ..< len(report) {
		diff := abs(report[i] - report[i - 1])

		// Check if difference is between 1 and 3
		if diff < 1 || diff > 3 {
			return false
		}

		// Check if direction is consistent
		if is_increasing && report[i] < report[i - 1] {
			return false
		}
		if is_decreasing && report[i] > report[i - 1] {
			return false
		}
	}

	return true
}

@(test)
is_safe_report_test :: proc(t: ^testing.T) {
	testing.expect(t, is_safe_report([]int{7, 6, 4, 2, 1}))
	testing.expect(t, !is_safe_report([]int{1, 2, 7, 8, 9}))
	testing.expect(t, !is_safe_report([]int{9, 7, 6, 2, 1}))
	testing.expect(t, !is_safe_report([]int{1, 3, 2, 4, 5}))
	testing.expect(t, !is_safe_report([]int{8, 6, 4, 4, 1}))
	testing.expect(t, is_safe_report([]int{1, 3, 6, 7, 9}))
}
