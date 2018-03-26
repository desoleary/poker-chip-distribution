/*
 * @(#)PokerChipDistributionForBonusTwo.java        2010/06/03
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
package com.company.application.domain;

import java.util.ArrayList;
import java.util.List;

import com.company.application.bo.calculator.ChipDistributionCalculatorForBonusTwo;

/**
 * Domain object that wraps all parameters necessary to determine poker chip
 * distribution for bonus two
 * 
 * @author Desmond O'Leary
 * 
 */
public class PokerChipDistributionForBonusTwo implements IPokerChipDistribution {
	private List<ChipColorAndQuantity> chipDetailsList = new ArrayList<ChipColorAndQuantity>();
	private int playerCount;
	private double buyInAmount;

	public PokerChipDistributionForBonusTwo(
			final List<ChipColorAndQuantity> chipDetailsList,
			final int playerCount, final double buyInAmount) {

		if (chipDetailsList == null || chipDetailsList.size() < 1) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter chipDetailsList found to have a count needs to be greater than zero but was recieved as '%d'",
									playerCount));
		}

		if (playerCount < 1) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter playerCount must be greater than zero but was recieved as '%s'",
									playerCount));
		}

		if (buyInAmount < ChipDistributionCalculatorForBonusTwo.availableCurrencies[0]) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter buyInAmount must be in the monetary format of ##.## with two decimal places but was recieved as '%s'",
									buyInAmount));
		}
		addAll(chipDetailsList);
		setPlayerCount(playerCount);
		setBuyInAmount(buyInAmount);
	}

	/**
	 * @param chipDetails
	 *            appends chip to collection
	 */
	public final void add(final ChipColorAndQuantity chipDetails) {
		this.chipDetailsList.add(chipDetails);
	}

	/**
	 * @param chipDetailsList
	 *            appends collection of chip details
	 */
	private void addAll(List<ChipColorAndQuantity> chipDetailsList) {
		this.chipDetailsList.addAll(chipDetailsList);
	}

	/**
	 * @return the chipDetailsList
	 */
	public List<ChipColorAndQuantity> getChipDetailsList() {
		return chipDetailsList;
	}

	/**
	 * @param playerCount
	 *            the playerCount to set
	 */
	public final void setPlayerCount(final int playerCount) {
		this.playerCount = playerCount;
	}

	/**
	 * @return the playerCount
	 */
	public final int getPlayerCount() {
		return playerCount;
	}

	/**
	 * @param buyInAmount
	 *            the buyInAmount to set
	 */
	public void setBuyInAmount(double buyInAmount) {
		this.buyInAmount = buyInAmount;
	}

	/**
	 * @return the buyInAmount
	 */
	public double getBuyInAmount() {
		return buyInAmount;
	}
}
