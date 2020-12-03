package org.zebre.puzzle;

public class Puzzle2 {

	
	public boolean checkEntry(String value, boolean firstPart) {
		String[] splitted = value.split(":");
		Policy policy = new Policy(splitted[0]);
		String password = splitted[1].trim();
		if (firstPart) {
			return checkPassword(policy, password);			
		} else {
			return checkLetters(policy, password);
		}
	}
	
	/**
	 * Find if the policy matches in the 1st part.
	 * 
	 * What did I learn? Stream API on IntStream (a char is an int)
	 * @param policy
	 * @param password
	 * @return
	 */
	public boolean checkPassword(Policy policy, String password) {
		long count = password.chars().filter(ch -> ch == policy.letter).count();
		return (count >= policy.one && count <= policy.two);
	}
	
	/**
	 * Find if the policy matches in the 2nd part.
	 * 
	 * What did I learn? remembered xor!
	 * @param policy
	 * @param password
	 * @return
	 */
	public boolean checkLetters(Policy policy, String password) {	
		boolean foundOne = password.charAt((int)policy.one - 1) == policy.letter;
		boolean foundTwo = password.charAt((int)policy.two - 1) == policy.letter;
		return foundOne ^ foundTwo;
	}
	
	/**
	 * A Policy for puzzle #2.
	 * Initialised from a String like "{@code one-two letter}". 
	 * first part: "2-4 b" means "need at least 2 'b', maximum 4"
	 * second part: "2-4 b" means 'b' is either in position 2 or 4 (and not both)
	 * @author laurent
	 *
	 */
	public class Policy {
		static final String PATTERN = "\\W";
		
		final String raw;
		final long one;
		final long two;
		final char letter;
		
		public Policy(String value) {
			this.raw = value;
			String[] splits = value.split(PATTERN);
			this.one = Long.valueOf(splits[0]);
			this.two = Long.valueOf(splits[1]);
			this.letter = splits[2].charAt(0);
		}
	}
	
}
