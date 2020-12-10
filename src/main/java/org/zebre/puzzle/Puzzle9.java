package org.zebre.puzzle;

import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.LongSummaryStatistics;

public class Puzzle9 {
    
  /**
   * 
   * @param input
   * @param listSize
   * @return
   */
  public long findMatch(List<String> input, int listSize) {
    List<Long> fifoList = getFifoList(input, listSize);
    int index = listSize;
    for(int i=index; i<input.size(); i++) {
      long number = Long.parseLong(input.get(i));
      long[] previousNumbers = listToArray(fifoList);
      if(!isSumInList(number, previousNumbers)) {
        return number;
      }
      fifoList.remove(0); // SonarLint FP ? java:S5413
      fifoList.add(number);
    }
    return 0;
  }
  
  /**
   * Convert a List of Long into an array of long
   * What did I learn? Stream to map lists to array
   * @param listOfLongs
   * @return
   */
  protected long[] listToArray(List<Long> listOfLongs) {
    return listOfLongs.stream().mapToLong(l -> l).toArray();
  }
  
  /**
   * Build a LikedList from elements of the argument List.
   * @param input
   * @param listSize
   * @return
   */
  public List<Long> getFifoList(List<String> input, int listSize) {
    List<Long> fifoList = new LinkedList<>();
    for(int i=0; i<listSize; i++) {
      fifoList.add(Long.parseLong(input.get(i)));
    }
    return fifoList;
  }
  
  public boolean isSumInList(long number, long[] previousNumbers) {
    for (int i = 0; i < previousNumbers.length - 1; i++) {
      for (int j = i+1; j < previousNumbers.length; j++) {
        if(number == (previousNumbers[i] + previousNumbers[j])) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static final long PART1 = 177777905;
  
  public long findSumOfMatchRangeBounds(List<String> input, int start, long match) {
    List<Long> range = getFifoList(input, start);
    int index = start;
    while (index < input.size()) {
      switch (compareSum(range, match) ) {
        case 0:
          return sumMinMax(range);
        case 1:
          System.out.println("removing first");
          range.remove(0);
          break;
        default:
          System.out.println("adding index " + index);
          range.add(Long.parseLong(input.get(index)));
          index++;
      }
    }
    return 0;
  }
  
  /**
   * Compare the range, returns -1 if less than match, 0 if equals and +1 if above
   * What did I learn ? Another cool example of Streams to sum values
   * @param listOfLongs
   * @param match
   * @return
   */
  public int compareSum(List<Long> listOfLongs, long match) {
    long sum = listOfLongs.stream().mapToLong(l -> l).sum();
    System.out.println("sum is " + sum);
    if (sum == match) {
      return 0;
    }
    return (sum < match ? -1 : 1);
  }
  
  /**
   * Find min and max, and returns their sum
   * What did I learn ? Stream have SummaryStatistics to collect values in one pass
   * @param listOfLongs
   * @return
   */
  public long sumMinMax(List<Long> listOfLongs) {
    LongSummaryStatistics stats = listOfLongs.stream().mapToLong(l -> l).summaryStatistics();
    return stats.getMin() + stats.getMax();
  }
  
}
