package aoc24_day03

import "core:fmt"
import "core:os"
import "core:strconv"
import "core:strings"
import "core:testing"
import "core:text/match"

main :: proc() {
	data, ok := os.read_entire_file("resources/2024/day03-input.txt")
	if !ok do return
	defer delete(data)

	input := string(data)

	fmt.println(part1(input)) // 179834255
}

part1 :: proc(input: string) -> (sum: int) {
	pattern := `mul%((%d%d?%d?,%d%d?%d?)%)`

	sum = 0
	matcher := match.matcher_init(input, pattern)
	for s in match.matcher_gmatch(&matcher) {
		ss := strings.split(s, ",")
		sum += strconv.atoi(ss[0]) * strconv.atoi(ss[1])
	}
	return
}

@(test)
part1_test :: proc(t: ^testing.T) {
	example := `xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))`
	result := part1(example)
	fmt.printf("Test Result: %d\n", result)
	testing.expect_value(t, result, 161)
}
