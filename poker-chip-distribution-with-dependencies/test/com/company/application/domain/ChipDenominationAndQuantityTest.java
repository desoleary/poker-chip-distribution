package com.company.application.domain;

import junit.framework.Assert;

import org.junit.Test;

public class ChipDenominationAndQuantityTest {

	@Test
	public void shouldReturnAllParametersPassedToConstructure() {
		double expectedDenomination = 10.00;
		int expectQuantity = 10;

		ChipDenominationAndQuantity result = new ChipDenominationAndQuantity(
				expectedDenomination, expectQuantity);

		Assert.assertEquals(expectedDenomination, result.getDenomination());
		Assert.assertEquals(expectQuantity, result.getQuantity());
	}

	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenDenominationIsLessThanZero() {
		double expectedDenomination = -1.00;
		int expectQuantity = 10;

		new ChipDenominationAndQuantity(expectedDenomination, expectQuantity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenDenominationIsEqualToZero() {
		double expectedDenomination = 0.00;
		int expectQuantity = 10;

		new ChipDenominationAndQuantity(expectedDenomination, expectQuantity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenQuantityIsLessThanOne() {
		double expectedDenomination = 10.00;
		int expectQuantity = 0;

		new ChipDenominationAndQuantity(expectedDenomination, expectQuantity);
	}
}
