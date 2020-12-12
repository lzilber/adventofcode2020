package org.zebre.puzzle;

import java.util.List;

public class Puzzle12 {
  
  private Position boat = new Position(Direction.E);
  private Position waypoint = new Position(Direction.E);

  public int getManhattanDistance() {
    return Math.abs(boat.eastWest) + Math.abs(boat.northSouth);
  }
  
  public void initWaypoint(int e, int n) {
    waypoint.eastWest = e;
    waypoint.northSouth = n;
  }
  
  public Position runActions(List<String> input) {
    for (String action : input) {
      char what = action.charAt(0);
      int value = Integer.parseInt(action.substring(1));
      switch (what) {
        case 'N':
          // part 1: boat.move(Direction.N, value);
          waypoint.move(Direction.N, value);
          break;
        case 'S':
          waypoint.move(Direction.S, value);
          break;
        case 'E':
          waypoint.move(Direction.E, value);
          break;
        case 'W':
          waypoint.move(Direction.W, value);
          break;
        case 'L':
          // part 1: boat.turnLeft(value);
          waypoint.rotate(value, boat);
          break;
        case 'R':
          // part1 : boat.turnRight(value);
          waypoint.rotate(-value, boat);
          break;
        case 'F':
          // waypoint follows
          int x = waypoint.eastWest - boat.eastWest;
          int y = waypoint.northSouth - boat.northSouth;
          boat.translate(value, waypoint);
          waypoint.eastWest = boat.eastWest + x;
          waypoint.northSouth = boat.northSouth + y;
          break;
        default:
          break;
      }
    }
    return boat;
  }
  
  public class Position {
    
    int eastWest = 0;
    int northSouth = 0;
    Direction facing = Direction.E;
    
    public Position(Direction way) {
      facing = way;
    }
    
    public void translate(int value, Position waypoint) {
      int x = waypoint.eastWest - eastWest;
      int y = waypoint.northSouth - northSouth;
      for (int i=0; i<value; i++) {
        eastWest += x;
        northSouth += y;
      }
    }

    public Position move(Direction way, int value) {
      switch (way) {
        case N:
          northSouth += value;
          break;
        case S:
          northSouth -= value;
          break;
        case E:          
          eastWest += value;
          break;
        case W:
          eastWest -= value;
          break;
        default: 
          break;
      }
      return this;
    }
    
    /**
     * Rotates this position around the given center.
     * -90 = 270
     * @param value
     * @param center
     */
    public void rotate(int value, Position center) {
      int angle = value < 0 ? 360+value : value;
      // put center as origin
      int x = eastWest - center.eastWest;
      int y = northSouth - center.northSouth;
      int tmp;
      // rotate 
      // cos(90) = 0, sin(90) = 1 
      switch (angle) {
        case 90:
          tmp = -y;
          y = x;
          x = tmp;
          break;
        case 180:
          x = -x;
          y = -y;
          break;
        case 270:
          tmp = y;
          y = -x;
          x = tmp;
          break;
        default:
          break;
      }
      // translate back 
      eastWest = x + center.eastWest;
      northSouth = y + center.northSouth;
    }

    public Position turnLeft(int value) {
      int steps = value / 90;
      facing = facing.turn(false, steps);
      return this;
    }

    public Position turnRight(int value) {
      int steps = value / 90;
      facing = facing.turn(true, steps);
      return this;
    }
    
    public Position forward(int value) {
      move(facing, value);
      return this;      
    }
    
    @Override
    public String toString() {
      return "(e:" + eastWest + " n:" + northSouth + ")";
    }

  }
    
  public enum Direction {
    N, E, S, W;
    
    public Direction turn(boolean clockwise, int steps) {
      int index = Math.floorMod( ordinal()+(clockwise ? steps : -steps), 4);
      return values()[index];
    }
  }
}


