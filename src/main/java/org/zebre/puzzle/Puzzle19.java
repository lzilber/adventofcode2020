package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle19 {
  
  Map<String, Rule> bagOfRules = new HashMap<>();
  
  public String solvePart1(List<String> entries) {
    int result = 0;
    List<String> data = new ArrayList<>();
    boolean dataFound = false;
    for(String line: entries) {
      if (line.isBlank()) {
        dataFound = true;
      } else {
        if (dataFound) {
          data.add(line);
        } else {
          Rule r = parseRule(line);
          bagOfRules.put(r.id, r);
        }
      }
    }
    
    Rule r0 = bagOfRules.get("0");
    r0.buildRegularExpression(bagOfRules);
    result = matchrule(r0, data);
    return ""+result;
  }
  
  public Rule parseRule(String line) {
    String[] s = line.split(":");
    return new Rule(s[0].trim(), s[1].trim());
  }
  
  public int matchrule(Rule r, List<String> entries) {
    int count = 0;
    for (String line : entries) {
      if (r.matchFor(line)) {
        count++;
      }
    }
    return count;
  }


  public class Rule {
    public final String id;
    private String regularExpression;
    
    String[] first = {};
    String[] second = {};
    
    public Rule(String id, String content) {
      this.id = id;
      String c = content.trim();
      
      if (c.startsWith("\"")) {
        regularExpression = c.substring(1, 2);
      } else {
        String[] splits = c.split("\\|");
        first = parseRefs(splits[0]);
        if (splits.length > 1) {
          second = parseRefs(splits[1]);          
        }
      }
    }
    
    private String[] parseRefs(String rules) {
      return rules.trim().split(" ");
    }
    
    public String buildRegularExpression(Map<String, Rule> rules) {
      if (regularExpression == null) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < first.length; i++) {
          Rule r = rules.get(first[i]);
          sb.append(r.buildRegularExpression(rules));
        }
        if (second.length > 0) {
          String firstpart = sb.toString();
          sb = new StringBuilder("(");
          sb.append(firstpart);
          sb.append("|");
          for (int i = 0; i < second.length; i++) {
            Rule r = rules.get(second[i]);
            sb.append(r.buildRegularExpression(rules));
          }
          sb.append(")");
        }
        regularExpression = sb.toString();
      }
      return regularExpression;
    }
    
    public String regex() {
      return this.regularExpression;
    }
    
    public boolean matchFor(String input) {
      return input.matches(regularExpression);
    }
  }
}
