/*
 * @(#)ChipDistributionCalculatorForBonusOne.java        2010/06/03
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.company.application.domain.ChipDenominationAndQuantity;
import com.company.application.domain.PokerChipDistribution;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.domain.PokerChipDistributionResultList;
import com.company.application.domain.ResponseType;
import com.company.application.util.Utility;

/**
 * Class calculates the optimum distribution of poker chips that maximizes the
 * amount of chips that each player receives while ensuring that each player
 * receives at least one of each denomination.
 * 
 * @author Desmond O'Leary
 * 
 */
public class ChipDistributionCalculatorForBonusOne extends
		ChipDistributionCalculatorBase implements
		IChipDistributionCalculator<PokerChipDistribution> {
	private static final int NUMBER_OF_DECIMAL_PLACES = 2;
	private static final int MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT = 1;

	public PokerChipDistributionResultList calculate(PokerChipDistribution input) {
		Map<Double, Integer> currencyAndQuantityMap = convertToMap(input
				.getChipDetailsList(), input.getPlayerCount());
		Map<Double, Integer> currencyAndQuantityMatchingBuyInAmountMap = getCurrencyAndQuantitiesMatchingBuyInAmount(
				currencyAndQuantityMap, input.getBuyInAmount());
		Map<Double, Integer> mandatoryChipDetailsMap = retrieveMandatoryChipsOfAllDenominations(input
				.getChipDetailsList());
		throwIllegalArugmentExceptionIfbuyInAmountIsLessThan(
				mandatoryChipDetailsMap, input.getBuyInAmount());
		Map<Double, Integer> missingDenominationsMap = retriveMissingDenominationsUsing(
				currencyAndQuantityMatchingBuyInAmountMap,
				mandatoryChipDetailsMap);
		Map<Double, Integer> extraDenominationsMap = retrieveExtraDenominationsFrom(
				currencyAndQuantityMatchingBuyInAmountMap,
				mandatoryChipDetailsMap);
		Map<Double, Integer> revisedChipDistributionMap = retrieveRevisedChipDistributionMap(
				missingDenominationsMap, extraDenominationsMap);
		List<PokerChipDistributionResult> mergedResult = merge(
				mandatoryChipDetailsMap, revisedChipDistributionMap);
		return new PokerChipDistributionResultList(mergedResult,
				ResponseType.BONUS_ONE);
	}

	private void throwIllegalArugmentExceptionIfbuyInAmountIsLessThan(
			Map<Double, Integer> mandatoryChipDetailsMap, double buyInAmount)
			throws IllegalArgumentException {
		double totalMandatoryAmount = 0.00;
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(mandatoryChipDetailsMap);
		while (keys.hasNext()) {
			double denomination = (Double) keys.next();
			int quantity = mandatoryChipDetailsMap.get(denomination);
			totalMandatoryAmount += denomination * quantity;
		}

		if (buyInAmount < totalMandatoryAmount) {
			throw new IllegalArgumentException(
					String
							.format(
									"buy in amount must be greater or equal to '$.2%f' but was received as '$%.2f'",
									totalMandatoryAmount, buyInAmount));
		}
	}

	/**
	 * Generate a list of chip details of all denominations and mandatory
	 * quantity
	 * 
	 * @param chipDetailsList
	 * @return
	 */
	private Map<Double, Integer> retrieveMandatoryChipsOfAllDenominations(
			List<ChipDenominationAndQuantity> chipDetailsList) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		for (ChipDenominationAndQuantity chipDetails : chipDetailsList) {
			double denomination = Utility.roundToDecimals(chipDetails
					.getDenomination(), NUMBER_OF_DECIMAL_PLACES);
			result.put(denomination, MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT);
		}
		return result;
	}

	/**
	 * Retrieves missing mandatory denominations from maximum chip distribution
	 * list
	 * 
	 * @param maxChipDistribution
	 *            maximum chip distribution collection
	 * @param mandatoryChipDetailsMap
	 *            mandatory denominations and associated quantities
	 * @return map containing missing denominations and associated quantities
	 */
	private Map<Double, Integer> retriveMissingDenominationsUsing(
			final Map<Double, Integer> currencyAndQuantityMatchingBuyInAmountMap,
			final Map<Double, Integer> mandatoryChipDetailsMap) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		Map<Double, Integer> maxChipDistributionMap = convertToMap(currencyAndQuantityMatchingBuyInAmountMap);
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(mandatoryChipDetailsMap);
		while (keys.hasNext()) {
			double key = (Double) keys.next();
			if (maxChipDistributionMap.containsKey(key)) {
				int maxQuantity = maxChipDistributionMap.get(key);
				if (maxQuantity < MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT) {
					result.put(key, MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT
							- maxQuantity);
				}
			} else {
				result.put(key, MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT);
			}
		}
		return result;
	}

	/**
	 * Converts input to map for lookup purposes
	 * 
	 * @param input
	 * @return map with key as denomination and value as quantity
	 */
	private Map<Double, Integer> convertToMap(
			final Map<Double, Integer> currencyAndQuantityMatchingBuyInAmountMap) {
		Map<Double, Integer> chipQuantityAndDenominationMap = new HashMap<Double, Integer>();
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(currencyAndQuantityMatchingBuyInAmountMap);
		while (keys.hasNext()) {
			double denomination = keys.next();
			int quantity = currencyAndQuantityMatchingBuyInAmountMap
					.get(denomination);
			chipQuantityAndDenominationMap.put(denomination, quantity);
		}
		return chipQuantityAndDenominationMap;
	}

	/**
	 * Retrieves map of denominations of greater quantities to the mandatory
	 * quantities.
	 * 
	 * @param maxChipDistribution
	 *            maximum chip distribution collection
	 * @param mandatoryChipDetailsMap
	 *            mandatory denominations and associated quantities
	 * @return map containing missing denominations and associated quantities
	 */
	private Map<Double, Integer> retrieveExtraDenominationsFrom(
			final Map<Double, Integer> currencyAndQuantityMatchingBuyInAmountMap,
			final Map<Double, Integer> mandatoryChipDetailsMap) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(mandatoryChipDetailsMap);
		while (keys.hasNext()) {
			double key = (Double) keys.next();
			if (currencyAndQuantityMatchingBuyInAmountMap.containsKey(key)) {
				int maxQuantity = currencyAndQuantityMatchingBuyInAmountMap
						.get(key);
				if (maxQuantity > MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT) {
					result.put(key, maxQuantity
							- MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT);
				}
			}
		}
		return result;
	}

	private Map<Double, Integer> retrieveRevisedChipDistributionMap(
			final Map<Double, Integer> missingDenominationsMap,
			final Map<Double, Integer> extraDenominationsMap) {
		Map<Double, Integer> result = extraDenominationsMap;
		Iterator<Double> missingDenominationKeys = fetchSortedKeysInDescendingOrderUsing(missingDenominationsMap);
		while (missingDenominationKeys.hasNext()) {
			double missingDenominationKey = (Double) missingDenominationKeys
					.next();
			int missingQuantity = (int) missingDenominationsMap
					.get(missingDenominationKey);
			double missingAmount = missingDenominationKey * missingQuantity;

			Iterator<Double> extraDenominationKeys = fetchSortedKeysInDescendingOrderUsing(extraDenominationsMap);

			while (extraDenominationKeys.hasNext()
					&& missingAmount != Double.MIN_VALUE) {
				double extraDenominationKey = (Double) extraDenominationKeys
						.next();
				int extraQuantity = (int) extraDenominationsMap
						.get(extraDenominationKey);
				double extraAmount = extraDenominationKey * extraQuantity;

				if (extraAmount >= missingAmount) {
					int revisedQuantity = (int) (extraQuantity - (missingAmount / extraDenominationKey));
					result.remove(extraDenominationKey);

					if (revisedQuantity >= 1) {
						result.put(extraDenominationKey, revisedQuantity);
					}
					missingAmount = Double.MIN_VALUE;
				} else {
					result.remove(extraDenominationKey);
					missingAmount -= extraAmount;
				}
			}
		}
		return result;
	}

	private List<PokerChipDistributionResult> merge(
			final Map<Double, Integer> mandatoryChipDetailsMap,
			final Map<Double, Integer> revisedChipDistributionMap) {
		List<PokerChipDistributionResult> result = new ArrayList<PokerChipDistributionResult>();
		Iterator<Double> mandatoryDenominationKeys = fetchSortedKeysInDescendingOrderUsing(mandatoryChipDetailsMap);
		while (mandatoryDenominationKeys.hasNext()) {
			double mandatoryDenominationKey = (Double) mandatoryDenominationKeys
					.next();
			int quantity = revisedChipDistributionMap
					.containsKey(mandatoryDenominationKey) ? revisedChipDistributionMap
					.get(mandatoryDenominationKey)
					+ MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT
					: MANDATORY_CHIP_QUANTITY_PER_PARTICIPIANT;

			result.add(new PokerChipDistributionResult(String.format("$%.2f",
					mandatoryDenominationKey), quantity));
		}
		return result;
	}
}
