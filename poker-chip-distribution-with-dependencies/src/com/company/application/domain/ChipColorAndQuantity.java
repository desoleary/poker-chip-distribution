/*
 * @(#)ChipColorAndQuantity.java        2010/06/03
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
 * domain object for storing chip color and quantity.
 * 
 * @author Desmond O'Leary
 * 
 */
public class ChipColorAndQuantity {
	private String color;
	private int quantity;

	public ChipColorAndQuantity(final String color, final int quantity) {
		if (color == null || color.length() < 1) {
			throw new IllegalArgumentException(String
					.format("given parameter color is empty"));
		}

		if (quantity < 1) {
			throw new IllegalArgumentException(
					String
							.format(
									"parameter quantity must be a greater than zero but was recieved as '%d'",
									quantity));
		}
		setQuantity(quantity);
		setColor(color);
	}

	/**
	 * @param color
	 *            the color to set
	 */
	private final void setColor(final String color) {
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public final String getColor() {
		return color;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	private final void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public final int getQuantity() {
		return quantity;
	}
}
