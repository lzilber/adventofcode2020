package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle16.Range;
import org.zebre.puzzle.Puzzle16.Ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle16Test {
  
  Puzzle16 puzzle;

  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle16();
  }
  
  @Test
  public void testParseTickets()  {
    List<String> input = Arrays.asList("nearby tickets","7,3,47","40,4,50","55,2,20");
    List<Ticket> tickets = puzzle.parseTickets(input);
    assertEquals(3, tickets.size());
    assertEquals(55, tickets.get(2).fields().get(0).intValue());
  }
  
  
  @Test
  public void testParseRanges()  {
    List<String> input = Arrays.asList("departure location: 32-174 or 190-967",
      "departure station: 50-580 or 588-960",
      "departure platform: 35-595 or 621-972",
      "");
    List<Range> ranges = puzzle.parseRanges(input, 10);
    assertEquals(6, ranges.size());
    assertFalse(ranges.get(2).isIn(49));
    assertFalse(ranges.get(2).isIn(581));
  }

  @Test
  public void testTicketValidate()  {
    Ticket t = puzzle.new Ticket();
    t.fields().addAll(Arrays.asList(7, 1, 14));
    List<Range> ranges = new ArrayList<>();
    ranges.add(puzzle.new Range(1, 3));
    ranges.add(puzzle.new Range(5, 7));
    ranges.add(puzzle.new Range(6, 11));
    ranges.add(puzzle.new Range(33, 44));
    ranges.add(puzzle.new Range(13, 40));
    ranges.add(puzzle.new Range(45, 50));
    assertTrue(t.validate(ranges).isValid());
    // valid
    t.fields().clear();
    t.fields().addAll(Arrays.asList(7,3,47));
    assertTrue(t.validate(ranges).isValid());
    // invalid
    t.fields().clear();
    t.fields().addAll(Arrays.asList(40,4,50));
    assertFalse(t.validate(ranges).isValid());

    t.fields().clear();
    t.fields().addAll(Arrays.asList(55,2,20));
    assertFalse(t.validate(ranges).isValid());

    t.fields().clear();
    t.fields().addAll(Arrays.asList(38,6,12));
    assertFalse(t.validate(ranges).isValid());
  }
}
