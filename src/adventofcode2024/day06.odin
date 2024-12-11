package aoc24_06

import "core:fmt"
import "core:os"
import "core:strings"

Direction :: enum {
	North,
	East,
	South,
	West,
}

Position :: struct {
	x, y: int,
}

is_equal :: proc(a, b: Position) -> bool {
	return a.x == b.x && a.y == b.y
}

contains :: proc(positions: []Position, pos: Position) -> bool {
	for p in positions {
		if is_equal(p, pos) {
			return true
		}
	}
	return false
}

turn_right :: proc(dir: Direction) -> Direction {
	switch dir {
	case .North:
		return .East
	case .East:
		return .South
	case .South:
		return .West
	case .West:
		return .North
	}
	return .North
}

move_forward :: proc(pos: Position, dir: Direction) -> Position {
	new_pos := pos
	switch dir {
	case .North:
		new_pos.y -= 1
	case .East:
		new_pos.x += 1
	case .South:
		new_pos.y += 1
	case .West:
		new_pos.x -= 1
	}
	return new_pos
}

is_obstacle :: proc(grid: [][]rune, pos: Position) -> bool {
	if pos.y < 0 || pos.y >= len(grid) || pos.x < 0 || pos.x >= len(grid[0]) {
		return true
	}
	return grid[pos.y][pos.x] == '#'
}

find_start :: proc(grid: [][]rune) -> (Position, Direction) {
	for y := 0; y < len(grid); y += 1 {
		for x := 0; x < len(grid[y]); x += 1 {
			if grid[y][x] == '^' {
				return Position{x, y}, .North
			}
		}
	}
	return Position{0, 0}, .North
}

solve :: proc(input: string) -> int {
	// Parse input into grid
	lines := strings.split(strings.trim_space(input), "\n")
	defer delete(lines)

	grid := make([][]rune, len(lines))
	defer delete(grid)

	for line, i in lines {
		grid[i] = make([]rune, len(line))
		for c, j in line {
			grid[i][j] = rune(c)
		}
	}

	// Find starting position and direction
	start_pos, dir := find_start(grid)

	// Track visited positions
	visited := make([dynamic]Position)
	defer delete(visited)
	append(&visited, start_pos)

	current_pos := start_pos
	current_dir := dir

	// Simulate movement until leaving the map
	for {
		next_pos := move_forward(current_pos, current_dir)

		// Check if we've left the map
		if next_pos.x < 0 ||
		   next_pos.x >= len(grid[0]) ||
		   next_pos.y < 0 ||
		   next_pos.y >= len(grid) {
			break
		}

		// Check for obstacle
		if is_obstacle(grid, next_pos) {
			current_dir = turn_right(current_dir)
			continue
		}

		// Move to next position
		current_pos = next_pos
		if !contains(visited[:], current_pos) {
			append(&visited, current_pos)
		}
	}

	return len(visited)
}

main :: proc() {
	data, ok := os.read_entire_file("resources/2024/day06-input.txt")
	if !ok {
		fmt.println("Failed to read input file")
		return
	}
	defer delete(data)

	input := string(data)
	result := solve(input)
	fmt.println("Result:", result)
}
