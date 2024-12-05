package aoc24_day4

import "core:fmt"
import "core:os"
import "core:strings"

Pos :: struct {
	x, y: int,
}

Direction :: struct {
	dx, dy: int,
}

DIRECTIONS := []Direction {
	{0, 1}, // right
	{1, 0}, // down
	{1, 1}, // diagonal down-right
	{-1, 1}, // diagonal down-left
	{0, -1}, // left
	{-1, 0}, // up
	{-1, -1}, // diagonal up-left
	{1, -1}, // diagonal up-right
}

// X-MAS pattern directions for each diagonal
TOP_LEFT_TO_BOTTOM_RIGHT := Direction{1, 1}
TOP_RIGHT_TO_BOTTOM_LEFT := Direction{-1, 1}

is_valid_pos :: proc(grid: []string, pos: Pos) -> bool {
	if pos.y < 0 || pos.y >= len(grid) do return false
	line := grid[pos.y]
	return pos.x >= 0 && pos.x < len(line)
}

check_xmas :: proc(grid: []string, start: Pos, dir: Direction) -> bool {
	target := "XMAS"
	pos := start

	for i := 0; i < len(target); i += 1 {
		if !is_valid_pos(grid, pos) do return false
		line := grid[pos.y]
		if line[pos.x] != target[i] do return false
		pos.x += dir.dx
		pos.y += dir.dy
	}
	return true
}

part1 :: proc(input: string) -> int {
	lines := strings.split_lines(input)
	if len(lines) == 0 do return 0

	count := 0
	height := len(lines)
	width := len(lines[0])

	for y := 0; y < height; y += 1 {
		for x := 0; x < width; x += 1 {
			start := Pos{x, y}

			// Check all directions from current position
			for dir in DIRECTIONS {
				if check_xmas(lines, start, dir) {
					count += 1
				}
			}
		}
	}

	return count
}

// part2
// Check diagonal MAS starting from a position
check_diagonal_mas :: proc(grid: []string, start: Pos, right_diagonal: bool) -> bool {
	chars := "MAS"
	pos := start
	dx := right_diagonal ? 1 : -1 // Direction depends on which diagonal we're checking

	for i := 0; i < len(chars); i += 1 {
		if !is_valid_pos(grid, pos) do return false
		if grid[pos.y][pos.x] != chars[i] do return false
		pos.x += dx
		pos.y += 1
	}
	return true
}

// Check diagonal SAM starting from a position
check_diagonal_sam :: proc(grid: []string, start: Pos, right_diagonal: bool) -> bool {
	chars := "SAM"
	pos := start
	dx := right_diagonal ? 1 : -1

	for i := 0; i < len(chars); i += 1 {
		if !is_valid_pos(grid, pos) do return false
		if grid[pos.y][pos.x] != chars[i] do return false
		pos.x += dx
		pos.y += 1
	}
	return true
}

part2 :: proc(input: string) -> int {
	lines := strings.split_lines(input)
	if len(lines) == 0 do return 0

	// Remove any empty lines
	valid_lines := make([dynamic]string)
	defer delete(valid_lines)

	for line in lines {
		if len(line) > 0 {
			append(&valid_lines, line)
		}
	}

	if len(valid_lines) == 0 do return 0

	count := 0
	height := len(valid_lines)
	width := len(valid_lines[0])

	// We need at least 3 rows and 3 columns to form an X-MAS
	if height < 3 || width < 3 do return 0

	// For each possible center position
	for y := 1; y < height - 1; y += 1 {
		for x := 1; x < width - 1; x += 1 {
			// Check if center is 'A'
			if valid_lines[y][x] != 'A' do continue

			// Check left and right diagonals
			// For left diagonal: start position is (x-1, y-1)
			// For right diagonal: start position is (x+1, y-1)

			left_start := Pos{x - 1, y - 1}
			right_start := Pos{x + 1, y - 1}

			// Check all valid combinations (MAS/MAS, MAS/SAM, SAM/MAS, SAM/SAM)
			if (check_diagonal_mas(valid_lines[:], left_start, true) &&
				   check_diagonal_mas(valid_lines[:], right_start, false)) ||
			   (check_diagonal_mas(valid_lines[:], left_start, true) &&
					   check_diagonal_sam(valid_lines[:], right_start, false)) ||
			   (check_diagonal_sam(valid_lines[:], left_start, true) &&
					   check_diagonal_mas(valid_lines[:], right_start, false)) ||
			   (check_diagonal_sam(valid_lines[:], left_start, true) &&
					   check_diagonal_sam(valid_lines[:], right_start, false)) {
				count += 1
			}
		}
	}

	return count
}

main :: proc() {
	input, ok := os.read_entire_file("resources/2024/day04-input.txt")
	if !ok {
		fmt.println("Error reading input file")
		return
	}
	defer delete(input)

	input_str := string(input)

	part1_result := part1(input_str)
	fmt.println("Number of XMAS occurrences:", part1_result) // 2458

	part2_result := part2(input_str)
	fmt.println("Number of X-MAS patterns:", part2_result) // 2137
}
