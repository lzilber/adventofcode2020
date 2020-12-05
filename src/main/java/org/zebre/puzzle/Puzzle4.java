package org.zebre.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle4 {

	public long countValidPassportsStep1(List<String> input) {
		List<Passport> passports = extractPassports(input);
		return passports.stream().filter(Passport::checkAllButCountry).count();
	}
	
	public long countValidPassportsStep2(List<String> input) {
		List<Passport> passports = extractPassports(input);
		return passports.stream().filter(Passport::check).count();
	}
	
	public List<Passport> extractPassports (List<String> input) {
		List<Passport> passports = new ArrayList<>();
		Map<String, String> pairs = new HashMap<>();
		for (String line : input) {
			if (line.isBlank()) {
				// build Passport
				passports.add(new Passport(pairs));
				pairs.clear();
			} else {
				for(String pair: line.split(" ")) {
					pairs.put(pair.split(":")[0], pair.split(":")[1]);
				}
			}
		}
		// add last passport
		if (!pairs.isEmpty()) {
			passports.add(new Passport(pairs));
		}
		return passports;
	}
	
	public static boolean checkColor(String value) {
		return value.matches("#[0-9a-f]{6}");
	}
	
	public static boolean checkInteger(String value, int min, int max) {
		int val = Integer.parseInt(value);
		return (val >= min && val <= max);
	}
	
	public static final List<String> eyeValues = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
	public static boolean checkEye(String value) {
		return eyeValues.contains(value);
	}
	
	public static boolean checkNumber(String value) {
		if (value.length() == 9) {
			try {
				Long.parseLong(value);
				return true;
			} catch (NumberFormatException e) {
				// Not a number
			}
		}
		return false;
	}
	
	/*
	 * If cm, the number must be at least 150 and at most 193.
	 * If in, the number must be at least 59 and at most 76.
	 */
	public static boolean checkHeight(String value) {
		if(value.contains("cm")) {
			return checkInteger(value.split("cm")[0], 150, 193);
		} else if (value.contains("in")) {
			return checkInteger(value.split("in")[0], 59, 76);				
		}
		return false;
	}
	
	public class Passport {
		static final String birthYear = "byr";
		static final String issueYear = "iyr";
		static final String expirYear = "eyr";
		static final String height = "hgt";
		static final String hairColor = "hcl";
		static final String eyeColor = "ecl";
		static final String passportId = "pid";
		static final String countryId = "cid";
		
		final Map<String, String> fields = new HashMap<>();
		
		public Passport(Map<String, String> values) {
			for (Map.Entry<String, String> entry : values.entrySet()) {
				fields.put(entry.getKey(), entry.getValue());
			}		
		}
		
		public boolean check() {
			if (checkAllButCountry()) {
				return checkInteger(fields.get(birthYear), 1920, 2002) &&
						checkInteger(fields.get(issueYear), 2010, 2020) &&
						checkInteger(fields.get(expirYear), 2020, 2030) &&
						checkHeight(fields.get(height)) &&
						checkColor(fields.get(hairColor)) &&
						checkEye(fields.get(eyeColor)) &&
						checkNumber(fields.get(passportId));
			}
			return false;
		}
		
		public boolean isPassport() {
			return (!emptyField(countryId) && checkAllButCountry());
		}
		
		public boolean isNorthPole() {
			return (emptyField(countryId) && checkAllButCountry());
		}
		
		public boolean checkAllButCountry() {
			return !(emptyField(birthYear) ||
					emptyField(issueYear) ||
					emptyField(expirYear) ||
					emptyField(height) ||
					emptyField(hairColor) ||
					emptyField(eyeColor) ||
					emptyField(passportId));
		}
		
		/**
		 * Return true if field is missing or empty
		 * @param key
		 * @return
		 */
		private boolean emptyField(String key) {
			if(fields.containsKey(key) && (fields.get(key) != null)) {
				return fields.get(key).isBlank();
			}
			//System.out.println("DEBUG key not found or value is null for " + key);
			return true;
		}
	}
}
