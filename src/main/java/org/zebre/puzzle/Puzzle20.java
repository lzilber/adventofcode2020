package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Puzzle20 {

  public String solvePart1(List<String> entries) {
    long result = 1;
    int corners = 0;
    int sided = 0;
    int invalid = 1;
    List<Tile> tiles = parseTiles(entries, 10);
    for (Tile tile : tiles) {
      tile.countMatchingTiles(tiles);
    }
    corners = 0;
    sided = 0;
    invalid = 0;
    for (Tile tile : tiles) {
      switch (tile.matches) {
        case 4:
          break;
        case 3:
          sided++;
          break;
        case 2:
          System.out.println(tile.id + " is a corner");
          result = result * tile.id;
          corners++;
          break;
        default:
          invalid++;
      }      
    }
    System.out.println("Invalid :" + invalid);
    System.out.println("Corners :" + corners);
    System.out.println("Border :" + sided);
    return ""+result;
  }
      
  public List<Tile> parseTiles(List<String> input, int tileSize) {
    List<Tile> results = new ArrayList<>();
    List<String> tileLines = new ArrayList<>();
    int i = 1;
    int cut = tileSize + 1; // tile size + title line
    for(String line: input) {
      if (!line.isBlank()) {
        tileLines.add(line);
        i++;
      }
      if (i>cut) {
        results.add(new Tile(tileLines));
        tileLines.clear();
        i = 1;
      } 
    }
    return results;
  }
  
  public class Tile {
    public int id;

    public Set<Integer> hashcodes = new HashSet<>();    
    public Map<Direction, Integer> sides = new EnumMap<>(Direction.class);
    public Map<Direction, Tile> neighbors = new EnumMap<>(Direction.class);
    public int matches;
            
    public int[] sidesAsArray() {
      return sides.values().stream().mapToInt(i -> i).toArray();
    }
    
    public Tile() {
      for(Direction d : Direction.values()) {
        sides.put(d, 0);
        neighbors.put(d, null);
      }
    }
    
    public Tile(List<String> tileLines) {
      super(); 
      id = Integer.valueOf(tileLines.get(0).substring(5, 9));

      sides.put( Direction.top, tileSideToHashcode(tileLines.get(1)));
      sides.put( Direction.bottom, tileSideToHashcode(tileLines.get(10)));
      
      StringBuilder leftSide = new StringBuilder();
      StringBuilder rightSide = new StringBuilder();
      for(int i=1; i<11; i++) {
        String line = tileLines.get(i);
        leftSide.append(line.charAt(0));
        rightSide.append(line.charAt(9));
      }
      sides.put( Direction.left, tileSideToHashcode(leftSide.toString()));
      sides.put( Direction.right, tileSideToHashcode(rightSide.toString()));
    
      hashcodes.addAll(sides.values());
      hashcodes.addAll(flipTopBottomSides().values());
      hashcodes.addAll(flipRightLeftSides().values());
    }
    
    public int tileSideToHashcode(String input) {
      String binary = input.replace(".", "0").replace("#", "1");
      return Integer.parseInt(binary, 2);
    }
    
    public Map<Direction, Integer> flipTopBottomSides() {
      Map<Direction, Integer> newSides = new EnumMap<>(Direction.class);
      newSides.put( Direction.top, sides.get(Direction.bottom));
      newSides.put( Direction.bottom, sides.get(Direction.top));
      newSides.put( Direction.left, flipBits(sides.get(Direction.left)));
      newSides.put( Direction.right, flipBits(sides.get(Direction.right)));
      return newSides;
    }

    public Map<Direction, Integer> flipRightLeftSides() {
      Map<Direction, Integer> newSides = new EnumMap<>(Direction.class);
      newSides.put( Direction.left, sides.get(Direction.right));
      newSides.put( Direction.right, sides.get(Direction.left));
      newSides.put( Direction.top, flipBits(sides.get(Direction.top)));
      newSides.put( Direction.bottom, flipBits(sides.get(Direction.bottom)));
      return newSides;
    }

    protected int flipBits(int i) {      
      String binary = Integer.toBinaryString(i);
      StringBuilder sb = new StringBuilder();
      for (i=binary.length(); i>0; i--) {
        sb.append(binary.charAt(i-1));
      }
      int paddingSize = 10 - binary.length();
      for (i=0; i<paddingSize; i++) {
        sb.append('0');
      }
      return Integer.parseInt(sb.toString(), 2);
    }
    
    public int countMatchingTiles(List<Tile> tiles) {
      matches = 0;
      for(Tile t : tiles) {
        if (t.equals(this)) {
          continue;
        }
        if (matchAnySide(t)) {
          matches++;
        }
      }
      return matches;
    }
    
    private boolean matchAnySide(Tile t) {
      for(Integer myside : hashcodes) {
        for(Integer theirside : t.hashcodes) {
          if (myside.equals(theirside)) {
            return true;
          }
        }
      }
      return false;
    }
    
    public int neighborhood() {
      int count = 0;
      count += neighbors.get(Direction.top) == null ? 0 : 1;
      count += neighbors.get(Direction.right) == null ? 0 : 1;
      count += neighbors.get(Direction.bottom) == null ? 0 : 1;
      count += neighbors.get(Direction.left) == null ? 0 : 1;
      return count;
    }
    
    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("id:");
      sb.append(id).append("[ ");
      for(int code : sides.values()) {
        sb.append(code).append(" ");
      }
      sb.append("]");
      return sb.toString();
    }
  }
  
  public enum Direction {
    top, right, bottom, left;
    
    static {
      top.miror = bottom;
      right.miror = left;
      bottom.miror = top;
      left.miror = right;
    }

    private Direction miror;

    public Direction opposite() {
        return miror;
    }

  }

}
