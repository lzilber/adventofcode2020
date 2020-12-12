package org.zebre.puzzle;

import org.junit.Before;
import org.junit.Test;
import org.zebre.puzzle.Puzzle12.Position;
import org.zebre.puzzle.Puzzle12.Direction;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Puzzle12Test {
  
  Puzzle12 puzzle;

  @Before
  public void setUp() throws Exception {
    puzzle = new Puzzle12();
  }

  @Test
  public void testGetManhattanDistance() {
    List<String> input = Arrays.asList("F10",
        "N3",
        "F7",
        "R90",
        "F11");
    puzzle.initWaypoint(10, 1);
    Position pos = puzzle.runActions(input);
    assertEquals(286, puzzle.getManhattanDistance());
  }

  @Test
  public void testRunActions() {
    List<String> input = Arrays.asList("F10",
        "N3",
        "F7",
        "R90",
        "F11");
    puzzle.initWaypoint(10, 1);
    Position pos = puzzle.runActions(input);
    assertEquals(Direction.E, pos.facing);
    assertEquals(214, pos.eastWest);
    assertEquals(-72, pos.northSouth);
  }
  
  @Test 
  public void testWaypointRotate() {
    Position ship = puzzle.new Position(Direction.N);
    ship.eastWest = 170;
    ship.northSouth = 38;
    Position waypoint = puzzle.new Position(Direction.N);
    waypoint.eastWest = 180;
    waypoint.northSouth = 42;
    waypoint.rotate(-90, ship);
    assertEquals(174, waypoint.eastWest);
    assertEquals(28, waypoint.northSouth);
    waypoint.eastWest = 180;
    waypoint.northSouth = 42;
    waypoint.rotate(270, ship);
    assertEquals(174, waypoint.eastWest);
    assertEquals(28, waypoint.northSouth);
    
    ship.eastWest = 10;
    ship.northSouth = 5;
    waypoint.eastWest = 18;
    waypoint.northSouth = 7;
    waypoint.rotate(90, ship);
    assertEquals(8, waypoint.eastWest);
    assertEquals(13, waypoint.northSouth);  
    waypoint.eastWest = 18;
    waypoint.northSouth = 7;
    waypoint.rotate(-270, ship);
    assertEquals(8, waypoint.eastWest);
    assertEquals(13, waypoint.northSouth); 
    
    ship.eastWest = -10;
    ship.northSouth = 5;
    waypoint.eastWest = -8;
    waypoint.northSouth = 7;
    waypoint.rotate(270, ship);
    assertEquals(-8, waypoint.eastWest);
    assertEquals(3, waypoint.northSouth);  
     
    waypoint.eastWest = -8;
    waypoint.northSouth = 7;
    waypoint.rotate(180, ship);
    assertEquals(-12, waypoint.eastWest);
    assertEquals(3, waypoint.northSouth);
  }

  
  @Test
  public void testBoatTurnLeft() {
    Position pos = puzzle.new Position(Direction.N);
    assertEquals(Direction.W, pos.turnLeft(90).facing);
    assertEquals(Direction.S, pos.turnRight(270).facing);
  }
  
  @Test
  public void testDirectionTurn() {
    Direction d = Direction.E;
    assertEquals(Direction.N, d.turn(false, 1));
    assertEquals(Direction.W, d.turn(true, 2));
    assertEquals(Direction.N, d.turn(true, 3));
    assertEquals(Direction.N, d.turn(false, 1));
    d = Direction.N;
    assertEquals(Direction.W, d.turn(false, 1));
  }

}
