package com.company.application.bo.calculator;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.bo.calculator.ChipDistributionCalculatorForBonusTwo;
import com.company.application.domain.PokerChipDistribution;
import com.company.application.domain.PokerChipDistributionForBonusTwo;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.exception.ParserException;
import com.company.application.parser.PockerChipDistributionParserForBonusOne;
import com.company.application.parser.PockerChipDistributionParserForBonusTwo;

public class ChipDistributionCalculatorForBonusTwoTest {

	@Test
	public void shouldReturnChipsAndAssociatedCurrencyToMaximizeAmountOfChips()
			throws IOException, ParserException {
		PokerChipDistributionForBonusTwo input = new PockerChipDistributionParserForBonusTwo()
				.parse(getTestDataSecenarioThree("$10.00"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculatorForBonusTwo()
				.calculate(input);

		Assert.assertEquals("$1.00", actual.get(0).getDenomination());
		Assert.assertEquals(4, actual.get(0).getQuantity());
		Assert.assertEquals("$0.50", actual.get(1).getDenomination());
		Assert.assertEquals(4, actual.get(1).getQuantity());
		Assert.assertEquals("$0.25", actual.get(2).getDenomination());
		Assert.assertEquals(10, actual.get(2).getQuantity());
		Assert.assertEquals("$0.10", actual.get(3).getDenomination());
		Assert.assertEquals(9, actual.get(3).getQuantity());
		Assert.assertEquals("$0.05", actual.get(4).getDenomination());
		Assert.assertEquals(10, actual.get(4).getQuantity());
		Assert.assertEquals("$0.01", actual.get(5).getDenomination());
		Assert.assertEquals(10, actual.get(5).getQuantity());
	}

	@Test
	public void shouldReturnMaxChipsWhenGivenMaxBuyInAmount()
			throws IOException, ParserException {
		PokerChipDistributionForBonusTwo input = new PockerChipDistributionParserForBonusTwo()
				.parse(getTestDataSecenarioThree("$6350.00"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculatorForBonusTwo()
				.calculate(input);

		Assert.assertEquals("$1000.00", actual.get(0).getDenomination());
		Assert.assertEquals(5, actual.get(0).getQuantity());
		Assert.assertEquals("$100.00", actual.get(1).getDenomination());
		Assert.assertEquals(5, actual.get(1).getQuantity());
		Assert.assertEquals("$50.00", actual.get(2).getDenomination());
		Assert.assertEquals(10, actual.get(2).getQuantity());
		Assert.assertEquals("$20.00", actual.get(3).getDenomination());
		Assert.assertEquals(10, actual.get(3).getQuantity());
		Assert.assertEquals("$10.00", actual.get(4).getDenomination());
		Assert.assertEquals(10, actual.get(4).getQuantity());
		Assert.assertEquals("$5.00", actual.get(5).getDenomination());
		Assert.assertEquals(10, actual.get(5).getQuantity());
	}
	
	@Test
	public void shouldReturnMaxChipsWhenGivenMinBuyInAmount()
			throws IOException, ParserException {
		PokerChipDistributionForBonusTwo input = new PockerChipDistributionParserForBonusTwo()
				.parse(getTestDataSecenarioThree("$0.01"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculatorForBonusTwo()
				.calculate(input);

		Assert.assertEquals("$1.00", actual.get(0).getDenomination());
		Assert.assertEquals(0, actual.get(0).getQuantity());
		Assert.assertEquals("$0.50", actual.get(1).getDenomination());
		Assert.assertEquals(0, actual.get(1).getQuantity());
		Assert.assertEquals("$0.25", actual.get(2).getDenomination());
		Assert.assertEquals(0, actual.get(2).getQuantity());
		Assert.assertEquals("$0.10", actual.get(3).getDenomination());
		Assert.assertEquals(0, actual.get(3).getQuantity());
		Assert.assertEquals("$0.05", actual.get(4).getDenomination());
		Assert.assertEquals(0, actual.get(4).getQuantity());
		Assert.assertEquals("$0.01", actual.get(5).getDenomination());
		Assert.assertEquals(1, actual.get(5).getQuantity());
	}

	private final static String getTestDataSecenarioThree(String buyInAmount) {
		return new StringBuffer("B2").append("\n").append(
				"50/Red,50/Blue,100/Black,100/Green,100/Yello,100/Taupe")
				.append("\n").append("10").append("\n").append(buyInAmount)
				.toString();
	}
}
