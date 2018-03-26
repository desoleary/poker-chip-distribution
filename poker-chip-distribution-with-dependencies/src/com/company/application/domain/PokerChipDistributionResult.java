/*
 * @(#)PokerChipDistributionResult.java        2010/06/03
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

import com.company.application.util.Parameter;

/**
 * poker chip distribution result domain object for storing chip color, denomination and quantity
 * 
 * @author Desmond O'Leary
 * 
 */
public class PokerChipDistributionResult {
	private String color;
	private String denomination;
	private int quantity;

	public PokerChipDistributionResult(final String denomination, final int quantity) {
		this(null, denomination, quantity);
	}

	public PokerChipDistributionResult(final String color, final String denomination,
			final int quantity) {

		if (!Parameter.hasValidNumericMonetaryValueFor(denomination)) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter denomination must be in the monetary format of $##.## with two decimal places but was recieved as '%s'",
									denomination));
		}

		if (quantity < 0) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter quantity must be a greater than or equal to zero but was recieved as '%d'",
									quantity));
		}
		setColor(color);
		setDenomination(denomination);
		setQuantity(quantity);
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public final void setColor(final String color) {
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public final String getColor() {
		return color;
	}

	/**
	 * @param denomination
	 *            the denomination to set
	 */
	private void setDenomination(final String denomination) {
		this.denomination = denomination;
	}

	/**
	 * @return the denomination
	 */
	public final String getDenomination() {
		return denomination;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public final void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public final int getQuantity() {
		return quantity;
	}
}
