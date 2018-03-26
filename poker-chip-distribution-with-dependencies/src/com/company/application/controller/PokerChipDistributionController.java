/*
 * @(#)PokerChipDistributionController.java        2010/06/03
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
package com.company.application.controller;

import com.company.application.domain.PokerChipDistributionResultList;
import com.company.application.exception.ParserException;
import com.company.application.handler.IRequestHandler;
import com.company.application.handler.RequestHandlerFactory;

/**
 * Class in the main entry point for the Poker Chip Distribution Calculator
 * application, responsible for delegating to the request handler factory to
 * return appropriate request handler based on input. Then delegates to handler
 * to handle request and return PokerChipDistributionResultList.
 * 
 * @author Desmond O'Leary
 * 
 */
public class PokerChipDistributionController {

	/**
	 * Delegates to request handler to return appropriate handler and then handles request.
	 * @param input request input
	 * @return result collection with each poker chip items with color,
	 *         denomination and quantity
	 * @throws IllegalArgumentException input contains invalid field parameters
	 * @throws ParserException error parsing input
	 */
	public final PokerChipDistributionResultList calculatePokerChipDistributionFor(
			final String input) throws IllegalArgumentException,
			ParserException {
		IRequestHandler handler = RequestHandlerFactory
				.createRequstHandlerFor(input);
		return handler.handleRequestFor(input);
	}
}
