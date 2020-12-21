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

/*

 As you're walking to yet another connecting flight, you realize that one of the legs of your re-routed trip coming up is on a high-speed train. However, the train ticket you were given is in a language you don't understand. You should probably figure out what it says before you get to the train station after the next flight.

Unfortunately, you can't actually read the words on the ticket. You can, however, read the numbers, and so you figure out the fields these tickets must have and the valid ranges for values in those fields.

You collect the rules for ticket fields, the numbers on your ticket, and the numbers on other nearby tickets for the same train service (via the airport security cameras) together into a single document you can reference (your puzzle input).

The rules for ticket fields specify a list of fields that exist somewhere on the ticket and the valid ranges of values for each field. For example, a rule like class: 1-3 or 5-7 means that one of the fields in every ticket is named class and can be any value in the ranges 1-3 or 5-7 (inclusive, such that 3 and 5 are both valid in this field, but 4 is not).

Each ticket is represented by a single line of comma-separated values. 
The values are the numbers on the ticket in the order they appear; every ticket has the same format. 
For example, consider this ticket:

.--------------------------------------------------------.
| ????: 101    ?????: 102   ??????????: 103     ???: 104 |
|                                                        |
| ??: 301  ??: 302             ???????: 303      ??????? |
| ??: 401  ??: 402           ???? ????: 403    ????????? |
'--------------------------------------------------------'
Here, ? represents text in a language you don't understand. 
This ticket might be represented as 101,102,103,104,301,302,303,401,402,403; of course, the actual train tickets you're looking at are much more complicated. 
In any case, you've extracted just the numbers in such a way that the first number is always the same specific field, the second number is always a different specific field, and so on - you just don't know what each position actually means!

Start by determining which tickets are completely invalid; these are tickets that contain values which aren't valid for any field. Ignore your ticket for now.

For example, suppose you have the following notes:

class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12
It doesn't matter which position corresponds to which field; 
you can identify invalid nearby tickets by considering only whether tickets contain values that are not valid for any field. 
In this example, the values on the first nearby ticket are all valid for at least one field. 
This is not true of the other three nearby tickets: the values 4, 55, and 12 are are not valid for any field. 
Adding together all of the invalid values produces your ticket scanning error rate: 4 + 55 + 12 = 71.

Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?
 
*/