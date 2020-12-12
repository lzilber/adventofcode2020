package org.zebre.puzzle;

import java.util.Arrays;
import java.util.List;

public class Puzzle11 {

  public int[][] loadInput(List<String> input){
    int linesCount = input.size();
    int[][] seats = new int[linesCount][];
    for(int line=0; line < linesCount; line++) {
      int[] chars = input.get(line).chars().toArray();
      seats[line] = chars;
    }
    return seats;
  }
  
  public long countSeats(int match, int[][] seats) {
    return Arrays.stream(seats).flatMapToInt(Arrays::stream).filter(s -> s == match).count();
  }
  
  public boolean roundOfRules(int[][] seats) {
    int newSeats[][] = new int[seats.length][seats[0].length];
    boolean changed = false;
    for (int i = 0; i < seats.length; i++) {
      for (int j = 0; j < seats[i].length; j++) {
        if (matchSeatRules(i, j, seats)) {
          newSeats[i][j] = seats[i][j] == 'L' ? '#' : 'L';
          changed = true;
        } else {
          newSeats[i][j] = seats[i][j];
        }
      }
    }
    for (int i = 0; i < newSeats.length; i++) {
      seats[i] = Arrays.copyOf(newSeats[i], newSeats[i].length);
    }
    return changed;
  }
  
  public boolean matchSeatRules(int row, int col, int[][]seats) {
    switch (seats[row][col]) {
      case 'L':
        return (countOccupiedNext(row, col, seats) == 0);
      case '#':
        // part 1 : return (countOccupiedNext(row, col, seats) > 3);
        return (countOccupiedNext(row, col, seats) > 4);
      case '.':
      default:
        break;
    }
    return false;
  }
  
  public int countOccupiedNext(int row, int col, int[][]seats) {
    // part 1: 
    // int[] adjacents = getAdjacentSeats(row, col, seats);
    // part 2:
    int[] adjacents = getVisibleSeats(row, col, seats);
    int count = 0;
    for (int i = 0; i < adjacents.length; i++) {
      if (adjacents[i] == '#') {
        count++;
      }
    }
    return count;
  }
  
  public int[] getAdjacentSeats(int row, int col, int[][]seats) {
    int maxCol = seats[0].length - 1;
    int[] result = { '0', '0', '0', '0', '0', '0', '0', '0'};
    if (row > 0) {
      result[0] = col > 0 ? seats[row-1][col-1] : 0;
      //System.out.print(Character.toChars(result[0]));
      result[1] = seats[row-1][col];
      //System.out.print(Character.toChars(result[1]));
      result[2] = col < maxCol ? seats[row-1][col+1] : 0;
      //System.out.println(Character.toChars(result[2]));
    }
    result[3] = col > 0 ? seats[row][col-1] : 0;
    //System.out.print(Character.toChars(result[3]));
    result[4] = col < maxCol ? seats[row][col+1] : 0;
    //System.out.print("X");
    //System.out.println(Character.toChars(result[4]));
    if (row < seats.length - 1) {
      result[5] = col > 0 ? seats[row+1][col-1] : 0;
      //System.out.print(Character.toChars(result[5]));
      result[6] = seats[row+1][col];
      //System.out.print(Character.toChars(result[6]));
      result[7] = col < maxCol ? seats[row+1][col+1] : 0;
      //System.out.println(Character.toChars(result[7]));
    }
    return result;
  }

  public int[] getVisibleSeats(int row, int col, int[][]seats) {
    int maxCol = seats[0].length - 1;
    int[] result = { '0', '0', '0', '0', '0', '0', '0', '0'};
    if (row > 0) {
      result[0] = findVisibleSeat(-1, -1, row, col, seats);
      result[1] = findVisibleSeat(-1, 0, row, col, seats);
      result[2] = findVisibleSeat(-1, 1, row, col, seats);
    }
    result[3] = findVisibleSeat(0, -1, row, col, seats);
    result[4] = findVisibleSeat(0, 1, row, col, seats);
    if (row < seats.length - 1) {
      result[5] = findVisibleSeat(1, -1, row, col, seats);
      result[6] = findVisibleSeat(1, 0, row, col, seats);
      result[7] = findVisibleSeat(1, 1, row, col, seats);
    }
    return result;
  }
  
  public int findVisibleSeat(int stepRow, int stepCol, int row, int col, int[][]seats) {
    int maxCol = seats[0].length - 1;
    int maxRow = seats.length - 1;
    int i = row + stepRow;
    int j = col + stepCol;
    while( (i >= 0) && (i <= maxRow) && (j >= 0) && (j <= maxCol) ) {
      if (seats[i][j] != '.') {
        return seats[i][j];
      }
      i = i + stepRow;
      j = j + stepCol;
    }
    return '.';
  }
}
