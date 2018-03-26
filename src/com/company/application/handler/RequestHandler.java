/*
 * @(#)RequestHandler.java        2010/06/03
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
import com.company.application.domain.PokerChipDistribution;
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
public class RequestHandler implements IRequestHandler {
	private IParser<String, PokerChipDistribution> parser;
	private IChipDistributionCalculator<PokerChipDistribution> calculator;

	public RequestHandler(IParser<String, PokerChipDistribution> parser,
			IChipDistributionCalculator<PokerChipDistribution> calculator) {
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
	public PokerChipDistributionResultList handleRequestFor(String input)
			throws ParserException {
		return calculator.calculate(parser.parse(input));
	}
}
