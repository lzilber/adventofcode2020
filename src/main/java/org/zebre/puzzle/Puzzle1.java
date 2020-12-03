package org.zebre.puzzle;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Puzzle1 {

	/**
	 * Check if a list has 2 values whose sum matches a number.
	 * 
	 * What did I learn? {@code AbstractMap.SimpleImmutableEntry} can be used to hold a tuple/pair.
	 * @param entries
	 * @param matchValue
	 * @return
	 */
	public AbstractMap.SimpleImmutableEntry<Integer, Integer> match2(
			List<Integer> entries, Integer matchValue) {
		for (int i=0; i< (entries.size()-1); i++) {
			for (int j=i+1; j<entries.size(); j++) {
				if (entries.get(i) + entries.get(j) == matchValue) {
					return new SimpleImmutableEntry<>(entries.get(i), entries.get(j));
				}
			}
		}
		return null;
	}
	
	/**
	 * Multiply the key and value of an {@code AbstractMap.SimpleImmutableEntry}.
	 * @param result
	 * @return
	 */
	public Integer multiply(AbstractMap.SimpleImmutableEntry<Integer, Integer> result) {
		return result.getKey() * result.getValue();
	}
	
	/**
	 * Check if a list has 3 values whose sum matches a number.
	 * 
	 * What did I learn? {@code Arrays.asList} is a quick way to build a List of something.
	 * @param entries
	 * @param matchValue
	 * @return
	 */
	public List<Integer> match3(List<Integer> entries, Integer matchValue) {
		for (int i=0; i< (entries.size()-2); i++) {
			for (int j=i+1; j<entries.size()-1; j++) {
				for (int k=j+1; k<entries.size(); k++) {
					if (entries.get(i) + entries.get(j) + entries.get(k) == matchValue) {
						return Arrays.asList(
								entries.get(i),
								entries.get(j),
								entries.get(k));
					}
				}
			}
		}
		return Collections.emptyList();
	}
	
	/**
	 * Multiply the numbers in a List.
	 *
	 * What did I learn? use Collection and Stream API, and {@code reduce()} terminal operation. 
	 * Simpler than a for loop, but takes some learning.
	 * @param 
	 * @return 
	 */
	public Integer multiply(List<Integer> result) {
		return result.stream().reduce(1, (x, y) -> x * y);
	}
}
