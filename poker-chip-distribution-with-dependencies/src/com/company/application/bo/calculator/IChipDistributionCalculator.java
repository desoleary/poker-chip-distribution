/*
 * @(#)IChipDistributionCalculator.java        2010/06/03
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
package com.company.application.bo.calculator;

import java.util.List;

import com.company.application.domain.IPokerChipDistribution;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.domain.PokerChipDistributionResultList;

/**
 * Interface create abstraction for all chip distribution calculator
 * implementations.
 * 
 * @author Desmond O'Leary
 * 
 */
public interface IChipDistributionCalculator<IInput> {
	PokerChipDistributionResultList calculate(final IInput input);
}
