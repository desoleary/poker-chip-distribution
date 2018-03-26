package com.company.application.controller;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.exception.ParserException;

public class PokerChipDistributionControllerTest {

	@Test
	public void shouldReturnChipsAndAssociatedCurrencyToMaximizeAmountOfChips()
			throws IllegalArgumentException, IOException, ParserException {
		List<PokerChipDistributionResult> actual = new PokerChipDistributionController()
				.calculatePokerChipDistributionFor(getTestDataSecenarioOne("$10.00"));

		Assert.assertEquals("$2.00", actual.get(0).getDenomination());
		Assert.assertEquals(0, actual.get(0).getQuantity());
		Assert.assertEquals("$1.00", actual.get(1).getDenomination());
		Assert.assertEquals(1, actual.get(1).getQuantity());
		Assert.assertEquals("$0.50", actual.get(2).getDenomination());
		Assert.assertEquals(10, actual.get(2).getQuantity());
		Assert.assertEquals("$0.25", actual.get(3).getDenomination());
		Assert.assertEquals(10, actual.get(3).getQuantity());
		Assert.assertEquals("$0.10", actual.get(4).getDenomination());
		Assert.assertEquals(10, actual.get(4).getQuantity());
		Assert.assertEquals("$0.05", actual.get(5).getDenomination());
		Assert.assertEquals(10, actual.get(5).getQuantity());
	}

	private final static String getTestDataSecenarioOne(String buyInAmount) {
		return new StringBuffer(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append(buyInAmount)
				.toString();
	}
}
