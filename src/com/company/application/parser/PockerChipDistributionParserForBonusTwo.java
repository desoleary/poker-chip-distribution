package com.company.application.parser;

import java.util.ArrayList;
import java.util.List;

import com.company.application.domain.ChipColorAndQuantity;
import com.company.application.domain.PokerChipDistributionForBonusTwo;
import com.company.application.exception.ParserException;

public class PockerChipDistributionParserForBonusTwo extends
		PockerChipDistributionParserBase implements
		IParser<String, PokerChipDistributionForBonusTwo> {
	private static final int MAXIMUM_NUMBER_OF_LINES = 4;
	private static final int CHIP_DETAILS_INDEX = 1;
	private static final int CHIP_DETAIL_PARAMETER_COUNT = 2;
	private static final int CHIP_QUANTITY_INDEX = 0;
	private static final int CHIP_COLOR_INDEX = 1;
	private static final int NUMBER_OF_PARTICIPANTS_INDEX = 2;
	private static final int BUY_IN_AMOUNT_INDEX = 3;
	private static final int DOLLAR_CHARACTER_OFFSET_INDEX = 1;

	public PokerChipDistributionForBonusTwo parse(String input)
			throws ParserException {
		
		if (input == null || input.equals("")) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter input must not be null or empty but as given as '%s'",
									input));
		}
		
		String[] chipDistributionDetailsList = NEW_LINE_REGEX.split(input);

		if (!(chipDistributionDetailsList.length >= MAXIMUM_NUMBER_OF_LINES)) {
			throw new ParserException(
					String
							.format(
									"Malformed input should have three sections separated by carriage return but was given as '%s'",
									input));
		}
		return new PokerChipDistributionForBonusTwo(
				fetchChipDetailsFrom(chipDistributionDetailsList[CHIP_DETAILS_INDEX]),
				Integer
						.parseInt(chipDistributionDetailsList[NUMBER_OF_PARTICIPANTS_INDEX]),
				convertMonetaryAmountToDouble(chipDistributionDetailsList[BUY_IN_AMOUNT_INDEX]));
	}

	/**
	 * Takes in input which should be in a specific format $denomination/$qty
	 * and split by the ',' character
	 * 
	 * @param input
	 * @return
	 */
	private List<ChipColorAndQuantity> fetchChipDetailsFrom(final String input)
			throws ParserException {
		List<ChipColorAndQuantity> result = new ArrayList<ChipColorAndQuantity>();
		String[] chipDetails = input.split(","); // make the denomination a
		// constant
		if (chipDetails.length < 1) {
			throw new ParserException(
					String
							.format(
									"No poker chip denomination and qty were found in required format, given input: '%s'",
									input));
		}

		for (String chipDetail : chipDetails) {
			String[] chipDetailItem = chipDetail.split("/");
			if (chipDetailItem.length < CHIP_DETAIL_PARAMETER_COUNT) {
				throw new ParserException(String.format(
						"Missing poker chip details from '%s'", chipDetail));
			}
			result.add(new ChipColorAndQuantity(
					chipDetailItem[CHIP_COLOR_INDEX], Integer
							.parseInt(chipDetailItem[CHIP_QUANTITY_INDEX])));
		}
		return result;
	}

	/**
	 * Removes '$' character and returns input string as double.
	 * 
	 * @param input
	 *            monetary value in the format of $##.##
	 * @return valid double with two decimal places
	 */
	private double convertMonetaryAmountToDouble(final String input) {
		return Double.parseDouble(input
				.substring(DOLLAR_CHARACTER_OFFSET_INDEX)); // removes the '$'
		// character
	}
}
