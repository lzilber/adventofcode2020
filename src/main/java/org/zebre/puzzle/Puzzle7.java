package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle7 {
  
  private Map<String, Bag> mapOfBags = new HashMap<>();

  public int countBagOptionsFor(List<String> rules, String tag) {
    List<Bag> bags = parseBags(rules);
    return containsBag(bags, tag);
  }
  
  public int countBagInside(List<String> rules, String tag) {
    if (mapOfBags.isEmpty()) {
      parseBags(rules);
    }
    Bag bag = mapOfBags.get(tag);
    return bag.countBags();
  }
    
  public List<Bag> parseBags(List<String> rules) {
    List<Bag> bags = new ArrayList<>();
    for (String rule : rules) {
      String[] splitted = rule.split(" bags contain");
      Bag bagReference = getOrCreate(splitted[0], mapOfBags);
      if (!splitted[1].startsWith(" no other bags")) {
        String[] content = splitted[1].split(",");
        for (String countAndBag : content) {
          String[] parsed = countAndBag.trim().split(" ");
          String bagname = parsed[1] + " " + parsed[2];
          Bag b = getOrCreate(bagname, mapOfBags);
          bagReference.addContent(b, Integer.parseInt(parsed[0]));
        }
      }
    }
    for (Bag bag : mapOfBags.values()) {
      bags.add(bag);
    }
    return bags;
  }
  
  private Bag getOrCreate(String key, Map<String, Bag> mapOfBags) {
    if (mapOfBags.containsKey(key)) {
      return  mapOfBags.get(key);
    } else {
      Bag newBag = new Bag(key);
      mapOfBags.put(key, newBag);
      return newBag;
    }
  }
   
  private int containsBag(List<Bag> bags, String tag) {
    int total = 0;
    for (Bag b : bags) {
        total += b.containsBag(tag) ? 1 : 0;
    }
    return total;
  }
  
  public class Bag implements Comparable<Bag> {
    private final String tag;
    private Map<Bag, Integer> content = new HashMap<>();
    
    public Bag(String key) {
      tag = key;
    }
    
    public String getTag() {
      return tag;
    }
    
    public Map<Bag, Integer> getContent() {
      return content;
    }
    
    public void addContent(Bag bag, int count) {
      content.put(bag, count);
    }
    
    public boolean containsBag(String tag) {
      if (this.tag.equals(tag)) {
        return false; 
      }
      for (Bag bag : content.keySet()) {
        if (bag.getTag().equals(tag) || bag.containsBag(tag)) {
            return true;
        }
      }
      return false;
    }
    
    public int countBags() {
      int count = 0;
      for (Bag bag : content.keySet()) {
        count += content.get(bag) * (1 + bag.countBags()); 
      }
      return count;
    }

    @Override
    public int compareTo(Bag o) {
      return this.tag.compareTo(o.tag);
    }
    
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Bag) {
        return this.tag.equals(((Bag)obj).getTag());
      }
      return false;
    }
    
    @Override
    public int hashCode() {
      return this.tag.hashCode();
    }
  }
  
}
