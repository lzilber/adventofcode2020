package org.zebre.puzzle;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Puzzle3Test {

	final List<String> input = Arrays.asList(
			"..##.......",
			"#...#...#..",
			".#....#..#.",
			"..#.#...#.#",
			".#...##..#.",
			"..#.##.....",
			".#.#.#....#",
			".#........#",
			"#.##...#...",
			"#...##....#",
			".#..#...#.#");

	@Test
	public void testCountTrees() {
		Puzzle3 puzzle = new Puzzle3();
		assertEquals(7, puzzle.countTrees(input, 3, 1, false));
		
		int trees11 = puzzle.countTrees(input, 1, 1, false);
    	int trees31 = puzzle.countTrees(input, 3, 1, false);
    	int trees51 = puzzle.countTrees(input, 5, 1, false);
    	int trees71 = puzzle.countTrees(input, 7, 1, false);    	
    	int trees12 = puzzle.countTrees(input, 1, 2, true);
    	
    	assertEquals(336,(trees11 * trees31 * trees51 * trees71 * trees12));
	}
	
	
}
