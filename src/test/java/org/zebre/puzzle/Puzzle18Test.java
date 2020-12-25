package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Puzzle18Test {
  
  Puzzle18 puzzle;

  @Before
  public void setUp() {
    puzzle = new Puzzle18();
  }
  
  @Test
  public void testComputeRecursively() {
    String line = "0 + 0";
    int[] chars = line.chars().toArray();
    assertEquals(0, puzzle.computeRecursively(chars, 0).value);
    
    line = "2 * 3 + (4 * 5)";
    chars = line.chars().toArray();
    assertEquals(26, puzzle.computeRecursively(chars, 0).value);
    
    line = "((2 * 3) + (4 * 5))";
    chars = line.chars().toArray();
    assertEquals(26, puzzle.computeRecursively(chars, 0).value);

    line = "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2";
    chars = line.chars().toArray();
    assertEquals(13632, puzzle.computeRecursively(chars, 0).value);
    
    line = "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))";
    chars = line.chars().toArray();
    assertEquals(12240, puzzle.computeRecursively(chars, 0).value);

    line = "1 + (2 * 3) + (4 * (5 + 6))";
    chars = line.chars().toArray();
    assertEquals(51, puzzle.computeRecursively(chars, 0).value);
    
    line = "(8 * 2 + 8) + (8 * 5 * 8 * 7 * 8 * (3 * 3 + 9)) + 6 + 9 * (4 * 7 + 3 + 2)";
    chars = line.chars().toArray();
    assertEquals(10645767, puzzle.computeRecursively(chars, 0).value);
  }

  @Test
  public void testEvaluate() {
    assertEquals(0, puzzle.evaluate("0 + 0"));
    assertEquals(71, puzzle.evaluate("1 + 2 * 3 + 4 * 5 + 6"));
  }

}
