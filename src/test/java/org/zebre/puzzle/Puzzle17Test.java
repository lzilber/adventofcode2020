package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle17.Cube;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Puzzle17Test {

  Puzzle17 puzzle;
  Map<String, Cube> pocketDimension;
  
  @Before
  public void setUp() {
    puzzle = new Puzzle17();
    List<String> input = Arrays.asList(".#.", "..#", "###");
    pocketDimension = puzzle.createCubesFrom(input);
  }

  @Test
  public void testSixCycle() {
    pocketDimension = puzzle.oneCycle(pocketDimension);
    assertEquals(11, pocketDimension.keySet().size());
    for(int i=0; i<5; i++) {
      pocketDimension = puzzle.oneCycle(pocketDimension);    
    }
    assertEquals(112, pocketDimension.keySet().size());
  }
/*
1:2:0
1:1:0
2:0:0

 2 .N..
 1 .Y.#
 0 ..Y#
 -1..#.
 * 
 */
  
  @Test
  public void testOneCycle() {
    for(Cube cube: pocketDimension.values()) {
      System.out.println(cube);
    }
    System.out.println(" --- ");
    Map<String, Cube> results = puzzle.oneCycle(pocketDimension);
    for(Cube cube: results.values()) {
      System.out.println(cube);
    }
    String key = Puzzle17.makeKey(1, -1, 0);
    assertNotNull(results.get(key));
    assertTrue(results.get(key).isActive());
    key = Puzzle17.makeKey(1, 0, 0);
    assertNotNull(results.get(key));
    assertTrue(results.get(key).isActive());
    key = Puzzle17.makeKey(2, 0, 0);
    assertNotNull(results.get(key));
    assertTrue(results.get(key).isActive());
    key = Puzzle17.makeKey(0, 1, 0);
    assertNotNull(results.get(key));
    assertTrue(results.get(key).isActive());
    key = Puzzle17.makeKey(2, 1, 0);
    assertNotNull(results.get(key));
    assertTrue(results.get(key).isActive());
  }
  
  @Test
  public void testBuildExtendedList() {
    List<Cube> results = puzzle.buildExtendedList(pocketDimension);
    assertEquals(75, results.size());
    Cube c = puzzle.new Cube(1, -1, 0);
    assertTrue(results.contains(c));
  }
  
  /*
   * Initial
   * .#.
   * ..#
   * ###
   */
  
  @Test
  public void testNeighbors() {
    String key = Puzzle17.makeKey(1, 0, 0);
    Cube cube = pocketDimension.getOrDefault(key, puzzle.new Cube(1, 0, 0));
    List<Cube> neighbors = puzzle.getNeighbors(cube, pocketDimension);
    assertEquals(26, neighbors.size());
    assertFalse(neighbors.contains(cube));
    assertEquals(3, neighbors.stream().filter(Cube::isActive).count());

    key = Puzzle17.makeKey(2, 1, -1);
    cube = pocketDimension.getOrDefault(key, puzzle.new Cube(2, 1, -1));
    neighbors = puzzle.getNeighbors(cube, pocketDimension);
    assertEquals(26, neighbors.size());
    assertFalse(neighbors.contains(cube));
    assertEquals(4, neighbors.stream().filter(Cube::isActive).count());
  }
  
  @Test
  public void testCreateCubesFrom() {
    assertEquals(9, pocketDimension.size());
    assertEquals(5, pocketDimension.values().stream().filter(Cube::isActive).count());
    String key = Puzzle17.makeKey(0, 0, 0);
    assertTrue(pocketDimension.get(key).isActive());
    key = Puzzle17.makeKey(1, 0, 0);
    assertTrue(pocketDimension.get(key).isActive());
    key = Puzzle17.makeKey(2, 0, 0);
    assertTrue(pocketDimension.get(key).isActive());
    key = Puzzle17.makeKey(2, 1, 0);
    assertTrue(pocketDimension.get(key).isActive());
    key = Puzzle17.makeKey(1, 2, 0);
    assertTrue(pocketDimension.get(key).isActive());
  }

}
