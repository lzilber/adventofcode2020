package org.zebre.puzzle;

import java.util.HashMap;
import java.util.Map;

public class Puzzle15 {
  
  protected static final int[] INPUT = {5,1,9,18,13,8,0};

  public int solvePart1() {
    return playNTurns(2020, INPUT);
  }
  
  public int solvePart2() {
    return playNTurns(30000000, INPUT);
  }
  
  public Map<Integer, Integer> initMap(int[] input) {
    Map<Integer, Integer> numbers = new HashMap<>();
    for (int i = 0; i < input.length; i++) {
      numbers.put(input[i], i+1);
    }
    return numbers;
  }
  
  /**
   * Give the spoken number for an input and a turn
   * @param n
   * @param input
   * @return
   */
  public int playNTurns(int n, int[] input) {
    Map<Integer, Integer> numbers = initMap(input); // Numbers -> Last turn spoken
    Integer spoken = 0;
    int next = 0;
    for (int turn=input.length+1; turn<=n; turn++) {
      spoken = next;
      next = playTurn(turn, spoken, numbers);
      numbers.put(spoken, turn);
      //System.out.println(turn + " -> " + spoken);
    }
    return spoken;
  }

  /**
   * Play one turn according to the rules
   * @param turn
   * @param spoken
   * @param numbers
   * @return
   */
  public int playTurn(int turn, int spoken, Map<Integer, Integer> numbers) {
    if (!numbers.containsKey(spoken)) {
      return 0;
    }
    int lastTurn = numbers.get(spoken);
    return (turn - lastTurn);
  }
}
