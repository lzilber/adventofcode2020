package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle14 {

  
  public long solvePart1(List<String> input) {
    List<MaskBloc> blocs = parseInput(input);
    Map<Integer, Long> memories = new HashMap<>();
    for(MaskBloc bloc : blocs) {
      Map<Integer, Long> result = memWrite(bloc);
      for(Integer key : result.keySet()) {
        memories.put(key,result.get(key));
      }
    }
    return memories.values().stream().mapToLong(Long::longValue).sum();
  }
  
  public List<MaskBloc> parseInput(List<String> input) {
    List<MaskBloc> blocs = new ArrayList<>();
    MaskBloc bloc = new MaskBloc(""); // prevent warnings
    for (String line : input) {
      if (line.startsWith("mask")) {
        bloc = new MaskBloc(line.split("=")[1].trim());
        blocs.add(bloc);
      } else {
        // ugly but I trust the format
        String[] numbers = line.substring(4).split("] = ");
        bloc.memories.put(Integer.parseInt(numbers[0]), Long.parseLong(numbers[1]));
      }
    }

    return blocs;
  }
  
  public Map<Integer, Long> memWrite(MaskBloc bloc) {
    Map<Integer, Long> result = new HashMap<>();
    for (Integer key : bloc.memories.keySet()) {
      Long value = bloc.memories.get(key);
      result.put(key, maskStep1(bloc.xMask, bloc.oneValue, value));
    }
    return result;
  }
  
  public long maskStep1(long xMask, long oneValue, long value) {
    return (xMask & value ) + oneValue;
  }
  
  public class MaskBloc {
    final String rawMask;
    public long xMask = 0;
    public long oneValue = 0;
    public final Map<Integer, Long> memories = new HashMap<>();
    
    public MaskBloc(String mask) {
      rawMask = mask;
      String reversed = new StringBuilder(mask).reverse().toString();
      long powerOf2 = 1;
      for (int c : reversed.chars().toArray()) {
        if (c == '1') {
          xMask += powerOf2;
          oneValue += powerOf2;
        }
        if (c == '0') {
          xMask += powerOf2;
        }
        powerOf2 = powerOf2 * 2;
      }
      xMask = ~xMask;
    }
  }
}
