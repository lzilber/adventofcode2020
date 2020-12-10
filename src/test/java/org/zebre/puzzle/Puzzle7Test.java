package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Puzzle7Test {
  
  final List<String> input = Arrays.asList(
      "vibrant bronze bags contain 2 light red bags.",
      "light red bags contain 1 bright white bag, 2 muted yellow bags.",
      "dark orange bags contain 3 bright white bags, 4 muted yellow bags.",
      "bright white bags contain 1 shiny gold bag.",
      "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.",
      "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.",
      "dark olive bags contain 3 faded blue bags, 4 dotted black bags.",
      "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.",
      "faded blue bags contain no other bags.",
      "dotted black bags contain no other bags."
    );
  
  Puzzle7 puzzle;

  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle7();
  }

  @Test
  public void testCountBagOptionsFor() {
    assertEquals(5, puzzle.countBagOptionsFor(input, "shiny gold"));
  }
    
  @Test
  public void testParseBags() {
    assertEquals(10, puzzle.parseBags(input).size());
  }
  
  @Test
  public void testCountBagInside() {
    assertEquals(32, puzzle.countBagInside(input, "shiny gold"));    
  }

}
