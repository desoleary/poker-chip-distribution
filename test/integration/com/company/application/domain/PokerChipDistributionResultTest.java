package com.company.application.domain;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.domain.PokerChipDistributionResult;

public class PokerChipDistributionResultTest {
	
	@Test
	public void shouldReturnAllParametersPassToConstructure(){
		String expectedColor = "Red";
		String expectedDenomination = "$12.00";
		int expectedQuantity = 777;
		
		PokerChipDistributionResult result = new PokerChipDistributionResult(expectedColor, expectedDenomination, expectedQuantity);
		
		Assert.assertEquals(expectedColor, result.getColor());
		Assert.assertEquals(expectedDenomination, result.getDenomination());
		Assert.assertEquals(expectedQuantity, result.getQuantity());		
	}
	
	@Test
	public void shouldReturnAllParametersPassToConstructureAndSetColorToNull(){
		
		String expectedColor = null;
		String expectedDenomination = "$12.00";
		int expectedQuantity = 777;
		
		PokerChipDistributionResult result = new PokerChipDistributionResult(expectedDenomination, expectedQuantity);
		
		Assert.assertEquals(expectedColor, null);
		Assert.assertEquals(expectedDenomination, result.getDenomination());
		Assert.assertEquals(expectedQuantity, result.getQuantity());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenGivenNonNumericDenomination() {
		String expectedColor = "Red";
		String expectedDenomination = "some non numeric characters";
		int expectedQuantity = 777;
		
		new PokerChipDistributionResult(expectedColor, expectedDenomination, expectedQuantity);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenGivenQuantityLessThanZero() {
		String expectedColor = "Red";
		String expectedDenomination = "$12.00";
		int expectedQuantity = -1;
		
		new PokerChipDistributionResult(expectedColor, expectedDenomination, expectedQuantity);
	}
}
