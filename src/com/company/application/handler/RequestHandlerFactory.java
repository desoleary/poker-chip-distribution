/*
 * @(#)RequestHandlerFactory.java        2010/06/03
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
package com.company.application.handler;

import com.company.application.bo.calculator.ChipDistributionCalculator;
import com.company.application.bo.calculator.ChipDistributionCalculatorForBonusOne;
import com.company.application.bo.calculator.ChipDistributionCalculatorForBonusTwo;
import com.company.application.parser.PockerChipDistributionParser;
import com.company.application.parser.PockerChipDistributionParserForBonusOne;
import com.company.application.parser.PockerChipDistributionParserForBonusTwo;

/**
 * Returns the appropriate request handler based on input value.
 * 
 * @author Desmond O'Leary
 * 
 */
public class RequestHandlerFactory {
	private static final String REQUEST_HANDLER_BONUS_ONE_IDENTIFIER = "B1";
	private static final String REQUEST_HANDLER_BONUS_TWO_IDENTIFIER = "B2";

	/**
	 * Returns the appropriate request handler based on input value.
	 * @param input request input
	 * @return request handler
	 * @throws IllegalArgumentException input is invalid.
	 */
	public static IRequestHandler createRequstHandlerFor(String input)
			throws IllegalArgumentException {
		if (input == null || input.equals("")) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter input must not be null or empty but was found as '%s'",
									input));
		}

		if (input.startsWith(REQUEST_HANDLER_BONUS_ONE_IDENTIFIER)) {
			return new RequestHandler(
					new PockerChipDistributionParserForBonusOne(),
					new ChipDistributionCalculatorForBonusOne());
		} else if (input.startsWith(REQUEST_HANDLER_BONUS_TWO_IDENTIFIER)) {
			return new RequestHandlerForBonusTwo(
					new PockerChipDistributionParserForBonusTwo(),
					new ChipDistributionCalculatorForBonusTwo());
		}
		return new RequestHandler(new PockerChipDistributionParser(),
				new ChipDistributionCalculator());
	}
}
