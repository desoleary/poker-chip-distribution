package com.company.application.domain;

import org.junit.Assert;
import org.junit.Test;

public class ChipColorAndQuantityTest {
	
	@Test
	public void shouldReturnAllParametersPassedToConstructure() {
		
		String expectedColor = "Blue";
		int expectQuantity = 10;
		
		ChipColorAndQuantity actual = new ChipColorAndQuantity(expectedColor, expectQuantity);
		
		Assert.assertEquals(expectedColor, actual.getColor());
		Assert.assertEquals(expectQuantity, actual.getQuantity());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenColorIsEmpty() {
		String expectedColor = "";
		int expectQuantity = 10;
		
		new ChipColorAndQuantity(expectedColor, expectQuantity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenColorIsNull() {
		String expectedColor = null;
		int expectQuantity = 10;
		
		new ChipColorAndQuantity(expectedColor, expectQuantity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenQuantityIsLessThanOne() {
		String expectedColor = "Blue";
		int expectQuantity = 0;
		
		new ChipColorAndQuantity(expectedColor, expectQuantity);
	}
}
