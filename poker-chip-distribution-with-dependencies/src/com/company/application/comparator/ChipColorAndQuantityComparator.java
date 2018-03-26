/*
 * @(#)ChipColorAndQuantityComparator.java        2010/06/03
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
package com.company.application.comparator;

import java.util.Comparator;

import com.company.application.domain.ChipColorAndQuantity;

/**
 * Class compares ChipColorAndQuantityComparator on the basis of the
 * denomination amount. Used to sort a collection of ChipQuantityAndDenomination
 * ordered by ascending quantity amount.
 * 
 * @author Desmond O'Leary
 * 
 */
public class ChipColorAndQuantityComparator implements
		Comparator<ChipColorAndQuantity> {

	/**
	 * Compares two ChipQuantityAndDenomination object to determine which one
	 * has the lowest denomination amount
	 * 
	 * @param chipDetails1
	 *            first object to compare
	 * @param chipDetails2
	 *            first object to compare
	 * @return '1' if obj1 is greater than obj2, returns '-1' if obj1 is less
	 *         than obj2 else return '0'
	 */
	public final int compare(final ChipColorAndQuantity chipDetails1, final ChipColorAndQuantity chipDetails2) {
		double quantity1 = chipDetails1.getQuantity();
		double quantity2 = chipDetails2.getQuantity();
		if (quantity1 > quantity2) {
			return 1;
		} else if (quantity1 < quantity2) {
			return -1;
		} else {
			return 0;
		}
	}

}
