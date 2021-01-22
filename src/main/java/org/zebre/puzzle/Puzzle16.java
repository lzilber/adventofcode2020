package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle16 {

  public int solvePart1(List<String> input) {
    List<Range> ranges = parseRanges(input, 100);
    List<Ticket> tickets = parseTickets(input);
    return outOfRanges(tickets, ranges).stream().mapToInt(Integer::intValue).sum();
  }
  
  public void solvePart2(List<String> input) {
    List<Range> ranges = parseRanges(input, 12);
    List<Ticket> tickets = parseTickets(input);
    List<Ticket> valids = new ArrayList<>();
    for(Ticket t : tickets) {
      if (t.validate(ranges).isValid()) {
        valids.add(t);
      }
    }
    System.out.println(valids.size() + " tickets");
    
    Field[] departures = new Field[6];
    for(int i=0; i<12; i=i+2) {
      departures[i/2] = new Field(ranges.get(i), ranges.get(i+1));
    }
    
    for(Field d : departures) {
      List<Integer> matches = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19));
      System.out.print(d+ " options:");
      for (Ticket t : tickets) {
        if (t.validate(ranges).isValid()) {
          List<Integer> found = d.nonMatchingFieldIndex(t);
          if (!found.isEmpty()) {
            for (Integer n : found) {
              matches.remove(n);
            }
          }
        }
      }
      System.out.println(matches);
    }
  }
  
  public List<Range> parseRanges(List<String> input, int limit) {
    List<Range> ranges = new ArrayList<>();
    int index = 0;
    String line = input.get(index);
    while(!line.isBlank() && index < limit) {
      String[] blocks = line.split(":")[1].split("or");
      String[] r1 = blocks[0].trim().split("-");
      String[] r2 = blocks[1].trim().split("-");
      ranges.add( new Range(Integer.parseInt(r1[0]), Integer.parseInt(r1[1])) );
      ranges.add( new Range(Integer.parseInt(r2[0]), Integer.parseInt(r2[1])) );
      index++;
      line = input.get(index);
    }
    return ranges;
  }
  
  public List<Ticket> parseTickets(List<String> input) {
    List<Ticket> tickets = new ArrayList<>();
    boolean reached = false;
    for(String line : input) {
      if (reached) {
        List<Integer> numbers = Arrays.stream(line.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        Ticket t = new Ticket();
        t.fields().addAll(numbers);
        tickets.add(t);
      } else if (line.startsWith("nearby tickets")) {
        reached = true;
      }
    }
    return tickets;
  }
  
  public List<Integer> outOfRanges(List<Ticket> tickets, List<Range> ranges) {
    List<Integer> errorRates = new ArrayList<>();
    for(Ticket ticket : tickets) {
      if (!ticket.validate(ranges).isValid()) {
        errorRates.add(ticket.errorRate());
      } else {
        // System.out.println(ticket);
      }
    }
    return errorRates;
  }
  
  public List<Range> validRanges(List<Ticket> tickets, List<Range> ranges) {
    List<Range> fieldRanges = new ArrayList<>();
    int count = tickets.get(0).fields().size();
    for(int i=0; i<count ; i++) {
      fieldRanges.add(new Range(1000, 0));
    }
    for(Ticket ticket : tickets) {
      if (ticket.validate(ranges).isValid()) {
        for(int i=0; i<ticket.fields().size(); i++) {
          int value = ticket.fields().get(i);
          fieldRanges.get(i).adjust(value, value);
        }
      } 
    }
    return fieldRanges;
  }
  
  public class Ticket {
    List<Integer> fieldList = new ArrayList<>();
    boolean valid = false;
    int rate = 0;
    
    public List<Integer> fields() {
      return fieldList;
    }
    
    public boolean isValid() {
      return valid;
    }
    
    public int errorRate() {
      return rate;
    }
    
    public Ticket validate(List<Range> ranges) {
      boolean inRange = true;
      for (Integer n : fieldList) {
        for (Range range : ranges) {
          inRange = range.isIn(n);
          if (inRange) {
            break;
          } 
        }
        if (!inRange) {
          rate += n;
        }
      }
      if (rate > 0) {
        valid = false;
      } else {
        valid = true;
      }
      return this;
    }
    
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();
      for(Integer field : fieldList) {
        sb.append(field).append(',');
      }
      return sb.toString();
    }
  }
  
  public class Field {
    Range r1;
    Range r2;
    
    public Field(Range range1, Range range2) {
      r1 = range1;
      r2 = range2;
    }
    
    public List<Integer> nonMatchingFieldIndex(Ticket t) {
      List<Integer> result = new ArrayList<>();
      for(int index = 0; index < t.fields().size(); index++) {
        Integer n = t.fields().get(index);
        if(!r1.isIn(n) && !r2.isIn(n)) {
          result.add(index);
        }
      }
      return result;
    }
    
    @Override
    public String toString() {
      return r1.toString() + " or " + r2.toString();
    }
  }
  
  public class Range {
    int min;
    int max;
    
    public Range(int minimum, int maximum) {
      min = minimum;
      max = maximum;
    }
    
    public boolean isIn(int n) {
      return (n >= min) && (n <= max);
    }
    
    public void adjust(int minimum, int maximum) {
      if (minimum < min) {
        min = minimum;
      }
      if (maximum > max) {
        max = maximum;
      }
    }
    
    @Override
    public String toString() {
      return "[" + min + "-" + max + "]";
    }
  }
}