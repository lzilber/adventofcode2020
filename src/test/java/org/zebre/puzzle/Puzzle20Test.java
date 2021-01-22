package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle20.Direction;
import org.zebre.puzzle.Puzzle20.Tile;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle20Test {
  
  Puzzle20 puzzle;

  @Before
  public void setUp() {
    puzzle = new Puzzle20();
  }
  
  @Test
  public void testSolvePart1() {
    assertEquals("20899048083289", puzzle.solvePart1(input));
  }
  
  @Test
  public void testParseTiles() {
    List<Tile> results = puzzle.parseTiles(input, 10);
    assertEquals(9, results.size());
    assertEquals(2311, results.get(0).id);
  }

  @Test
  public void testTileConstructor() {
    List<String> in = Arrays.asList(
        "Tile 2311:",
        "..##.#..#.",
        "##..#.....",
        "#...##..#.",
        "####.#...#",
        "##.##.###.",
        "##...#.###",
        ".#.#.#..##",
        "..#....#..",
        "###...#.#.",
        "..###..###");
    Tile t = puzzle.new Tile(in);
    assertEquals(2311, t.id);
    // top:    ..##.#..#. -> 0011010010
    assertEquals(Integer.parseInt("0011010010", 2), t.sidesAsArray()[0]);
    // right:  ...#.##..# -> 0001011001
    assertEquals(Integer.parseInt("0001011001", 2), t.sidesAsArray()[1]); // 101010
    // bottom: ..###..### -> 0011100111
    assertEquals(Integer.parseInt("0011100111", 2), t.sidesAsArray()[2]);
    // left:   .#####..#. -> 0111110010
    assertEquals(Integer.parseInt("0111110010", 2), t.sidesAsArray()[3]);
  }

  @Test
  public void testTileFlipBits() {
    Tile t = puzzle.new Tile(input);
    int value = Integer.parseInt("0100111110", 2);
    int result = t.flipBits(value);
    assertEquals(Integer.parseInt("0111110010", 2), result); // 0011010010
  }
  
  @Test
  public void testTileSideToHashcode() {
    Tile t = puzzle.new Tile(input);
    assertEquals(210, t.tileSideToHashcode("..##.#..#.")); // 0011010010
  }
  
  @Test
  public void testCountMatchingTiles() {
    List<Tile> tiles = puzzle.parseTiles(input, 10);
    Tile t = tiles.get(0);
    assertEquals(2311, t.id);
    assertEquals(3,t.countMatchingTiles(tiles));
    t = tiles.get(1);
    assertEquals(1951, t.id);
    assertEquals(2,t.countMatchingTiles(tiles));
    t = tiles.get(2);
    assertEquals(1171, t.id);
    assertEquals(2,t.countMatchingTiles(tiles));
    t = tiles.get(3);
    assertEquals(1427, t.id);
    assertEquals(4,t.countMatchingTiles(tiles));
    t = tiles.get(4);
    assertEquals(1489, t.id);
    assertEquals(3,t.countMatchingTiles(tiles));
    t = tiles.get(5);
    assertEquals(2473, t.id);
    assertEquals(3,t.countMatchingTiles(tiles));
  }
  
  @Test
  public void testNeighbors()  {
    Tile t = puzzle.new Tile(input);
    Tile n = puzzle.new Tile();
    assertEquals(0, t.neighborhood()); 
    t.neighbors.put(Direction.bottom, n);
    assertEquals(1, t.neighborhood()); 
    t.neighbors.put(Direction.left, n);
    assertEquals(2, t.neighborhood()); 
    t.neighbors.put(Direction.right, n);
    assertEquals(3, t.neighborhood()); 
    t.neighbors.put(Direction.top, n);
    assertEquals(4, t.neighborhood()); 
  }
  
  List<String> input = Arrays.asList(
      "Tile 2311:",
      "..##.#..#.",
      "##..#.....",
      "#...##..#.",
      "####.#...#",
      "##.##.###.",
      "##...#.###",
      ".#.#.#..##",
      "..#....#..",
      "###...#.#.",
      "..###..###",
      "",
      "Tile 1951:",
      "#.##...##.",
      "#.####...#",
      ".....#..##",
      "#...######",
      ".##.#....#",
      ".###.#####",
      "###.##.##.",
      ".###....#.",
      "..#.#..#.#",
      "#...##.#..",
      "",
      "Tile 1171:",
      "####...##.",
      "#..##.#..#",
      "##.#..#.#.",
      ".###.####.",
      "..###.####",
      ".##....##.",
      ".#...####.",
      "#.##.####.",
      "####..#...",
      ".....##...",
      "",
      "Tile 1427:",
      "###.##.#..",
      ".#..#.##..",
      ".#.##.#..#",
      "#.#.#.##.#",
      "....#...##",
      "...##..##.",
      "...#.#####",
      ".#.####.#.",
      "..#..###.#",
      "..##.#..#.",
      "",
      "Tile 1489:",
      "##.#.#....",
      "..##...#..",
      ".##..##...",
      "..#...#...",
      "#####...#.",
      "#..#.#.#.#",
      "...#.#.#..",
      "##.#...##.",
      "..##.##.##",
      "###.##.#..",
      "",
      "Tile 2473:",
      "#....####.",
      "#..#.##...",
      "#.##..#...",
      "######.#.#",
      ".#...#.#.#",
      ".#########",
      ".###.#..#.",
      "########.#",
      "##...##.#.",
      "..###.#.#.",
      "",
      "Tile 2971:",
      "..#.#....#",
      "#...###...",
      "#.#.###...",
      "##.##..#..",
      ".#####..##",
      ".#..####.#",
      "#..#.#..#.",
      "..####.###",
      "..#.#.###.",
      "...#.#.#.#",
      "",
      "Tile 2729:",
      "...#.#.#.#",
      "####.#....",
      "..#.#.....",
      "....#..#.#",
      ".##..##.#.",
      ".#.####...",
      "####.#.#..",
      "##.####...",
      "##..#.##..",
      "#.##...##.",
      "",
      "Tile 3079:",
      "#.#.#####.",
      ".#..######",
      "..#.......",
      "######....",
      "####.#..#.",
      ".#...#.##.",
      "#.#####.##",
      "..#.###...",
      "..#.......",
      "..#.###...",
      "");
}

