/*
 * @(#)PokerChipDistribution.java        2010/06/03
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

/**
 * Domain object that wraps all parameters necessary to determine poker chip
 * distribution
 * 
 * @author Desmond O'Leary
 * 
 */
public class PokerChipDistribution implements IPokerChipDistribution {
	private List<ChipDenominationAndQuantity> chipDetailsList = new ArrayList<ChipDenominationAndQuantity>();
	private int playerCount;
	private double buyInAmount;

	public PokerChipDistribution(
			final List<ChipDenominationAndQuantity> chipDetailsList,
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
									"parameter playerCount must be a greater than zero but was recieved as '%d'",
									playerCount));
		}

		double lowestDenomination = getMinDenominationValueFrom(chipDetailsList);
		if (buyInAmount < lowestDenomination) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter buyInAmount must be a greater than the lowest denomination value '%s' but was recieved as '%d'",
									lowestDenomination, playerCount));
		}
		addAll(chipDetailsList);
		setPlayerCount(playerCount);
		setBuyInAmount(buyInAmount);
	}

	/**
	 * @param chipDetails
	 *            appends chip to collection
	 */
	public final void add(final ChipDenominationAndQuantity chipDetails) {
		this.chipDetailsList.add(chipDetails);
	}

	/**
	 * @param chipDetailsList
	 *            appends collection of chip details
	 */
	public final void addAll(
			final List<ChipDenominationAndQuantity> chipDetailsList) {
		this.chipDetailsList.addAll(chipDetailsList);
	}

	/**
	 * @return the chipDetailsList
	 */
	public final List<ChipDenominationAndQuantity> getChipDetailsList() {
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
	public final void setBuyInAmount(final double buyInAmount) {
		this.buyInAmount = buyInAmount;
	}

	/**
	 * @return the buyInAmount
	 */
	public final double getBuyInAmount() {
		return buyInAmount;
	}

	private static double getMinDenominationValueFrom(final List<ChipDenominationAndQuantity> chipDetailsList) {
		double minValue = chipDetailsList.get(0).getDenomination();
		for (int i = 1; i < chipDetailsList.size(); i++) {
			if (chipDetailsList.get(i).getDenomination() < minValue) {
				minValue = chipDetailsList.get(i).getDenomination();
			}
		}
		return minValue;
	}
}
