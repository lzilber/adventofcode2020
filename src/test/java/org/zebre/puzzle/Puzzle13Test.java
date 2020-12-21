package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle13.BusData;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle13Test {
  
  Puzzle13 puzzle;

  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle13();
  }

  @Test
  public void testBusMatch() {
    int[] buss = {13, 37, 449};
    BusData bus = puzzle.busMatch(buss, 500);
    assertEquals(507,bus.timestamp);
    assertEquals(13,bus.id);
  }
  
  @Test
  public void testJustAboveMultiple() {
    assertEquals(945, puzzle.justAboveMultiple(7, 939));
    assertEquals(944, puzzle.justAboveMultiple(59, 939));
  }
  
  @Test
  public void testBusList() {
    int[] buss = puzzle.busList("13,x,x,x,37,x,449");
    assertEquals(3, buss.length);
    assertEquals(37, buss[1]);
  }
  
  @Test
  public void testBussOffset() {
    int[] offsets = puzzle.busOffsets("13,x,x,x,37,x,449");
    assertEquals(3, offsets.length);
    assertEquals(4, offsets[1]);
    assertEquals(6, offsets[2]);
  }
  
  @Test
  public void testBrutalEarliestTimestamp() {
    BigDecimal base = new BigDecimal(7);
    int[] buss = {13, 59, 31, 19};
    int[] offs = {1, 4, 6, 7};
    BigDecimal[] values = new BigDecimal[buss.length];
    BigDecimal[] offsets = new BigDecimal[buss.length];    
    for (int i = 0; i < buss.length; i++) {
      values[i] = new BigDecimal(buss[i]);
      offsets[i] = new BigDecimal(offs[i]);
    }
    BigDecimal start = new BigDecimal(1000000);
    assertEquals(new BigDecimal(1068781), puzzle.brutalEarliestTimestamp(start, base, values, offsets));
    
    base = new BigDecimal(1789);
    int[] buss2 = {37, 47, 1889};
    int[] offs2 = {1, 2, 3};
    BigDecimal[] values2 = new BigDecimal[buss2.length];
    BigDecimal[] offsets2 = new BigDecimal[buss2.length];    
    for (int i = 0; i < buss2.length; i++) {
      values2[i] = new BigDecimal(buss2[i]);
      offsets2[i] = new BigDecimal(offs2[i]);
    }
    start = new BigDecimal("1000000000");
    assertEquals(new BigDecimal("1202161486"), puzzle.brutalEarliestTimestamp(start, base, values2, offsets2));
  }

  @Test
  public void testBetterBusList() {
    List<BusData> result = puzzle.betterBusList("13,x,x,x,37,x,449");
    assertEquals(3, result.size());
    assertEquals(449, result.get(0).id);
    assertEquals(13, result.get(2).id);
    assertEquals(-6, result.get(2).offset);
  }
  
  @Test
  public void testChineseRemainderTheorem() {
    int[] buss = {7, 13, 59, 31, 19};
    int[] offs = {0, 1, 4, 6, 7};
    assertEquals(1068781, puzzle.chineseReminderTheorem(buss, offs));

  }
}
