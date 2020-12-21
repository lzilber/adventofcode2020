package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Puzzle15Test {

/*
 * Given the starting numbers 0,3,6, the 2020th number spoken is 436.
 * Given the starting numbers 1,3,2, the 2020th number spoken is 1.
Given the starting numbers 2,1,3, the 2020th number spoken is 10.
Given the starting numbers 1,2,3, the 2020th number spoken is 27.
Given the starting numbers 2,3,1, the 2020th number spoken is 78.
Given the starting numbers 3,2,1, the 2020th number spoken is 438.
Given the starting numbers 3,1,2, the 2020th number spoken is 1836.
 */
  
  Puzzle15 puzzle;
  
  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle15();
  }

  @Test
  public void testPlayNTurns() {
    int[] input = {0, 3, 6};
    assertEquals(4, puzzle.playNTurns(9, input));
    assertEquals(0, puzzle.playNTurns(10, input));
    assertEquals(436, puzzle.playNTurns(2020, input));
    assertEquals(175594, puzzle.playNTurns(30000000, input));
  }
  
  @Test
  public void testInitMap() {
    int[] input = {0, 3, 6};
    Map<Integer, Integer> numbers = puzzle.initMap(input);
    assertEquals(Integer.valueOf(1), numbers.get(0));
    assertEquals(Integer.valueOf(2), numbers.get(3));
    assertEquals(Integer.valueOf(3), numbers.get(6));
    assertNull(numbers.get(4));
  }

  @Test
  public void testPlayTurn() {
    Map<Integer, Integer> numbers = new HashMap<>();
    numbers.put(0, 1);
    numbers.put(3, 2);
    numbers.put(6, 3);
    assertEquals(3, puzzle.playTurn(4, 0, numbers));
    numbers.put(0, 4);
    assertEquals(3, puzzle.playTurn(5, 3, numbers));
    numbers.put(3, 5);
    assertEquals(1, puzzle.playTurn(6, 3, numbers));
    numbers.put(3, 6);
    assertEquals(0, puzzle.playTurn(7, 1, numbers));
    numbers.put(1, 7);
    assertEquals(4, puzzle.playTurn(8, 0, numbers));
  }

}
