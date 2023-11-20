package FalseInputsMitigator;

/* 
 * This class works to check all inputs are valid
 * A valid input is an input that is a combination of letters and numbers.
 * 
 */
public class FalseInputsChecker {
	public static boolean checkIfValidInput(String str, int condition) {
		if (str.length() == 0) {
			return false;
		}
		else if (condition == 0) {
			return str.matches("[A-Za-z0-9 ]*");
		}
		else if (condition == 1) {
			return str.matches("[A-Za-z0-9.@#*& ]*");
		}
		else if (condition == 2) {
			return str.matches("[A-Za-z0-9]*");
		}
		return false;
	}
	
}
