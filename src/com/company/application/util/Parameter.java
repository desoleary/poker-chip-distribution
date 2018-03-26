package com.company.application.util;

import java.util.regex.Pattern;

public class Parameter {
	/** verifies that the given value is numeric. */
	public static final Pattern IS_NUMERIC_REGEX = Pattern.compile("^+[0-9]$");

	/**
	 * verifies that the given value is a numeric monetary value with two
	 * decimal places.
	 */
	private static final Pattern HAS_VALID_NUMERIC_MONETARY_REGEX = Pattern
			.compile("^\\${1}\\d+(\\.(\\d{2}))?$");

	/**
	 * Checks if given value is numeric.
	 * 
	 * @param value 
	 * @return If given value is numeric returns true else false
	 */
	public static final boolean isIntegerFor(final String value) {
		try{
			return IS_NUMERIC_REGEX.matcher(value).matches();
		}catch(NullPointerException ex){
			return false;
		}		
	}

	/**
	 * Checks if given value is a numeric monetary value with two
	 * 
	 * @param value
	 * @return If given value is a valid numeric monetary value with two decimal
	 *         place returns true else false
	 */
	public static final boolean hasValidNumericMonetaryValueFor(
			final String value) {
		return HAS_VALID_NUMERIC_MONETARY_REGEX.matcher(value).matches();
	}
}
