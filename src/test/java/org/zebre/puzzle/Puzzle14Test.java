package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle14.MaskBloc;

import static org.junit.Assert.*;

public class Puzzle14Test {

  Puzzle14 puzzle;
  
  @Before
  public void setUp() {
    puzzle = new Puzzle14();
  }
  
  @Test
  public void testMaskBloc() {
    MaskBloc bloc = puzzle.new MaskBloc("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X");
    assertEquals(64, bloc.oneValue);
    assertEquals(~(66), bloc.xMask);
  }

  @Test
  public void testmaskStep1() {
    long oneValue = 64; 
    long zeroMask = 2; // all 0 to 1
    long xMask = ~(oneValue+zeroMask);
    assertEquals(73, puzzle.maskStep1(xMask, oneValue, 11));
    assertEquals(101, puzzle.maskStep1(xMask, oneValue, 101));
    assertEquals(64, puzzle.maskStep1(xMask, oneValue, 0));
  }

}
