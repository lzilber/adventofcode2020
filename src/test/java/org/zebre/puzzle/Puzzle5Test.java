package org.zebre.puzzle;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Puzzle5Test {
	
	final List<String> input = Arrays.asList(
			"BFFFBBFRRR",
			"FFFBBBFRRR",
			"BBFFBBFRLL"
			);
	
	private Puzzle5 puzzle;
	
	@Before
	public void setup() {
		puzzle = new Puzzle5();
	}

	@Test
	public void testMaxSeatId() {
		assertEquals(820, puzzle.maxSeatId(input));
	}

	/*
BFFFBBFRRR: row 70, column 7, seat ID 567.
FFFBBBFRRR: row 14, column 7, seat ID 119.
BBFFBBFRLL: row 102, column 4, seat ID 820
	 */
	
	@Test
	public void testComputeSeatId() {
		assertEquals(567, puzzle.computeSeatId(70, 7));
	}

	@Test
	public void testGetRow() {
		assertEquals(44, puzzle.getRow("FBFBBFF"));
		assertEquals(14, puzzle.getRow("FFFBBBF"));
		assertEquals(102, puzzle.getRow("BBFFBBF")); // 1100110
	}

	@Test
	public void testGetCol() {
		assertEquals(5, puzzle.getCol("RLR"));
	}

}
