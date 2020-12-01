package org.zebre.puzzle;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Puzzle1Test {

	final List<Integer> input = Arrays.asList(1721, 979, 366, 299, 675, 1456);

	@Test
	public void testMatch2() {
		Puzzle1 puzzle = new Puzzle1();
		AbstractMap.SimpleImmutableEntry<Integer, Integer> result = puzzle.match2(input, 2020);
		assertNotNull(result);
		System.out.println(result);
	}

	@Test
	public void testMultiplyEntry() {
		Puzzle1 puzzle = new Puzzle1();
		AbstractMap.SimpleImmutableEntry<Integer, Integer> result = new SimpleImmutableEntry<>(1721, 299);
		assertEquals(Integer.valueOf(514579), puzzle.multiply(result));
	}

	@Test
	public void testMatch3() {
		Puzzle1 puzzle = new Puzzle1();
		List<Integer> result = puzzle.match3(input, 2020);
		assertNotNull(result);
		System.out.println(result);
	}

	@Test
	public void testMultiplyList() {
		Puzzle1 puzzle = new Puzzle1();
		assertEquals(Integer.valueOf(241861950), puzzle.multiply(Arrays.asList(979, 366, 675)));
	}
}
