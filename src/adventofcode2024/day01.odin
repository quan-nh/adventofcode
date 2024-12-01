package aoc24

import "core:fmt"
import "core:os"
import "core:slice"
import "core:strconv"
import "core:strings"

main :: proc() {
	x, y: [dynamic]int
	defer {delete(x);delete(y)}

	data, ok := os.read_entire_file("resources/2024/day01.txt")
	if !ok do return
	defer delete(data)

	it := string(data)
	for line in strings.split_lines_iterator(&it) {
		s := strings.split(line, "   ")
		append(&x, strconv.atoi(s[0]))
		append(&y, strconv.atoi(s[1]))
	}

	slice.sort(x[:])
	slice.sort(y[:])

	result := 0
	for i in 0 ..< len(x) {
		result += abs(x[i] - y[i])
	}
	fmt.println(result) // 1765812
}
