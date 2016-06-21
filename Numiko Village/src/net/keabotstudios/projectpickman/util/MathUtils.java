package net.keabotstudios.projectpickman.util;

public class MathUtils {
	
	/**
	 * Adds leading 0's to a number.<p><b>Examples:</b><p>formatNumber(12, 5) returns "00012"<br>formatNumber(69, 2) returns "69"<br>formatNumber(500, 2) returns "00"
	 * @param n Number to format
	 * @param tl Total length of the output number
	 * @return The formatted string
	 */
	public static String formatNumber(int n, int tl) {
		return String.format("%0" + tl + "d", n);
	}
	
	/**
	 * Gets the amount of digits in a number.<p><b>Examples:</b><p>getNumDigits(8) returns 1<br>getNumDigits(555) returns 3<br>getNumDigits(1234) returns 4<br>getNumDigits("foo") returns a fucking error you dipshit
	 * @param n Number to get the digits of
	 * @return The amount of digits
	 */
	public static int getNumDigits(int n) {
		return String.valueOf(n).length();
	}
	
	/**
	 * 
	 * @param n
	 * @param min
	 * @param max
	 * @return
	 */
	public static int clamp(int n, int min, int max) {
		if (n > max)
			n = max;
		if (n < min)
			n = min;
		return n;
	}

}
