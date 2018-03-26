package com.company.application.util;


public class Utility {
	private static final int DOLLAR_CHARACTER_OFFSET_INDEX = 1;
	
	/**
	 * rounds decimal value to two decimal places.
	 * 
	 * @param val
	 *            double value
	 * @param numberOfDecimalPlaces
	 *            number of decimal places
	 * @return double with two decimal places
	 */
	public static double roundToDecimals(final double val,
			final int numberOfDecimalPlaces) {
		double p = (double) Math.pow(10, numberOfDecimalPlaces);
		double tmp = Math.round(val * p);
		return (double) tmp / p;
	}

	/**
	 * converts string to decimal rounds decimal value to two decimal places.
	 * 
	 * @param val
	 *            double value
	 * @param numberOfDecimalPlaces
	 *            number of decimal places
	 * @return double with two decimal places
	 */
	public static double roundToDecimals(final String val,
			final int numberOfDecimalPlaces) throws NumberFormatException {
		return roundToDecimals(Double.parseDouble(val), numberOfDecimalPlaces);
	}
	
	/**
	 * Removes '$' character and returns input string as double.
	 * 
	 * @param input
	 *            monetary value in the format of $##.##
	 * @return valid double with two decimal places
	 */
	public static double convertMonetaryAmountToDouble(final String input, final int numberOfDecimalPlaces) {
		return roundToDecimals(input
				.substring(DOLLAR_CHARACTER_OFFSET_INDEX), numberOfDecimalPlaces); // removes the '$'
		// character
	}
}
