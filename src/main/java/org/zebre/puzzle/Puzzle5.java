package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Puzzle5 {
	
	private List<Long> seats = new ArrayList<>();
	private long maxSeatId = 0;
	private long minSeatId = 100;
	
	public long maxSeatId(List<String> input) {
		for (String encoded : input) {
			long seatId = computeSeatId(
					getRow(encoded.substring(0, 7)), 
					getCol(encoded.substring(7, encoded.length())));
			if (seatId > maxSeatId) {
				maxSeatId = seatId;
			}
			// added for part 2
			if (seatId < minSeatId) {
				minSeatId = seatId;
			}
			seats.add(seatId);
			//System.out.println("Added " + seatId);
		}
		return maxSeatId;
	}
	
	public long checkAvailableSeat() {
		for(long id=minSeatId; id < maxSeatId; id++) {
			if(!seats.contains(id)) {
				return id;
			}
		}
		return 0;
	}
	
	public long computeSeatId(int row, int col) {
		return ((long)row * 8) + (long)col; 
	}
	
	public int parseEncodedBinary(String encoded, char zero) {
		int result = 0;
		int powerOfTwo = 1;
		int slot = encoded.length() - 1; 
		while (slot >= 0 ) {
			result += powerOfTwo * (encoded.charAt(slot) == zero ? 0 : 1);
			powerOfTwo = powerOfTwo << 1;
			slot--;
		}
		return result;
	}
	
	public int getRow(String encoded) {
		return parseEncodedBinary(encoded, 'F');
	}

	public int getCol(String encoded) {
		return parseEncodedBinary(encoded, 'L');
	}

}
