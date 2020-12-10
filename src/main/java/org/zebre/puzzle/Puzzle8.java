package org.zebre.puzzle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Puzzle8 {

  public Map<Integer, Instruction> loadProgram(List<String> input) {
    Map<Integer, Instruction> instructions = new HashMap<>();
    int id = 1;
    for(String line : input) {
      instructions.put(id++, new Instruction(line));
    }
    return instructions;
  }
  
  public long runProgramUntilLoop(Map<Integer, Instruction> program) {
    Set<Integer> executed = new HashSet<>();
    long accumulator = 0;
    int currentLine = 1;
    boolean loopFound = false,
        endReached = false;
    while(!loopFound && !endReached) {
      executed.add(currentLine);
      Instruction instruction = program.get(currentLine);
      accumulator += instruction.accumulator(); 
      int nextLine = instruction.nextLine(currentLine);
      if (nextLine > program.size()) {
        System.out.println("End reached line " + currentLine);
        endReached = true;
      }
      if (executed.contains(nextLine)) {
        loopFound = true;
        System.out.println("bad instruction found line " + currentLine);
        System.out.println("Dump of previous " + executed.size() + " instructions...");
        for(Integer index: executed) {
          System.out.println(program.get(index));
        }
      } else {
        currentLine = nextLine;
      }
    }
    return accumulator;
  }

  public int runProgramUntilEnd(Map<Integer, Instruction> program) {
    int currentLine = 1;
    int safeguard = 0;
    while(safeguard++ < 1000) {
      Instruction instruction = program.get(currentLine);
      if(! (instruction.type == InstructionType.acc)) {
        Instruction swapped = instruction.swapNopJmp();
        program.put(currentLine, swapped);
        if (runProgramFrom(currentLine, program)) {
          System.out.println("End reached after swapping line " + currentLine);
          return currentLine;
        } else {
          program.put(currentLine, instruction);
        }       
      }
      currentLine = instruction.nextLine(currentLine);
    }
    return 0;
  }
  
  /**
   * Run from one Instruction index.
   * @param index
   * @param program
   * @return true if it reached the end.
   */
  public boolean runProgramFrom(int index, Map<Integer, Instruction> program) {
    Set<Integer> executed = new HashSet<>();
    int currentLine = 1;
    int safeguard = 0;
    while(safeguard++ < 1000) {
      executed.add(currentLine);
      Instruction instruction = program.get(currentLine);
      int nextLine = instruction.nextLine(currentLine);
      if (nextLine > program.size()) {
        System.out.println("From index " + index + ", end reached!");
        return true;
      }
      if (executed.contains(nextLine)) {
        System.out.println("From index " + index + ", bad instruction found line " + currentLine);
        return false;
      } else {
        currentLine = nextLine;
      }
    }
    return false;
  }
  
  public enum InstructionType {
    acc, jmp, nop
  }
  
  public class Instruction {
    private final InstructionType type;
    private final int argument;
    
    public Instruction(InstructionType t, int a) {
      this.type = t;
      this.argument = a;
    }
    
    public Instruction(String line) {
      String[] parts = line.split(" ");
      this.type = InstructionType.valueOf(parts[0]);
      this.argument = Integer.parseInt(parts[1]);
    }
    
    public int accumulator() {
      if (type == InstructionType.acc) {
        return argument;
      } else {
        return 0;
      }
    }
    
    public int nextLine(int currentLine) {
      switch (type) {
        case acc:
          return currentLine + 1;
        case jmp:
          return currentLine + argument;
        case nop:
          return currentLine + 1;
        default:
          break;
      }
      return 0;
    }
    
    public Instruction swapNopJmp() {
      if (type == InstructionType.jmp) {
        return new Instruction(InstructionType.nop, argument);
      } else if (type == InstructionType.nop) {
        return new Instruction(InstructionType.jmp, argument);        
      }
      return this;
    }
    
    @Override
    public String toString() {
      return type.name() + " " + (argument>0?"+":"") + argument;
    }
  }
}
