/*
 * @(#)ChipDistributionCalculatorBonusTwo.java        2010/06/03
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.company.application.comparator.ChipColorAndQuantityComparator;
import com.company.application.domain.ChipColorAndQuantity;
import com.company.application.domain.PokerChipDistributionForBonusTwo;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.domain.PokerChipDistributionResultList;
import com.company.application.domain.ResponseType;

/**
 * Class calculates the optimum distribution of poker chips that maximizes the
 * amount of chips that each player receives.
 * 
 * @author Desmond O'Leary
 * 
 */
public class ChipDistributionCalculatorForBonusTwo extends
		ChipDistributionCalculatorBase implements
		IChipDistributionCalculator<PokerChipDistributionForBonusTwo> {
	/** normal currency values */
	public static final double[] availableCurrencies = { 0.01, 0.05, 0.10,
			0.25, 0.50, 1.00, 2.00, 5.00, 10.00, 20.00, 50.00, 100.00, 1000.00 };

	public final PokerChipDistributionResultList calculate(
			final PokerChipDistributionForBonusTwo input) {
		double buyInAmount = input.getBuyInAmount();
		List<Integer> maximumAvailableQuantities = extractQuantitiesFrom(input
				.getChipDetailsList(), input.getPlayerCount());
		Map<Double, Integer> optimalCurrencyAndQuantityRangeMap = findOptimalCurrencyAndQuantityRangeFrom(
				availableCurrencies, maximumAvailableQuantities, buyInAmount);
		Map<Double, Integer> currencyAndQuantityMatchingBuyInAmountMap = getCurrencyAndQuantitiesMatchingBuyInAmountForBonusTwo(
				optimalCurrencyAndQuantityRangeMap, buyInAmount);
		List<String> colors = fetchColorByQuantityInDescendingOrder(input
				.getChipDetailsList());
		return new PokerChipDistributionResultList(merge(
				currencyAndQuantityMatchingBuyInAmountMap, colors),
				ResponseType.BONUS_TWO);
	}

	/**
	 * Extracts all quantity amounts from input
	 * 
	 * @param chipDetailsList
	 *            collection of chip details with color and associated quantity.
	 * @return
	 */
	private List<Integer> extractQuantitiesFrom(
			final List<ChipColorAndQuantity> chipDetailsList,
			final int participantCount) {
		List<Integer> result = new ArrayList<Integer>();
		for (ChipColorAndQuantity chipDetails : chipDetailsList) {
			result.add(chipDetails.getQuantity() / participantCount);
		}
		return result;
	}

	/**
	 * Come up with a range of currencies and associated quantities that totaled
	 * together amount to greater than the buy in amount. This range will
	 * include currency and maximum quantities available. Note that the highest
	 * quantity will start with the lowest currency to ensure that the maximum
	 * amount of chips can be returned.
	 * 
	 * @param availableCurrencies
	 *            available currency values
	 * @param maximumAvailableQuantities
	 *            maximum available quantities
	 * @param buyInAmount
	 *            buy in amount
	 * @return map with key as the currency/denomination and value as associated
	 *         quantity
	 */
	private Map<Double, Integer> findOptimalCurrencyAndQuantityRangeFrom(
			final double[] availableCurrencies,
			final List<Integer> maximumAvailableQuantities,
			final double buyInAmount) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		for (int index = 0; index <= availableCurrencies.length
				- maximumAvailableQuantities.size(); index++) {
			List<Double> currencyRange = getCurrencyRangeFrom(
					availableCurrencies, availableCurrencies[index],
					maximumAvailableQuantities.size());
			double maximumAmount = maximumAmountForBonusTwoFrom(currencyRange,
					maximumAvailableQuantities);

			if (maximumAmount >= buyInAmount) {
				for (int index2 = 0; index2 < currencyRange.size(); index2++) {
					result.put(currencyRange.get(index2),
							maximumAvailableQuantities.get(index2));
				}
				return result;
			}
		}
		throw new IllegalArgumentException(String.format(
				"Unable to find currency range to match buy in amount '$%.2f'",
				buyInAmount));
	}

	/**
	 * fetch currency collection in the range of given input.
	 * 
	 * @param availableCurrencies
	 *            available currency values
	 * @param minimumAmount
	 *            currency amount that will act as the starting index
	 * @param quantity
	 *            denotes the range amount from the starting index (minimum
	 *            amount)
	 * @return list of currencies
	 */
	private List<Double> getCurrencyRangeFrom(
			final double[] availableCurrencies, final double minimumCurrency,
			final int quantity) {
		List<Double> result = new ArrayList<Double>();
		for (double currency : availableCurrencies) {
			if (currency >= minimumCurrency && result.size() != quantity) {
				result.add(currency);
			}
		}

		if (result.size() != quantity) {
			throw new IllegalArgumentException();
		}
		return result;
	}

	/**
	 * sorts colors by associated quantity in descending order and returns as
	 * list of colors.
	 * 
	 * @param chipDetailsList
	 *            list of chip details that includes color and quantity
	 * @return list of colors
	 */
	private List<String> fetchColorByQuantityInDescendingOrder(
			final List<ChipColorAndQuantity> chipDetailsList) {
		List<String> result = new ArrayList<String>();
		Collections.sort(chipDetailsList, new ChipColorAndQuantityComparator());
		for (ChipColorAndQuantity chipColorAndQuantity : chipDetailsList) {
			result.add(chipColorAndQuantity.getColor());
		}
		return result;
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
	private List<PokerChipDistributionResult> merge(
			final Map<Double, Integer> matchingCurrencyAndQuantityMap,
			final List<String> colors) {
		List<PokerChipDistributionResult> result = new ArrayList<PokerChipDistributionResult>();
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(matchingCurrencyAndQuantityMap);
		for (int index = 0; keys.hasNext(); index++) {
			String color = colors.get(index);
			double denomination = (Double) keys.next();
			int quantity = matchingCurrencyAndQuantityMap.get(denomination);
			result.add(new PokerChipDistributionResult(color, String.format(
					"$%.2f", denomination), quantity));
		}
		return result;
	}

	// TODO: will need to be removed going forward as error handling will be
	// done in a separate class.
	private boolean isSatisfiedBy(final PokerChipDistributionForBonusTwo input) {
		// TODO Add proper error handling here
		return true;
	}
}
