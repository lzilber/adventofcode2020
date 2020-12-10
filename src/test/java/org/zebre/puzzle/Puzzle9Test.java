package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle9Test {
  
  Puzzle9 puzzle;

  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle9();
  }

  @Test
  public void testFindMatch() {
    List<String> input = Arrays.asList( "35", "20", "15", "25", "47", "40", "62", "55", "65", "95", 
        "102", "117", "150", "182", "127", "219", "299", "277", "309", "576" );
    assertEquals( 127, puzzle.findMatch(input, 5));
  }

  @Test
  public void testListToArray() {
    List<Long> listOfLongs = Arrays.asList(Long.parseLong("-123"), Long.parseLong("0"), Long.parseLong("1234567890"));
    long[] result = puzzle.listToArray(listOfLongs);
    assertEquals(3, result.length);
    assertEquals(0, result[1]);
  }

  @Test
  public void testGetFifoList() {
    List<String> input = Arrays.asList( "35", "20", "15", "25", "47", "40", "62", "55", "65", "95", 
        "102", "117", "150", "182", "127", "219", "299", "277", "309", "576" );
    List<Long> listOfLongs = puzzle.getFifoList(input, 5);
    assertEquals(5, listOfLongs.size());
    assertTrue(47 == listOfLongs.get(4));
  }

  @Test
  public void testIsSumInList() {
    long[] range1 = { 127, 219, 299, 277, 309 };
    assertTrue(puzzle.isSumInList(576, range1));
    long[] range2 = { 95, 102, 117, 150, 182 };
    assertFalse(puzzle.isSumInList(127, range2));
  }
  
  @Test
  public void testFindSumOfMatchRangeBounds() {
    List<String> input = Arrays.asList( "35", "20", "15", "25", "47", "40", "62", "55", "65", "95", 
        "102", "117", "150", "182", "127", "219", "299", "277", "309", "576" );
    assertEquals(62, puzzle.findSumOfMatchRangeBounds(input, 3, 127));
  }
}
