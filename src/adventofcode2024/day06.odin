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

State :: struct {
	pos: Position,
	dir: Direction,
}

VisitedState :: struct {
	pos:   Position,
	dir:   Direction,
	steps: int,
}

is_equal :: proc(a, b: Position) -> bool {
	return a.x == b.x && a.y == b.y
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

is_blocked :: proc(grid: [][]rune, test_obstacle: Position, pos: Position) -> bool {
	if pos.y < 0 || pos.y >= len(grid) || pos.x < 0 || pos.x >= len(grid[0]) {
		return true
	}
	if is_equal(pos, test_obstacle) {
		return true
	}
	return grid[pos.y][pos.x] == '#'
}

// Part 1: Count unique positions before leaving map
solve_part1 :: proc(grid: [][]rune, start: State) -> int {
	visited := make([dynamic]Position)
	defer delete(visited)

	current := start
	append(&visited, current.pos)

	for {
		next_pos := move_forward(current.pos, current.dir)

		// Check if we've left the map
		if next_pos.y < 0 ||
		   next_pos.y >= len(grid) ||
		   next_pos.x < 0 ||
		   next_pos.x >= len(grid[0]) {
			break
		}

		// Check for obstacle
		if is_blocked(grid, Position{-1, -1}, next_pos) {
			current.dir = turn_right(current.dir)
			continue
		}

		// Move and track position
		current.pos = next_pos
		if !contains(visited[:], current.pos) {
			append(&visited, current.pos)
		}
	}

	return len(visited)
}

// Part 2: Find positions that create loops
check_for_loop :: proc(grid: [][]rune, start: State, test_obstacle: Position) -> bool {
	visited_states := make(map[string]int)
	defer delete(visited_states)

	current := start
	steps := 0
	MAX_STEPS :: 10000 // Increased to ensure we catch longer loops

	for steps < MAX_STEPS {
		// Create a unique key for the current state
		state_key := fmt.tprintf("%d,%d,%d", current.pos.x, current.pos.y, current.dir)

		// Check if we've seen this state before
		if prev_step, exists := visited_states[state_key]; exists {
			loop_length := steps - prev_step
			if loop_length > 1 { 	// Ensure it's a real loop, not just oscillating
				return true
			}
		}

		visited_states[state_key] = steps
		next_pos := move_forward(current.pos, current.dir)

		// Check if we've left the map
		if next_pos.y < 0 ||
		   next_pos.y >= len(grid) ||
		   next_pos.x < 0 ||
		   next_pos.x >= len(grid[0]) {
			return false // Not a loop if we exit the map
		}

		// Check for obstacles (including test obstacle)
		if is_blocked(grid, test_obstacle, next_pos) {
			current.dir = turn_right(current.dir)
			continue
		}

		current.pos = next_pos
		steps += 1
	}

	return false // No loop found within MAX_STEPS
}

solve_part2 :: proc(grid: [][]rune, start: State) -> int {
	loop_count := 0
	test_positions := make([dynamic]Position)
	defer delete(test_positions)

	// First collect all possible test positions
	for y := 0; y < len(grid); y += 1 {
		for x := 0; x < len(grid[0]); x += 1 {
			if grid[y][x] == '.' {
				append(&test_positions, Position{x, y})
			}
		}
	}

	// Test each position
	for test_pos in test_positions {
		if is_equal(test_pos, start.pos) {
			continue
		}

		if check_for_loop(grid, start, test_pos) {
			loop_count += 1
		}
	}

	return loop_count
}

contains :: proc(positions: []Position, pos: Position) -> bool {
	for p in positions {
		if is_equal(p, pos) {
			return true
		}
	}
	return false
}

find_start :: proc(grid: [][]rune) -> State {
	for y := 0; y < len(grid); y += 1 {
		for x := 0; x < len(grid[0]); x += 1 {
			if grid[y][x] == '^' {
				return State{Position{x, y}, .North}
			}
		}
	}
	return State{Position{0, 0}, .North}
}

solve :: proc(input: string) -> (part1: int, part2: int) {
	// Parse input
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

	start := find_start(grid)

	// Solve both parts
	part1 = solve_part1(grid, start)
	part2 = solve_part2(grid, start)

	return
}

main :: proc() {
	data, ok := os.read_entire_file("resources/2024/day06-input.txt")
	if !ok {
		fmt.println("Failed to read input file")
		return
	}
	defer delete(data)

	input := string(data)
	part1, part2 := solve(input)
	fmt.printf("Part 1: %d unique positions visited\n", part1)
	fmt.printf("Part 2: %d possible obstacle positions\n", part2)
}
