package org.zebre.puzzle;

import java.util.List;

public class Puzzle10 {

  public int[] sortedAdapters(List<String> input) {
    return input.stream().mapToInt(Integer::parseInt).sorted().toArray();
  }
  
  public int numberOfJolts(int difference, int[] adapters) {
    int count = 0;
    int lastJolt = 0;
    for (int i = 0; i < adapters.length; i++) {
      int a = adapters[i];
      if ( (a - lastJolt) == difference) {
        count++;
      }
      lastJolt = a;
    }
    return count;
  }
  
  public double oneJoltsAdapterArrangements(int[] adapters) {
    double count = 1;
    int consecutiveOneJolt = 0;
    int lastJolt = 0;
    for (int i = 0; i < adapters.length; i++) {
      int a = adapters[i];
      if ( (a - lastJolt) == 1) {
        consecutiveOneJolt++;
      } else if (consecutiveOneJolt > 0) {
        count = count * getPossibilitiesFor(consecutiveOneJolt);
        consecutiveOneJolt = 0;
      }
      lastJolt = a;
    }
    if (consecutiveOneJolt > 0) { 
      count = count * getPossibilitiesFor(consecutiveOneJolt);
    }
    return count;
  }
  
  /* nombre de chemin entre deux noeuds d'un graphe 
   * S--x--y--F 
   *  -----y--
   *  --x-----
   *  etc.
   */                
  public double getPossibilitiesFor(int consecutiveOneJolt) {
    switch (consecutiveOneJolt) {
      case 4:
        return 7;
      case 3:
        return 4;
      case 2:
        return 2;
      default:
        break;
    }
    return 1;
  }
}
