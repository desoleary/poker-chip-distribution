/*
 * @(#)PockerChipDistributionParser.java        2010/06/03
 *
 * Copyright (c) 2010 XXXXXX
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of XXXXXXXX
 * ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with XXXXXX.
 */
package com.company.application.parser;

import java.util.ArrayList;
import java.util.List;

import com.company.application.domain.ChipDenominationAndQuantity;
import com.company.application.domain.PokerChipDistribution;
import com.company.application.exception.ParserException;
import com.company.application.util.Utility;

/**
 * Parses input string into domain model representation
 * 
 * @author Desmond O'Leary
 * 
 */
public class PockerChipDistributionParser extends
		PockerChipDistributionParserBase implements
		IParser<String, PokerChipDistribution> {
	private static final int CHIP_DETAILS_INDEX = 0;
	private static final int NUMBER_OF_PARTICIPANTS_INDEX = 1;
	private static final int BUY_IN_AMOUNT_INDEX = 2;
	private static final int CHIP_DETAIL_PARAMETER_COUNT = 2;
	private static final int CHIP_QUANTITY_INDEX = 0;
	private static final int CHIP_DENOMINATION_INDEX = 1;
	private static final int MAXIMUM_NUMBER_OF_LINES = 3;
	private static final int NUMBER_OF_DECIMAL_PLACES = 2;

	public final PokerChipDistribution parse(final String input)
			throws ParserException {
		if (input == null || input.equals("")) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter input must not be null or empty but as given as '%s'",
									input));
		}
		String[] chipDistributionDetailsList = NEW_LINE_REGEX.split(input);
		if (chipDistributionDetailsList.length != MAXIMUM_NUMBER_OF_LINES) {
			throw new ParserException(
					String
							.format(
									"Malformed input should have three sections separated by carriage return but was given as '%s'",
									input));
		}

		List<ChipDenominationAndQuantity> chipDetailsList = fetchChipDetailsFrom(chipDistributionDetailsList[CHIP_DETAILS_INDEX]);

		int numberOfParticipants = Integer
				.parseInt(chipDistributionDetailsList[NUMBER_OF_PARTICIPANTS_INDEX]);
		double buyInAmount = Utility.convertMonetaryAmountToDouble(
				chipDistributionDetailsList[BUY_IN_AMOUNT_INDEX],
				NUMBER_OF_DECIMAL_PLACES);
		return new PokerChipDistribution(chipDetailsList, numberOfParticipants,
				buyInAmount);
	}

	/**
	 * Takes in input which should be in a specific format
	 * $denomination/$quantity and split by the ',' character.
	 * 
	 * @param input
	 * @return
	 */
	private List<ChipDenominationAndQuantity> fetchChipDetailsFrom(
			final String input) throws ParserException {
		List<ChipDenominationAndQuantity> result = new ArrayList<ChipDenominationAndQuantity>();
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
			result.add(new ChipDenominationAndQuantity(Utility
					.convertMonetaryAmountToDouble(
							chipDetailItem[CHIP_DENOMINATION_INDEX],
							NUMBER_OF_DECIMAL_PLACES), Integer
					.parseInt(chipDetailItem[CHIP_QUANTITY_INDEX])));

		}
		return result;
	}
}
