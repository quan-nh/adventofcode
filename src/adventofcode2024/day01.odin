package aoc24

import "core:fmt"
import "core:os"
import "core:slice"
import "core:strconv"
import "core:strings"
import "core:testing"

main :: proc() {
	data, ok := os.read_entire_file("resources/2024/day01-input.txt")
	if !ok do return
	defer delete(data)

	lines := strings.split_lines(strings.trim_right_space(string(data)))

	x := make([]int, len(lines))
	y := make([]int, len(lines))
	defer {delete(x);delete(y)}

	for line, idx in lines {
		s := strings.split(line, "   ")
		x[idx] = strconv.atoi(s[0])
		y[idx] = strconv.atoi(s[1])
	}

	part1_result := part1(x, y)
	fmt.println(part1_result) // 1765812

	part2_result := part2(x, y)
	fmt.println(part2_result) // 20520794

	fmt.println(calculate_similarity_score(x, y)) // 20520794
}

part1 :: proc(x, y: []int) -> int {
	slice.sort(x)
	slice.sort(y)

	result := 0
	for i in 0 ..< len(x) {
		result += abs(x[i] - y[i])
	}

	return result
}

@(test)
part1_test :: proc(t: ^testing.T) {
	x := []int{3, 4, 2, 1, 3, 3}
	y := []int{4, 3, 5, 3, 9, 3}
	result := part1(x, y)
	testing.expect_value(t, result, 11)
}

part2 :: proc(x, y: []int) -> int {
	m: map[int]int
	defer delete(m)

	result := 0
	for i in x {
		if freq, ok := m[i]; ok {
			result += i * freq
		} else {
			freq := 0
			for j in y {
				if i == j {
					freq += 1
				}
			}
			result += i * freq
			m[i] = freq
		}
	}

	return result
}

@(test)
part2_test :: proc(t: ^testing.T) {
	x := []int{3, 4, 2, 1, 3, 3}
	y := []int{4, 3, 5, 3, 9, 3}
	result := part2(x, y)
	testing.expect_value(t, result, 31)
}

// solution by LLM part 2
calculate_similarity_score :: proc(left_list, right_list: []int) -> int {
	// Count frequencies of numbers in the right list
	frequencies := make(map[int]int)
	defer delete(frequencies)

	for num in right_list {
		frequencies[num] += 1
	}

	// Calculate similarity score
	total_score := 0
	for num in left_list {
		// Multiply number by its frequency (0 if not found)
		frequency := frequencies[num] or_else 0
		total_score += num * frequency
	}

	return total_score
}

@(test)
calculate_similarity_score_test :: proc(t: ^testing.T) {
	x := []int{3, 4, 2, 1, 3, 3}
	y := []int{4, 3, 5, 3, 9, 3}
	result := calculate_similarity_score(x, y)
	testing.expect_value(t, result, 31)
}
