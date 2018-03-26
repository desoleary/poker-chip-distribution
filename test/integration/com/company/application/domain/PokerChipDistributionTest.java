package com.company.application.domain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class PokerChipDistributionTest {

	@Test
	public void shouldReturnAllParametersPassToConstructure() {
		List<ChipDenominationAndQuantity> expectedChipDetailsList = getInitiializedChipDetailsList();
		int expectedPlayerCount = 10;
		double expectedBuyInAmount = 10.00;

		PokerChipDistribution result = new PokerChipDistribution(
				expectedChipDetailsList, expectedPlayerCount,
				expectedBuyInAmount);

		Assert.assertEquals(expectedChipDetailsList, result
				.getChipDetailsList());
		Assert.assertEquals(expectedPlayerCount, result.getPlayerCount());
		Assert.assertEquals(expectedBuyInAmount, result.getBuyInAmount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenGivenNullChipDetailsList() {
		List<ChipDenominationAndQuantity> expectedChipDetailsList = null;
		int expectedPlayerCount = 10;
		double expectedBuyInAmount = 10.00;

		new PokerChipDistribution(expectedChipDetailsList, expectedPlayerCount,
				expectedBuyInAmount);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenPlayerCountIsLessThanOne() {
		List<ChipDenominationAndQuantity> expectedChipDetailsList = getInitiializedChipDetailsList();
		int expectedPlayerCount = 0;
		double expectedBuyInAmount = 10.00;

		new PokerChipDistribution(expectedChipDetailsList, expectedPlayerCount,
				expectedBuyInAmount);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenBuyInAMountIsLessThanZero() {
		List<ChipDenominationAndQuantity> expectedChipDetailsList = getInitiializedChipDetailsList();
		int expectedPlayerCount = 10;
		double expectedBuyInAmount = -10.00;

		new PokerChipDistribution(expectedChipDetailsList, expectedPlayerCount,
				expectedBuyInAmount);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenBuyInAMountIsEqualToZero() {
		List<ChipDenominationAndQuantity> expectedChipDetailsList = getInitiializedChipDetailsList();
		int expectedPlayerCount = 10;
		double expectedBuyInAmount = 0.00;

		new PokerChipDistribution(expectedChipDetailsList, expectedPlayerCount,
				expectedBuyInAmount);
	}
	
	private static final List<ChipDenominationAndQuantity> getInitiializedChipDetailsList(){
		List<ChipDenominationAndQuantity> chipDetailsList = new ArrayList<ChipDenominationAndQuantity>();
		chipDetailsList.add(new ChipDenominationAndQuantity(10.00, 10));
		return chipDetailsList;
	}
}
