/*
 * @(#)IRequestHandler.java        2010/06/03
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

import com.company.application.domain.PokerChipDistributionResultList;
import com.company.application.exception.ParserException;

/**
 * Interface create abstraction to allow for different implementations of the
 * underlying request handler.
 * 
 * @author Desmond O'Leary
 * 
 */
public interface IRequestHandler {
	PokerChipDistributionResultList handleRequestFor(String input)
			throws ParserException;
}
