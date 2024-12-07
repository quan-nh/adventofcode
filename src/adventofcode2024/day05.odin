package aoc24_05

import "core:fmt"
import "core:os"
import "core:strconv"
import "core:strings"

Rule :: struct {
	before, after: int,
}

Graph :: map[int][dynamic]int

// Build adjacency list representation of rules
build_graph :: proc(rules: []Rule, pages: []int) -> Graph {
	graph := make(Graph)

	// Initialize empty lists for all pages
	for page in pages {
		graph[page] = make([dynamic]int)
	}

	// Add edges from rules that apply to these pages
	for rule in rules {
		if rule.before in graph && rule.after in graph {
			append(&graph[rule.before], rule.after)
		}
	}

	return graph
}

// Topological sort using DFS
topological_sort :: proc(graph: Graph, pages: []int) -> []int {
	visited := make(map[int]bool)
	temp_mark := make(map[int]bool)
	result: [dynamic]int
	temp_result: [dynamic]int // Store results temporarily
	defer delete(visited)
	defer delete(temp_mark)
	defer delete(temp_result)

	visit :: proc(
		node: int,
		graph: Graph,
		visited, temp_mark: ^map[int]bool,
		temp_result: ^[dynamic]int,
	) -> bool {
		if node in temp_mark^ do return false // Cycle detected
		if node in visited^ do return true

		temp_mark^[node] = true

		if node in graph {
			for next in graph[node] {
				if !visit(next, graph, visited, temp_mark, temp_result) {
					return false
				}
			}
		}

		delete_key(temp_mark, node)
		visited^[node] = true
		append(temp_result, node) // Add to temporary result
		return true
	}

	// Try to visit each page
	for page in pages {
		if !(page in visited) {
			if !visit(page, graph, &visited, &temp_mark, &temp_result) {
				return nil
			}
		}
	}

	// Reverse the temporary result to get the correct order
	for i := len(temp_result) - 1; i >= 0; i -= 1 {
		append(&result, temp_result[i])
	}

	return result[:]
}

check_update :: proc(rules: []Rule, update: []int) -> bool {
	for i := 0; i < len(update); i += 1 {
		for j := i + 1; j < len(update); j += 1 {
			a, b := update[i], update[j]
			for rule in rules {
				if rule.before == b && rule.after == a {
					return false
				}
			}
		}
	}
	return true
}

get_middle_number :: proc(update: []int) -> int {
	return update[len(update) / 2]
}

parse_rules :: proc(input: string) -> ([]Rule, [][]int) {
	rules: [dynamic]Rule
	updates: [dynamic][]int

	lines := strings.split(input, "\n")
	parsing_rules := true

	for line in lines {
		if len(line) == 0 {
			parsing_rules = false
			continue
		}

		if parsing_rules {
			parts := strings.split(line, "|")
			if len(parts) == 2 {
				before, _ := strconv.parse_int(parts[0])
				after, _ := strconv.parse_int(parts[1])
				append(&rules, Rule{before, after})
			}
		} else {
			nums: [dynamic]int
			num_strs := strings.split(line, ",")
			for num_str in num_strs {
				num, _ := strconv.parse_int(strings.trim_space(num_str))
				append(&nums, num)
			}
			append(&updates, nums[:])
		}
	}

	return rules[:], updates[:]
}

solve_puzzle :: proc(input: string) -> (part1, part2: int) {
	rules, updates := parse_rules(input)
	part1, part2 = 0, 0

	fmt.println("\nPart 1: Checking valid updates...")
	for update in updates {
		if check_update(rules, update) {
			middle := get_middle_number(update)
			part1 += middle
			fmt.printf("Valid update: %v (middle: %d)\n", update, middle)
		}
	}

	fmt.println("\nPart 2: Fixing invalid updates...")
	for update in updates {
		if !check_update(rules, update) {
			graph := build_graph(rules, update)
			defer {
				for _, value in graph do delete(value)
				delete(graph)
			}

			correct_order := topological_sort(graph, update)
			if correct_order != nil {
				middle := get_middle_number(correct_order)
				part2 += middle
				fmt.printf("Invalid update: %v\n", update)
				fmt.printf("Corrected to: %v (middle: %d)\n", correct_order, middle)
			}
		}
	}

	return
}

main :: proc() {
	input, ok := os.read_entire_file("resources/2024/day05-input.txt")
	if !ok {
		fmt.println("Error reading input file")
		return
	}
	defer delete(input)

	input_str := string(input)

	part1, part2 := solve_puzzle(input_str)

	fmt.printf("\nResults:\n")
	fmt.printf("Part 1 - Sum of middle numbers from valid updates: %d\n", part1)
	fmt.printf("Part 2 - Sum of middle numbers from corrected invalid updates: %d\n", part2)
}
