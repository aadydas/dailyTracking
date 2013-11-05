package com.AndroidProject.dailyTracking.util;

public class Util {
	
	/**
	 * Sanitizes user input to prevent bad queries to the
	 * database.
	 * 
	 * @param input the user input
	 * @return the sanitized input
	 */
	public static String sanitizeInput(String input) {
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0 ; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c >= 48 && c <= 57) {
				sb.append(c);
			} else if (c >= 65 && c <= 90) {
				sb.append(c);
			} else if (c >= 97 && c <= 122) {
				sb.append(c);
			}
		}
		return sb.toString();
		
	}
	
}
