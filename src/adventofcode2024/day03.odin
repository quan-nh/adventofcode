package aoc24_day03

import "core:fmt"
import "core:os"
import "core:strconv"
import "core:strings"
import "core:testing"
import "core:text/match"
import "core:unicode"

main :: proc() {
	data, ok := os.read_entire_file("resources/2024/day03-input.txt")
	if !ok do return
	defer delete(data)

	input := string(data)

	fmt.println(part1(input)) // 179834255
	fmt.println(part2(input)) // 80570939

}

part1 :: proc(input: string) -> (sum: int) {
	pattern := `mul%((%d%d?%d?,%d%d?%d?)%)`

	sum = 0
	matcher := match.matcher_init(input, pattern)
	for s in match.matcher_gmatch(&matcher) {
		ss := strings.split(s, ",")
		defer delete(ss)
		sum += strconv.atoi(ss[0]) * strconv.atoi(ss[1])
	}
	return sum
}

@(test)
part1_test :: proc(t: ^testing.T) {
	example := `xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))`
	result := part1(example)
	testing.expect_value(t, result, 161)
}

// Helper to check for exact match of an instruction
check_instruction :: proc(text: string, pos: int) -> (kind: enum {
		None,
		Do,
		Dont,
		Mul,
	}, next_pos: int) {
	if pos >= len(text) {
		return .None, pos
	}

	// Check for do()
	if pos + 4 <= len(text) && text[pos:pos + 4] == "do()" {
		return .Do, pos + 4
	}

	// Check for don't()
	if pos + 7 <= len(text) && text[pos:pos + 7] == "don't()" {
		return .Dont, pos + 7
	}

	// Check for mul(X,Y)
	if pos + 5 <= len(text) && text[pos:pos + 4] == "mul(" {
		new_pos := pos
		new_pos += 4 // skip "mul("

		// Parse first number
		num1_start := new_pos
		for new_pos < len(text) && unicode.is_digit(rune(text[new_pos])) {
			new_pos += 1
		}
		if new_pos == num1_start || new_pos > num1_start + 3 { 	// check 1-3 digits
			return .None, pos + 1
		}

		// Check for comma
		if new_pos >= len(text) || text[new_pos] != ',' {
			return .None, pos + 1
		}
		new_pos += 1

		// Parse second number
		num2_start := new_pos
		for new_pos < len(text) && unicode.is_digit(rune(text[new_pos])) {
			new_pos += 1
		}
		if new_pos == num2_start || new_pos > num2_start + 3 { 	// check 1-3 digits
			return .None, pos + 1
		}

		// Check for closing parenthesis
		if new_pos >= len(text) && text[new_pos] != ')' {
			return .None, pos + 1
		}

		return .Mul, new_pos + 1
	}

	return .None, pos + 1
}

part2 :: proc(input: string) -> (sum: int) {
	sum = 0
	mul_enabled := true
	pos := 0

	for pos < len(input) {
		kind, new_pos := check_instruction(input, pos)

		switch kind {
		case .Do:
			mul_enabled = true
		case .Dont:
			mul_enabled = false
		case .Mul:
			if mul_enabled {
				// Extract and parse numbers
				mul_str := input[pos + 4:new_pos - 1]
				nums := strings.split(mul_str, ",")
				defer delete(nums)
				if len(nums) == 2 {
					sum += strconv.atoi(nums[0]) * strconv.atoi(nums[1])
				}
			}
		case .None:
		// Continue to next character
		}

		pos = new_pos
	}

	return sum
}

@(test)
part2_test :: proc(t: ^testing.T) {
	example := `xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))`
	result := part2(example)
	testing.expect_value(t, result, 48)
}
