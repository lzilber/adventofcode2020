package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle19.Rule;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Puzzle19Test {

  Puzzle19 puzzle;
  
  @Before
  public void setUp() {
    puzzle = new Puzzle19();
  }

  @Test
  public void testRuleConstructor() {
    Rule r = puzzle.new Rule("123", " \"a\"");
    assertEquals("123", r.id);
    assertEquals("a", r.regex());
    
    r = puzzle.new Rule("1", "4 1 5");
    assertNull(r.regex());
    assertEquals(3, r.first.length);
    assertEquals("1", r.first[1]);
    assertEquals(0, r.second.length);
    
    r = puzzle.new Rule("1", " 2 3 | 3 2");
    assertNull(r.regex());
    assertEquals(2, r.first.length);
    assertEquals("3", r.first[1]);
    assertEquals(2, r.second.length);
    assertEquals("3", r.second[0]);
  }
  
  @Test
  public void testRuleBuildRegularExpression() {
    Rule r = puzzle.new Rule("1", " 2 3 | 3 2");
    Map<String, Rule> rules = new HashMap<>();
    rules.put("2", puzzle.new Rule("2", " \"a\""));
    rules.put("3", puzzle.new Rule("3", " \"b\""));
    String regex = r.buildRegularExpression(rules);
    assertEquals("(ab|ba)", regex);
    
  }
  
  @Test
  public void testRuleMatch() {
    Rule r0 = puzzle.new Rule("0"," 4 1 5");
    Rule r1 = puzzle.new Rule("1"," 2 3 | 3 2");
    Rule r2 = puzzle.new Rule("2"," 4 4 | 5 5");
    Rule r3 = puzzle.new Rule("3"," 4 5 | 5 4");
    Rule r4 = puzzle.new Rule("4","\"a\"");
    Rule r5 = puzzle.new Rule("5"," \"b\"");  
    Map<String, Rule> rules = new HashMap<>();
    rules.put("1", r1);
    rules.put("2", r2);
    rules.put("3", r3);
    rules.put("4", r4);
    rules.put("5", r5);
    
    assertEquals("a((aa|bb)(ab|ba)|(ab|ba)(aa|bb))b", r0.buildRegularExpression(rules));
    assertTrue(r0.matchFor("ababbb"));
    assertFalse(r0.matchFor("bababa"));
    assertTrue(r0.matchFor("abbbab"));
    assertFalse(r0.matchFor("aaabbb"));
    assertFalse(r0.matchFor("aaaabbb"));
        
  }
}
