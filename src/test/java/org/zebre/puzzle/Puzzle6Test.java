package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle6Test {

  Puzzle6 objUnderTest;
  
  final List<String> input = Arrays.asList(
      "abc",
      "\n",
      "a",
      "b",
      "c",
      "\n",
      "ab",
      "ac",
"\n",
      "a",
      "a",
      "a",
      "a",
"\n", "b" );
  
  @Before
  public void setUp() throws Exception {
    objUnderTest = new Puzzle6();
  }
  
  @Test
  public void testExtractGroups() {
    List<List<String>> result = objUnderTest.extractGroups(input);
    assertEquals(5, result.size());
    assertEquals("abc", result.get(0).get(0));
  }

  @Test
  public void testCountGroupAnswers() {
    List<String> answers = Arrays.asList("abcx", "abcy", "abcz");
    assertEquals(6, objUnderTest.countGroupAnswers(answers));
  }

  @Test
  public void testAddAllGroupsAnswers() {
    assertEquals(11, objUnderTest.addAllGroupsAnswers(input));
  }

  @Test
  public void testCountCommonGroupsAnswers() {
    List<String> answers = Arrays.asList("abcx", "abcy", "abcz");
    assertEquals(3, objUnderTest.countCommonGroupAnswers(answers));
  }
  
  @Test
  public void testAddStep2GroupsAnswers() {
    assertEquals(6, objUnderTest.addStep2GroupsAnswers(input));
  }
}
