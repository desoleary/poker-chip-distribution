/*
 * @(#)ChipDenominationAndQuantity.java        2010/06/03
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

/**
 * domain object for storing chip denomination and quantity.
 * 
 * @author Desmond O'Leary
 * 
 */
public class ChipDenominationAndQuantity {
	private double denomination;
	private int quantity;

	public ChipDenominationAndQuantity(final double denomination,
			final int quantity) {
		if (denomination <= 0.00) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter denomination must be a greater than zero but was recieved as '%.2f'",
									denomination));
		}

		if (quantity < 1) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter quantity must be a greater than zero but was recieved as '%d'",
									quantity));
		}
		setDenomination(denomination);
		setQuantity(quantity);
	}

	/**
	 * @param denomination
	 *            the denomination to set
	 */
	public final void setDenomination(final double denomination) {
		this.denomination = denomination;
	}

	/**
	 * @return the denomination
	 */
	public final double getDenomination() {
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
