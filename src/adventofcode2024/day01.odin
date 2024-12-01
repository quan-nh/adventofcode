package aoc24

import "core:fmt"
import "core:os"
import "core:slice"
import "core:strconv"
import "core:strings"

main :: proc() {
	x, y: [dynamic]int
	defer {delete(x);delete(y)}

	data, ok := os.read_entire_file("resources/2024/day01-input.txt")
	if !ok do return
	defer delete(data)

	it := string(data)
	for line in strings.split_lines_iterator(&it) {
		s := strings.split(line, "   ")
		append(&x, strconv.atoi(s[0]))
		append(&y, strconv.atoi(s[1]))
	}

	part1_result := part1(x[:], y[:])
	fmt.println(part1_result) // 1765812

	part2_result := part2(x[:], y[:])
	fmt.println(part2_result) // 20520794
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
