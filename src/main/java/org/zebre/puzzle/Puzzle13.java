package org.zebre.puzzle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Puzzle13 {

  static final long TIMESTAMP = 1002576;
  static final String INPUT = "13,x,x,x,x,x,x,37,x,x,x,x,x,449,x,29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,19,x,x,x,23,x,x,x,x,x,x,x,773,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,17";
  
  public long solvePart1() {
    int[] buses = busList(INPUT);
    BusData match = busMatch(buses, TIMESTAMP);
    return (match.id * (match.timestamp - TIMESTAMP));
  }
  
  public BigDecimal solvePart2() {
    // this did not complete after 30 minutes...trying another way below    
    /*
     * int[] buses = busList(INPUT);
     * int[] offs = busOffsets(INPUT);
     * BigDecimal[] values = new BigDecimal[buses.length];
     * BigDecimal[] offsets = new BigDecimal[buses.length];
     * for (int i = 0; i < buses.length; i++) {
     * values[i] = new BigDecimal(buses[i]);
     * offsets[i] = new BigDecimal(offs[i]);
     * }
     * BigDecimal start = new BigDecimal("100000000000000");
     * BigDecimal base = new BigDecimal(buses[0]);
     * return brutalEarliestTimestamp(start, base, values, offsets);
     */  
    
    // still not good, looked up for tips -> Chinese Remainder Theorem
    /*
     * List<BusData> buses = betterBusList(INPUT);
     * BigDecimal[] values = new BigDecimal[buses.size()-1];
     * BigDecimal[] offsets = new BigDecimal[buses.size()-1];
     * for (int i = 1; i < buses.size(); i++) {
     * values[i-1] = new BigDecimal(buses.get(i).id);
     * offsets[i-1] = new BigDecimal(buses.get(i).offset);
     * }
     * BigDecimal start = new BigDecimal("100000000000000");
     * BigDecimal base = new BigDecimal(buses.get(0).id);
     * return brutalEarliestTimestamp(start, base, values, offsets);
     */
    
    int[] buses = busList(INPUT);
    int[] offs = busOffsets(INPUT);
    return new BigDecimal(chineseReminderTheorem(buses, offs));
  }
  
  public BusData busMatch(int[] buses, long threshold) {
    BusData result = new BusData();
    for (int bus : buses) {
      long multiple = justAboveMultiple(bus, threshold);
      if (multiple < result.timestamp) {
        result.timestamp = multiple;
        result.id = bus;
      }
    }
    return result;
  }
  
  public long justAboveMultiple(int of, long threshold) {
    return ((threshold / of) + 1) * of;
  }
  
  public int[] busList(String input) {
    List<Integer> result = new ArrayList<>();
    for (String busId : input.split(",")) {
      if(!busId.equals("x")) {
        result.add(Integer.parseInt(busId));
      }
    }
    return result.stream().mapToInt(i -> i).toArray();
  }
  
  public int[] busOffsets(String input) {
    List<Integer> result = new ArrayList<>();
    int offset = 0;
    for (String busId : input.split(",")) {
      if(!busId.equals("x")) {
        result.add(offset);
      }
      offset++;
    }
    return result.stream().mapToInt(i -> i).toArray();
  }
  
  public List<BusData> betterBusList(String input) {
    List<BusData> result = new ArrayList<>();
    int offset = 0;
    for (String busId : input.split(",")) {
      if(!busId.equals("x")) {
        BusData data = new BusData();
        data.id = Integer.parseInt(busId);
        data.offset = offset;
        result.add(data);
      }
      offset++;
    }
    // Now sort by value descending
    result.sort(Collections.reverseOrder());
    
    // update the offsets to be relative from first value
    int delta = result.get(0).offset;
    for (BusData busData : result) {
      busData.offset -= delta;
    }
    
    return result;
  }
  
  public BigDecimal brutalEarliestTimestamp(BigDecimal start, BigDecimal base, BigDecimal values[], BigDecimal[] offsets) {
    BigDecimal timestamp = start.divideToIntegralValue(base).multiply(base);
    boolean allfound = false;
    long safeguard = Long.MAX_VALUE;
    while (!allfound && safeguard > 0) {
      timestamp = timestamp.add(base);
      // System.out.println(safeguard + ") " + timestamp);
      allfound = true;
      for(int i=0; i<values.length; i++) {
        BigDecimal dividend = timestamp.add(offsets[i]);
        BigDecimal divisor = values[i];
        if (!dividend.remainder(divisor).equals(BigDecimal.ZERO)) {
          allfound = false;
          break;
        } 
      }
      safeguard--;
    }
    if (safeguard == 0) {
      System.out.println("safeguard reached at " + timestamp);
      timestamp = BigDecimal.ZERO;
    }
    return timestamp;
  }
  
  public long chineseReminderTheorem(int[] values, int[] reminders) {
    // Product of all numbers
    long product = 1;
    for(int i=0; i<values.length; i++ ){
      product *= values[i];
    }
    
    long[] partialProduct = new long[values.length];
    for(int i=0; i<values.length; i++){
      partialProduct[i] = product/values[i];
    }
    
    long[] inverse = new long[values.length];
    for(int i=0; i<values.length; i++) {
      inverse[i] = computeInverse(partialProduct[i], values[i]);

    }
    
    // need to figure out this
    long rem[] = new long[values.length];
    for(int i=0; i<values.length; i++){
      rem[i] = (-1 * reminders[i]) % values[i] + values[i];
    }    
    
    long sum = 0;
    for (int i = 0; i < values.length; i++) {
      sum += partialProduct[i] * inverse[i] * rem[i];
    }
    
    return sum % product;
  }
  
  private  long computeInverse(long a, long b) { 
    long m = b, t, q; 
    long x = 0, y = 1; 

    if (b == 1) 
      return 0; 

    // Apply extended Euclid Algorithm 
    while (a > 1) 
    { 
      // q is quotient 
      q = a / b; 
      t = b; 

      // now proceed same as Euclid's algorithm 
      b = a % b;a = t; 
      t = x; 
      x = y - q * x; 
      y = t; 
    } 

    // Make x1 positive 
    if (y < 0) 
      y += m; 

    return y; 
  }
  
  public class BusData implements Comparable<BusData> {
    public int id = 0;
    public long timestamp = Long.MAX_VALUE;
    public int offset = 0; // part 2
    
    @Override
    public int compareTo(BusData o) {
      return Integer.valueOf(id)
          .compareTo(Integer.valueOf(o.id));
    }
    
    
  }
}
