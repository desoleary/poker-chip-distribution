package com.company.application.domain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class PokerChipDistributionForBonusTwoTest {

	@Test
	public void shouldReturnAllParametersPassToConstructure() {
		List<ChipColorAndQuantity> expectedChipDetailsList = getInitiializedChipDetailsList();
		int expectedPlayerCount = 10;
		double expectedBuyInAmount = 10.00;

		PokerChipDistributionForBonusTwo result = new PokerChipDistributionForBonusTwo(
				expectedChipDetailsList, expectedPlayerCount,
				expectedBuyInAmount);

		Assert.assertEquals(expectedChipDetailsList, result
				.getChipDetailsList());
		Assert.assertEquals(expectedPlayerCount, result.getPlayerCount());
		Assert.assertEquals(expectedBuyInAmount, result.getBuyInAmount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenGivenNullChipDetailsList() {
		List<ChipColorAndQuantity> expectedChipDetailsList = null;
		int expectedPlayerCount = 10;
		double expectedBuyInAmount = 10.00;

		new PokerChipDistributionForBonusTwo(expectedChipDetailsList,
				expectedPlayerCount, expectedBuyInAmount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenPlayerCountIsLessThanOne() {
		List<ChipColorAndQuantity> expectedChipDetailsList = getInitiializedChipDetailsList();
		int expectedPlayerCount = 0;
		double expectedBuyInAmount = 10.00;

		new PokerChipDistributionForBonusTwo(expectedChipDetailsList,
				expectedPlayerCount, expectedBuyInAmount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenBuyInAMountIsLessThanZero() {
		List<ChipColorAndQuantity> expectedChipDetailsList = getInitiializedChipDetailsList();
		int expectedPlayerCount = 10;
		double expectedBuyInAmount = -10.00;

		new PokerChipDistributionForBonusTwo(expectedChipDetailsList,
				expectedPlayerCount, expectedBuyInAmount);
	}

	private static final List<ChipColorAndQuantity> getInitiializedChipDetailsList() {
		List<ChipColorAndQuantity> chipDetailsList = new ArrayList<ChipColorAndQuantity>();
		chipDetailsList.add(new ChipColorAndQuantity("Red", 10));
		return chipDetailsList;
	}
}
