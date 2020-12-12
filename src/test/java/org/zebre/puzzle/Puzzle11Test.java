package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle11Test {
  
  Puzzle11 puzzle;

  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle11();
  }

  @Test
  public void testLoadInput() {
    int[][] results = puzzle.loadInput(input);
    assertEquals(10, results.length);
    assertEquals(10, results[0].length);
    assertEquals('.', results[0][1]);
    assertEquals('L', results[1][0]);
  }
  
  @Test
  public void testGetAdjacentSeats() {
    int[][] seats = puzzle.loadInput(input); 
    puzzle.getAdjacentSeats(1, 0, seats);
    //System.out.println();
    puzzle.getAdjacentSeats(9, 9, seats);
    //System.out.println();
    int[] result = puzzle.getAdjacentSeats(2, 4, seats);
    assertEquals('L', result[0]);
    assertEquals('L', result[1]);
    assertEquals('L', result[2]);
    assertEquals('.', result[3]);
    assertEquals('.', result[4]);
    assertEquals('L', result[5]);
    assertEquals('.', result[6]);
    assertEquals('L', result[7]);
  }

  @Test
  public void testRoundOfRules() {
    int[][] seats = puzzle.loadInput(input);    
    printPuzzle(seats);
    int rounds = 0;
    while( puzzle.roundOfRules(seats) && rounds < 10 ) {
      printPuzzle(seats);
      rounds++;
      if (rounds == 1) {
        System.out.print("");
      }
    }
    // assertEquals(5, rounds);
    // assertEquals(37, puzzle.countSeats('#', seats));
    assertEquals(26, puzzle.countSeats('#', seats));
  }
  
  private void printPuzzle(int[][] seats) {
    for (int i = 0; i < seats.length; i++) {
      for (int j = 0; j < seats[i].length; j++) {
        System.out.print(Character.toChars(seats[i][j]));
      }
      System.out.println();
    }
    System.out.println(" - - -");
  }

  List<String> input = Arrays.asList(
      "L.LL.LL.LL",
      "LLLLLLL.LL",
      "L.L.L..L..",
      "LLLL.LL.LL",
      "L.LL.LL.LL",
      "L.LLLLL.LL",
      "..L.L.....",
      "LLLLLLLLLL",
      "L.LLLLLL.L",
      "L.LLLLL.LL");
}
