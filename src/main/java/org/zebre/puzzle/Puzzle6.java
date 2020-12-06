package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Puzzle6 {

  
  public List<List<String>> extractGroups(List<String> input) {
    List<List<String>> groups = new ArrayList<>();
    List<String> oneGroup = new ArrayList<>();
    for (String line : input) {
      if(line.isBlank()) {
        List<String> copy = new ArrayList<>();
        oneGroup.forEach(s -> copy.add(s));
        groups.add(copy);
        oneGroup.clear();
      } else {
        oneGroup.add(line);
      }
    }
    // add last group
    if (!oneGroup.isEmpty()) {
      List<String> copy = new ArrayList<>();
      oneGroup.forEach(s -> copy.add(s));
      groups.add(oneGroup);
    }
    return groups;
  }
  
  public int addAllGroupsAnswers(List<String> input) {
    List<List<String>> groups = extractGroups(input);
    int count = 0;
    for (List<String> group : groups) {
      count += countGroupAnswers(group);
    }
    return count;
  }
  
  public int countGroupAnswers(List<String> answers) {
    Set<Integer> uniqueAnswers = new HashSet<>();
    for (String answer : answers) {
      answer.chars().forEach(c -> uniqueAnswers.add(c));
    }
    return uniqueAnswers.size();
  }
  
  public int addStep2GroupsAnswers(List<String> input) {
    List<List<String>> groups = extractGroups(input);
    int count = 0;
    for (List<String> group : groups) {
      count += countCommonGroupAnswers(group);
    }
    return count;
  }
  
  public int countCommonGroupAnswers(List<String> answers) {
    int count = 0;
    int groupSize = answers.size();
    StringBuffer merged = new StringBuffer();
    answers.stream().forEach(s -> merged.append(s));
    for( int letter='a'; letter <= 'z'; letter++) {
      final int test = letter;
      int charOccurence = (int) merged.toString().chars().filter(c -> c == test).count();
      if (charOccurence == groupSize) {
        count ++;
      }
    }
    return count;
  }
}
