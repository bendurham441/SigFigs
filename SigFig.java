/**
 * SigFig.java
 * @author Ben Durham
 * @version 20180215
 * Provides functionality for Significant Figures in java. This class allows for conversion 
 * between number types and this type which has consideration for significant figures. This makes
 * use of an ArrayList of characters to hold the value of a number and conserve significant
 * figures. Allows for comparison and equivalence checking.
 */

import java.util.ArrayList;

// toDo
// ----------------------
// addition/subtraction
// multiplication/division
// validation
// negative functionality
// charat
// number of SigFigs

public class SigFig {
	// Reference array of all digits
	private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

	// Representation of the digits
	private final ArrayList<Character> digits;	

	/**
	 * Main constructor, taking in a string and adding each character to an arraylist in
	 * order to preserve trailing zeroes that would be dropped in the conversion to a double
	 * Also removes the insignificant leading zeroes
	 *
	 * @param num	A string representation of a number, for example "1.00"
	 * @see		removeLeadingZeroes
	 */
	public SigFig(String num) {
		// Create a new ArrayList the length of the string inputted
		digits = new ArrayList<Character>(num.length());

		// Add each character in the string to the ArrayList
		for (int i  = 0; i < num.length(); i++) {
			digits.add((char) num.charAt(i));
		}

		this.removeLeadingZeroes();
	}
	
	/**
	 * A less useful constructor to create a number considering significant digits from an
	 * integer
	 *
	 * @param num	the integer to make into a significant digit-considering number
	 * @see		SigFig
	 */
	public SigFig(int num) {
		this(Integer.toString(num));
	}

	/**
	 * A less useful constructor to create a number considering significant digits from a
	 * double
	 *
	 * @param num	the double to make into a significant digit-considering number
	 */
	public SigFig(double num) {
		this(Double.toString(num));
	}

	/**
	 * Removes leading (insignificant) zeroes from a number
	 */ 
	private void removeLeadingZeroes() {
		// Stores the index of the first significant digit
		int firstSigDigitIndex = 0;

		// Finds the index of the first significant digit
		for (int i = 0; i < digits.size(); i++) {
			if (digits.get(i) != '0') {
				firstSigDigitIndex = i;
				break;
			}
		}

		// Removes insignificant leading zeroes
		for (int i = 0; i < firstSigDigitIndex; i++) {
			digits.remove(0);
		}
		
		// If the previous loop removes the zero before a the decimal point, reinserts
		// a zero
		if (digits.get(0) == '.') {
			digits.add(0, '0');
		}
	}
	
	/**
	 * Checks if a given character represents a digit or a decimal point, as defined in DIGITS
	 *
	 * @param digit	the character to check
	 * @return	whether the character is a digit
	 */
	private static boolean isNumber(char digit) {
		// Flag to be changed if character is not a digit
		boolean isNum = false;
		for (int i = 0; i < DIGITS.length; i++) {
			// if the given character matches the digit in DIGITS
			if (digit == DIGITS[i] || digit == '.') {
				// return true
				return true;
			}
		}

		// If the character is not a digit, return false
		return false;
	}

	/**
	 * Converts a character to its corresponding integer digit
	 *
	 * @param c	the character to be converted
	 * @return	the corresponding integer
	 */ 
	private static int toDigit(char c) {
		// Loops through the DIGITS array and returns the corresponding digit
		for (int i = 0; i < DIGITS.length; i++) {
			if (c == DIGITS[i]) return i;
		}
		
		// Returns -1 if the number is not a digit
		return -1;
	}

	/**
	 * Converts the number to double
	 *
	 * @return	the number, represented as a double
	 * @see 	toDigit
	 */ 
	public double toDouble() {
		// Initializes variable to hold the position of the decimal point
		// Defaults to after the number, in the case that the number doesn't contain a
		// decimal point
		int decIndex = digits.size();

		// Finds the decimal point and update decIndex
		for (int i = 0; i < digits.size(); i++) {
			if (digits.get(i) == '.') decIndex = i;
		}

		// Holds the total to be returned later
		double total = 0;

		// Converts the digits ArrayList to a double by multiplying each digit by a power
		// of ten based on its position relative to the decimal point
		for (int i = 0; i < digits.size(); i++) {
			// Continues in the case that it encounters a decimal point
			if (i == decIndex) continue;
			total += toDigit(digits.get(i)) * Math.pow(10, decIndex - i);
		}

		// Returns the double representation of the number
		return total;
	}
	
	/**
	 * Converts the number to an integer
	 *
	 * @return	the number, represented as an integer
	 * @see		toDouble
	 */
	public int toInteger() {
		return (int) this.toDouble();
	}

	/**
	 * Checks to see if two SigFig instances are equal
	 *
	 * @param other	the other SigFig to compare it to
	 * @return	whether or not they are equivalent
	 */
	public boolean equals(SigFig other) {
		// Flag to hold whether or not the numbers are equivalent
		boolean isEqual = true;

		// Checks to see if the two SigFigs are of the same length
		if (this.digits.size() == other.digits.size()) {
			// checks to see if each corresponding digits are the same
			for (int i = 0; i < this.digits.size(); i++) {
				if (this.digits.get(i) != other.digits.get(i)) {
					isEqual = false;
					break;
				}
			}

			// Return whether or not the numbers digits are equivalents
			return isEqual;
		}

		// Return false if they are not of the same length
		return false;
	}

	/**
	 * Compares two SigFigs
	 *
	 * @param other	the other SigFig to compare it to
	 * @return	1 if this is greater, -1 if other is greater, 0 if equal
	 * @see		toDouble
	 */
	public int compareTo(SigFig other) {
		// Converts the SigFig to a double for easy comparison
		double other = other.toDouble();
		double current = this.toDouble();

		if (current > other) return 1;
		else if (current < other) return -1
		else return 0;
	}

	/**
	 * Converts the number to a string
	 *
	 * @return	the number, represented as a String
	 */
	public String toString() {
		// The string to be returned
		String result = "";

		// Append each chacacter from the digits ArrayList
		for (int i = 0; i < this.digits.size(); i++) {
			result += digits.get(i);
		}
		
		// Return the result
		return result;
	}
	
	/**
	 * Prints out the number
	 *
	 * @see		toString
	 */ 
	public void print() {
		// References toString()
		System.out.println(this.toString());
	}

	public static void main(String[] args) {
		// Testing isNumber()
		char testChar = 'a';
		System.out.println(isNumber(testChar));
		testChar = '0';
		System.out.println(isNumber(testChar));

		SigFig testFig = new SigFig("00.100");
		System.out.println(testFig.toDouble());
	}
}
