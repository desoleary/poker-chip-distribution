/*
 * @(#)RequestHandlerForBonusTwo.java        2010/06/03
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

import com.company.application.bo.calculator.IChipDistributionCalculator;
import com.company.application.domain.PokerChipDistributionForBonusTwo;
import com.company.application.domain.PokerChipDistributionResultList;
import com.company.application.exception.ParserException;
import com.company.application.parser.IParser;

/**
 * Parser input and delegates to chip distribution calculator to calculate the
 * optimum distribution of chips.
 * 
 * @author Desmond O'Leary
 * 
 */
public class RequestHandlerForBonusTwo implements IRequestHandler {
	private IParser<String, PokerChipDistributionForBonusTwo> parser;
	private IChipDistributionCalculator<PokerChipDistributionForBonusTwo> calculator;

	public RequestHandlerForBonusTwo(
			final IParser<String, PokerChipDistributionForBonusTwo> parser,
			final IChipDistributionCalculator<PokerChipDistributionForBonusTwo> calculator) {
		this.parser = parser;
		this.calculator = calculator;
	}

	/**
	 * Parser input and delegates to chip distribution calculator to calculate
	 * the optimum distribution of chips.
	 * 
	 * @param input
	 *            request input
	 * @return collection of poker chip distribution results contain chip color,
	 *         denomination and quantity.
	 */
	public final PokerChipDistributionResultList handleRequestFor(
			final String input) throws ParserException {
		return calculator.calculate(parser.parse(input));
	}

}
