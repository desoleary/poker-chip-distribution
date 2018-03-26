/*
 * @(#)PokerChipDistributionResultList.java        2010/06/03
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
 * Wrapper around the generic ArrayList implementation.
 */
public class PokerChipDistributionResultList extends
		ArrayList<PokerChipDistributionResult> {

	private ResponseType responseType;
	private static final long serialVersionUID = 1L;

	public PokerChipDistributionResultList(
			List<PokerChipDistributionResult> list, ResponseType responseType) {
		this.addAll(list);
		setResponseType(responseType);
	}

	/**
	 * @param denomination
	 *            the denomination to set
	 */
	private void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	/**
	 * @return the resposeType
	 */
	public ResponseType getResponseType() {
		return responseType;
	}

}
