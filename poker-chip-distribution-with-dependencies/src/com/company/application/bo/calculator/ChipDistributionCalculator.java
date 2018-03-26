/*
 * @(#)ChipDistributionCalculator.java        2010/06/03
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.company.application.domain.PokerChipDistribution;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.domain.PokerChipDistributionResultList;
import com.company.application.domain.ResponseType;

/**
 * Class calculates the optimum distribution of poker chips that maximizes the
 * amount of chips that each player receives
 * 
 * @author Desmond O'Leary
 * 
 */
public class ChipDistributionCalculator extends ChipDistributionCalculatorBase
		implements IChipDistributionCalculator<PokerChipDistribution> {

	public final PokerChipDistributionResultList calculate(
			final PokerChipDistribution input) {
		
		Map<Double, Integer> currencyAndQuantityMap = convertToMap(input
				.getChipDetailsList(), input.getPlayerCount());		
//		Iterator<Double> iter = optimalCurrencyAndQuantityRangeMap.keySet().iterator();
//		while(iter.hasNext()){
//			double key = iter.next();
//			int value = optimalCurrencyAndQuantityRangeMap.get(key);
//			System.out.println("key: " + key + ", value: " + value);
//		}		
		Map<Double, Integer> currencyAndQuantityMatchingBuyInAmountMap = getCurrencyAndQuantitiesMatchingBuyInAmount(
				currencyAndQuantityMap, input.getBuyInAmount());
		return merge(currencyAndQuantityMatchingBuyInAmountMap);
	}

	/**
	 * Merges denomination and quantity collection with colors list
	 * 
	 * @param matchingCurrencyAndQuantityMap
	 *            map with key as the currency/denomination and value as
	 *            associated quantity that matches buy in amount
	 * @param colors
	 *            list of colors
	 * @return result collection with each poker chip items with color,
	 *         denomination and quantity
	 */
	private PokerChipDistributionResultList merge(
			final Map<Double, Integer> matchingCurrencyAndQuantityMap) {
		List<PokerChipDistributionResult> result = new ArrayList<PokerChipDistributionResult>();
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(matchingCurrencyAndQuantityMap);
		while (keys.hasNext()) {
			double denomination = (Double) keys.next();
			int quantity = matchingCurrencyAndQuantityMap.get(denomination);
			result.add(new PokerChipDistributionResult(null, String.format(
					"$%.2f", denomination), quantity));
		}
		return new PokerChipDistributionResultList(result, ResponseType.DEFAULT);
	}
}
