package org.zebre.puzzle;

import java.util.List;

public class Puzzle3 {
	
	public int countTrees(List<String> input, int right, int down, boolean print) {
		int lineWidth = input.get(0).length();
		int x = 0; 
		int y = down;
		int trees = 0;
		while (y < input.size()) {
			x = x + right;
			if ( x >= lineWidth) {
				x = x - lineWidth;
			}
			if ( input.get(y).charAt(x) == '#' ) {
				trees++;
				if (print) {
					String debug = input.get(y).substring(0, x) + 'T' + input.get(y).substring(x);
					System.out.println(y+1 + ") " + debug);
				}
			}
			y = y + down;
		}
		if (print) {
			System.out.println("Completed with line width " + lineWidth + " and " + input.size() + " lines");
			System.out.println("Found " + trees + " trees");
		}
		return trees;
	}
}
