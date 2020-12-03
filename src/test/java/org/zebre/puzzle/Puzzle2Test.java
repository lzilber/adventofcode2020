package org.zebre.puzzle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.zebre.puzzle.Puzzle2.Policy;

public class Puzzle2Test {

	@Test
	public void testCheckEntry() {
		Puzzle2 puzzle = new Puzzle2();
		String entry = "1-3 a: abcde";
		assertTrue(puzzle.checkEntry(entry, true));
		entry = "1-3 b: cdefg";
		assertFalse(puzzle.checkEntry(entry, true));
		entry = "2-9 c: ccccccccc";
		assertTrue(puzzle.checkEntry(entry, true));
	}
	
	@Test
	public void testPolicy() {
		Puzzle2 puzzle = new Puzzle2();
		String value = "1-3 a";
		Policy policy = puzzle.new Policy(value);
		assertEquals(1, policy.one);
		assertEquals(3, policy.two);
		assertEquals('a', policy.letter);
		
	}
	
	/*
1-3 a: abcde is valid: position 1 contains a and position 3 does not.
1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
	*/
	@Test
	public void testCheckLetters() {
		Puzzle2 puzzle = new Puzzle2();
		String entry = "1-3 a: abcde";
		assertTrue(puzzle.checkEntry(entry, false));
		entry = "1-3 b: cdefg";
		assertFalse(puzzle.checkEntry(entry, false));
		entry = "2-9 c: ccccccccc";
		assertFalse(puzzle.checkEntry(entry, false));
		
	}
	
	@Test
	public void testPattern() {
		String value = "1-3 a";
		String[] splits = value.split("\\W");
		assertTrue(splits.length>0);
	}
}
