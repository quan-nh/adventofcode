package aoc24

import "core:testing"

x := []int{3,4,2,1,3,3}
y := []int{4,3,5,3,9,3}

@(test)
part1_test :: proc(t: ^testing.T) {
	result := part1(x, y)
	testing.expect_value(t, part1(x, y), 11)
}

@(test)
part2_test :: proc(t: ^testing.T) {
	result := part2(x, y)
	testing.expect_value(t, part2(x, y), 31)
}

@(test)
calculate_similarity_score_test :: proc(t: ^testing.T) {
	result := calculate_similarity_score(x, y)
	testing.expect_value(t, result, 31)
}
