package com.company.application.bo.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.company.application.domain.ChipDenominationAndQuantity;
import com.company.application.util.Utility;

public abstract class ChipDistributionCalculatorBase {
	protected static final int NUMBER_OF_DECIMAL_PLACES = 2;

	/**
	 * Takes in map of currency range and associated maximum quantity and buy in
	 * amount and returns currency and quantity map that maps given buy in
	 * amount.
	 * 
	 * @param optimalCurrencyAndQuantityRangeMap
	 *            map with key as the currency/denomination and value as
	 *            associated quantity
	 * @param buyInAmount
	 *            buy in amount to be matched
	 * @return map with key as the currency/denomination and value as associated
	 *         quantity that matches buy in amount
	 */
	protected Map<Double, Integer> getCurrencyAndQuantitiesMatchingBuyInAmount(
			final Map<Double, Integer> optimalCurrencyAndQuantityRangeMap,
			final double buyInAmount) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		double maximumAmount = getMaxiumAmountFrom(optimalCurrencyAndQuantityRangeMap);
		double remainingAmount = Utility.roundToDecimals(maximumAmount
				- buyInAmount, NUMBER_OF_DECIMAL_PLACES);
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(optimalCurrencyAndQuantityRangeMap);
		while (keys.hasNext()) {
			double currency = (Double) keys.next();
			int quantity = optimalCurrencyAndQuantityRangeMap.get(currency);

			int revisedQuantity = fetchRevisedQuantityFrom(remainingAmount,
					currency, quantity);
			result.put(currency, revisedQuantity);
			remainingAmount = Utility.roundToDecimals(remainingAmount
					- (currency * (quantity - revisedQuantity)),
					NUMBER_OF_DECIMAL_PLACES);
		}
		return result;
	}
	
	/**
	 * Takes in map of currency range and associated maximum quantity and buy in
	 * amount and returns currency and quantity map that maps given buy in
	 * amount.  Also takes into account the maximum currency range in descending order 
	 * 
	 * @param optimalCurrencyAndQuantityRangeMap
	 *            map with key as the currency/denomination and value as
	 *            associated quantity
	 * @param buyInAmount
	 *            buy in amount to be matched
	 * @return map with key as the currency/denomination and value as associated
	 *         quantity that matches buy in amount
	 */
	protected Map<Double, Integer> getCurrencyAndQuantitiesMatchingBuyInAmountForBonusTwo(
			final Map<Double, Integer> optimalCurrencyAndQuantityRangeMap,
			final double buyInAmount) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		double maximumAmount = getMaxiumAmountForBonusTwoFrom(optimalCurrencyAndQuantityRangeMap);
		double remainingAmount = Utility.roundToDecimals(maximumAmount
				- buyInAmount, NUMBER_OF_DECIMAL_PLACES);
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(optimalCurrencyAndQuantityRangeMap);
		while (keys.hasNext()) {
			double currency = (Double) keys.next();
			int quantity = optimalCurrencyAndQuantityRangeMap.get(currency);

			int revisedQuantity = fetchRevisedQuantityFrom(remainingAmount,
					currency, quantity);
			result.put(currency, revisedQuantity);
			remainingAmount = Utility.roundToDecimals(remainingAmount
					- (currency * (quantity - revisedQuantity)),
					NUMBER_OF_DECIMAL_PLACES);
		}
		return result;
	}	

	/**
	 * Takes in map and returns iterator of keys in descending order
	 * 
	 * @param mandatoryChipDetailsMap
	 *            map with key as the currency/denomination and value as
	 *            associated quantity that matches buy in amount
	 * @return denomination sorted in descending order
	 */
	protected Iterator<Double> fetchSortedKeysInDescendingOrderUsing(
			final Map<Double, Integer> mandatoryChipDetailsMap) {
		Vector<Double> v = new Vector<Double>(mandatoryChipDetailsMap.keySet());
		Collections.sort(v, Collections.reverseOrder());
		return v.iterator();
	}

	/**
	 * Calculate the maximum monetary amount based on given currency and
	 * list of quantities. 
	 * 
	 * @param currencyRange
	 *            range of currencies/denomination
	 * @param quantities
	 * @return total accumulated amount
	 */
	protected double maximumAmountFrom(final List<Double> currencyRange,
			final List<Integer> quantities) {
		double result = 0.00;
		for (int index = 0; index < currencyRange.size(); index++) {
			result += currencyRange.get(index) * quantities.get(index);
		}
		return result;
	}
	
	/**
	 * Calculate the maximum monetary amount based on given currency range and
	 * list of quantities. currency range will be sorted in ascending order,
	 * while quantities will be sorted in descending order before accumulating
	 * amount.
	 * 
	 * @param currencyRange
	 *            range of currencies/denomination
	 * @param quantities
	 * @return total accumulated amount
	 */
	protected double maximumAmountForBonusTwoFrom(List<Double> currencyRange,
			List<Integer> quantities) {
		Collections.sort(currencyRange);
		Collections.sort(quantities, Collections.reverseOrder());
		double result = 0.00;
		for (int index = 0; index < currencyRange.size(); index++) {
			result += currencyRange.get(index) * quantities.get(index);
		}
		return result;
	}

	/**
	 * Converts chip denomination and quantity to Map
	 * 
	 * @param chipDetailsList
	 * @return map with key as the currency/denomination and value as associated
	 *         quantity
	 */
	protected Map<Double, Integer> convertToMap(
			List<ChipDenominationAndQuantity> chipDetailsList,
			int playerCount) {
		Map<Double, Integer> result = new HashMap<Double, Integer>();
		for (ChipDenominationAndQuantity chipDetails : chipDetailsList) {
			result.put(chipDetails.getDenomination(), chipDetails.getQuantity()
					/ playerCount);
		}
		return result;
	}

	/**
	 * Calculates maximum monetary amount based on given denomination and
	 * associated quantities.
	 * 
	 * @param optimalCurrencyAndQuantityRangeMap
	 *            map with key as the currency/denomination and value as
	 *            associated quantity
	 * @return maximum amount
	 */
	private double getMaxiumAmountFrom(
			final Map<Double, Integer> optimalCurrencyAndQuantityRangeMap) {
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(optimalCurrencyAndQuantityRangeMap);
		List<Double> currencyRange = new ArrayList<Double>();
		List<Integer> maximumAvailableQuantities = new ArrayList<Integer>();
		while (keys.hasNext()) {
			double currency = (Double) keys.next();
			int quantity = optimalCurrencyAndQuantityRangeMap.get(currency);
			currencyRange.add(currency);
			maximumAvailableQuantities.add(quantity);
		}

		double maximumAmount = maximumAmountFrom(currencyRange,
				maximumAvailableQuantities);
		return maximumAmount;
	}
	
	
	private double getMaxiumAmountForBonusTwoFrom(
			Map<Double, Integer> optimalCurrencyAndQuantityRangeMap) {
		Iterator<Double> keys = fetchSortedKeysInDescendingOrderUsing(optimalCurrencyAndQuantityRangeMap);
		List<Double> currencyRange = new ArrayList<Double>();
		List<Integer> maximumAvailableQuantities = new ArrayList<Integer>();
		while (keys.hasNext()) {
			double currency = (Double) keys.next();
			int quantity = optimalCurrencyAndQuantityRangeMap.get(currency);
			currencyRange.add(currency);
			maximumAvailableQuantities.add(quantity);
		}

		double maximumAmount = maximumAmountForBonusTwoFrom(currencyRange,
				maximumAvailableQuantities);
		return maximumAmount;
	}	

	/**
	 * Takes in currency and amount and determines how many times the total
	 * amount goes into the remaining amount.
	 * 
	 * @param remainingAmount
	 *            remaining amount
	 * @param currency
	 *            currency/denomination amount
	 * @param quantity
	 *            quantity of denomination
	 * @return returns quantity minus found amount if found amount is less than
	 *         given quantity else return zero.
	 */
	private int fetchRevisedQuantityFrom(final double remainingAmount,
			final double currency, final int quantity) {
		int maxQuantityToSubtract = (int) Math
				.floor(remainingAmount / currency);
		if (quantity < maxQuantityToSubtract) {
			return 0;
		}
		return (quantity - maxQuantityToSubtract);
	}
}
