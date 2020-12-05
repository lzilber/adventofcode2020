package org.zebre.puzzle;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.zebre.puzzle.Puzzle4.Passport;

public class Puzzle4Test {

	final List<String> input = Arrays.asList(
			"ecl:gry pid:860033327 eyr:2020 hcl:#fffffd",
			"byr:1937 iyr:2017 cid:147 hgt:183cm",
			"\n",
			"iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884",
			"hcl:#cfa07d byr:1929",
			"\n",
			"hcl:#ae17e1 iyr:2013",
			"eyr:2024",
			"ecl:brn pid:760753108 byr:1931",
			"hgt:179cm",
			"\n",
			"hcl:#cfa07d eyr:2025 pid:166559648",
			"iyr:2011 ecl:brn hgt:59in"
			);
	
	@Test
	public void testCountValidPassports() {
		Puzzle4 puzzle = new Puzzle4();
		assertEquals(2, puzzle.countValidPassportsStep1(input));
	}

	@Test
	public void testExtractPassports() {
		Puzzle4 puzzle = new Puzzle4();
		List<Passport> list = puzzle.extractPassports(input);
		assertEquals(4, list.size());
	}

	@Test
	public void testColor() {
		assertTrue(Puzzle4.checkColor("#123abc"));
		assertFalse(Puzzle4.checkColor("123abc"));
		assertFalse(Puzzle4.checkColor("#123abz"));
		assertFalse(Puzzle4.checkColor("#123ab"));
		assertTrue(Puzzle4.checkColor("#000000"));
	}
}
