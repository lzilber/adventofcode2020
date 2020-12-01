package org.zebre.puzzle;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Puzzle1 {

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
	
	public Integer multiply(AbstractMap.SimpleImmutableEntry<Integer, Integer> result) {
		return result.getKey() * result.getValue();
	}
	
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
	
	public Integer multiply(List<Integer> result) {
		return result.stream().reduce(1, (x, y) -> x * y);
	}
}
