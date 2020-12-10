package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle8.Instruction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Puzzle8Test {

  final List<String> input = Arrays.asList(
      "nop +0",
      "acc +1",
      "jmp +4",
      "acc +3",
      "jmp -3",
      "acc -99",
      "acc +1",
      "jmp -4",
      "acc +6"
    );
  
  final List<String> input2 = Arrays.asList(
      "nop +0",
      "acc +1",
      "jmp +4",
      "acc +3",
      "jmp -3",
      "acc -99",
      "acc +1",
      "nop -4",
      "acc +6"
    );
  
  Puzzle8 puzzle;
  
  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle8();
  }

  @Test
  public void testLoadProgram() {
    assertEquals(9, puzzle.loadProgram(input).size());
  }

  @Test
  public void testRunProgramUntilLoop() {
    Map<Integer, Instruction> program = puzzle.loadProgram(input);
    assertEquals(5, puzzle.runProgramUntilLoop(program));
  }

  @Test
  public void testRunProgramUntilLoopUpdated() {
    Map<Integer, Instruction> program = puzzle.loadProgram(input2);
    assertEquals(8, puzzle.runProgramUntilLoop(program));
  }

  @Test
  public void testRunProgramUntilEnd() {
    Map<Integer, Instruction> program = puzzle.loadProgram(input);
    assertEquals(8, puzzle.runProgramUntilEnd(program));
  }

}
