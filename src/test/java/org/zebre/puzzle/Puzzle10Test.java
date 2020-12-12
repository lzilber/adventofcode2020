package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle10Test {

  Puzzle10 puzzle;
  
  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle10();
  }

  @Test
  public void testOneJoltsAdapterArrangements() {
    int[] adapters = puzzle.sortedAdapters(input2);
    assertEquals(19208, puzzle.oneJoltsAdapterArrangements(adapters), 0.1);
  }


  List<String> input1 = Arrays.asList( "16", "10", "15", "5", "1", "11", "7", "19", "6", "12", "4" );
  List<String> input2 = Arrays.asList( "28", "33", "18", "42", "31", "14", "46", "20", "48", "47", "24", 
      "23", "49", "45", "19", "38", "39", "11", "1", "32", "25", "35", "8", "17", "7", "9", "4", "2", "34", "10", "3");
}
