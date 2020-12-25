package org.zebre.puzzle;

import java.util.List;

public class Puzzle18 {
  
  public class Computed {
    public final long value ;
    public final int skip ;
    public Computed(long v, int i) {
      value = v;
      skip = i;
    }
    @Override
    public String toString() {
      return value + "(" + skip + ")";
    }
  }
  
  //      char[] chars = line.chars().toArray();

  public Computed computeRecursively(int[] chars, int start) {
    int index = start;
    long stack = 0;
    int operation = 0;
    while (index < chars.length) {
      switch (chars[index]) {
        case ' ':
          break;
        case '(':
          Computed number = computeRecursively(chars, index+1);
          stack = operation(stack, operation, number.value);
          operation = 0;
          index = number.skip; 
          break;
        case ')':
          return new Computed(stack, index);
        case '+':
          operation = 1;
          break;
        case '*':
          operation = 2;
          break;
        default: // digit
          int digit = chars[index] - '0';
          stack = operation(stack, operation, digit);
          operation = 0;
          break;
      }
      index++;
    }
    return new Computed(stack, index);
  }
  
  private long operation(long a, int op, long b) {
    if (op == 1) {
      a = a + b;
    } else if (op == 2) {
      a = a * b;
    } else {
      a = b;
    }
    return a;
  }

  public String printChars(int[] chars, int start) {
    if (! (start<chars.length)) {
      return "out of range";
    }
    char[] data = new char[chars.length - start];
    for (int i = start; i < chars.length; i++) {
      data[i-start] = (char) chars[i];
    }
    return String.valueOf(data);
  }
  
  /**
   * Evaluate an expression of single digits with operations, separated by white space.
   * @param expression
   * @return
   */
  public long evaluate(String expression) {
    String[] chars = expression.split(" ");
    long stack = Long.parseLong(chars[0]);
    int operation = 0;
    for(int i=1; i<chars.length; i++) {
        if (operation == 0) {
          operation = chars[i].equals("+") ? 1 : 2;
        } else {
          long number = Long.parseLong(chars[i]);
          if (operation == 1) {
            stack = stack + number;
          } else {
            stack = stack * number;
          }
          operation = 0;
        }  
    }
    return stack;
  }

  public String solvePart1(List<String> entries) {
    long result = 0;
    for(String line: entries) {
      int[] chars = line.chars().toArray();
      Computed c =  computeRecursively(chars, 0);
      result = result + c.value;
      System.out.println(" > " + c.value + " = " + result  );
    }
    return ""+result;
  }

}
