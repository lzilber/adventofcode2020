package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle17 {
  
  
  public int solvePart1(List<String> input) {
    Map<String, Cube> myPocketDim = createCubesFrom(input);
    for(int i=0; i<6; i++) {
      myPocketDim = oneCycle(myPocketDim);    
    }
    return myPocketDim.keySet().size();
  }
  
  public Map<String, Cube> createCubesFrom(List<String> input) {
    Map<String, Cube> results = new HashMap<>();
    int y=input.size()-1;
    for(String line : input) {
      char[] chars = line.toCharArray();
      for (int x = 0; x < chars.length; x++) {
        Cube c = new Cube(x, y, 0);
        if (chars[x] == '#') {
          c.setActive(true);
        }
        results.put(c.key(), c);
      }
      y--;
    }
    return results;
  }
  
  public Map<String, Cube>  oneCycle(Map<String, Cube> pocketDimension) {
    // Build dimension to check
    List<Cube> extended = buildExtendedList(pocketDimension);
        
    List<Cube> activated = new ArrayList<>();
    for (Cube cube : extended) {
      if (cube.nextState(getNeighbors(cube, pocketDimension))) {
        activated.add(cube);
      }
    }
    
    Map<String, Cube> newDimension = new HashMap<>();
    for(Cube c: activated) {
      c.setActive(true);
      newDimension.put(c.key(), c);    
    }
    return newDimension;
  }
  
  public List<Cube> buildExtendedList(Map<String, Cube> pocketDimension) {
    List<Cube> extended = new ArrayList<>();
    for (Cube entry : pocketDimension.values()) {
      if (!extended.contains(entry)) {
        extended.add(entry);
      }
      for (Cube neighbor : getNeighbors(entry, pocketDimension)) {
        if (!extended.contains(neighbor)) {
          extended.add(neighbor);
        }
      }
    }
    return extended;
  }
  
  
  List<Cube> getNeighbors(Cube cube, Map<String, Cube> pocketDim) {
    List<Cube> results = new ArrayList<>();
    // +1 or -1 on x, y , z
    for (int x=cube.getX()-1; x<=cube.getX()+1; x++) {      
      for (int y=cube.getY()-1; y<=cube.getY()+1; y++) {
        for (int z=cube.getZ()-1; z<=cube.getZ()+1; z++) {
          String key = makeKey(x, y, z);
          if (!key.equals(cube.key())) {
            Cube neighbor = pocketDim.getOrDefault(key, new Cube(x, y, z));
            results.add(neighbor);
          }
        }
      }
    }
    return results;
  }
  
  public static String makeKey(int x, int y, int z) {
    return x + ":" + y +":" + z;
  }

  public class Cube {
    final String id;
    final int x;
    final int y;
    final int z;
    boolean active = false;
    
    public Cube(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.id = makeKey(x, y, z);
    }
    
    public boolean nextState(List<Cube> neighbors) {
      long count = neighbors.stream().filter(Cube::isActive).count();
      if (this.active) {
        if (count < 2 || count > 3) {
          return false;
        }
      } else {
        if (count == 3) {
          return true;
        }
      }
      return this.active;
    }
    
    public int getX() {
      return this.x;
    }

    public int getY() {
      return this.y;
    }

    public int getZ() {
      return this.z;
    }

    public boolean isActive() {
      return active;
    }
    
    public void setActive(boolean b) {
      active = b;
    }
    
    public String key() {
      return id;
    }
    
    @Override
    public String toString() {
      return id + ":" + active;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (this==obj) {
        return true;
      }
      if (obj instanceof Cube) {
        Cube c = (Cube) obj;
        return id.equals(c.id);
      }
      return false;
    }
    
    @Override
    public int hashCode() {
      return id.hashCode();
    }
  }
}
