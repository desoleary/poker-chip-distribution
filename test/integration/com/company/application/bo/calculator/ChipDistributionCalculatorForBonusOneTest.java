package com.company.application.bo.calculator;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.bo.calculator.ChipDistributionCalculatorForBonusOne;
import com.company.application.domain.PokerChipDistribution;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.exception.ParserException;
import com.company.application.parser.PockerChipDistributionParserForBonusOne;

public class ChipDistributionCalculatorForBonusOneTest {
	@Test
	public void shouldReturnChipsAndAssociatedCurrencyToMaximizeAmountOfChips()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParserForBonusOne()
				.parse(getTestDataSecenarioTwo("$10.00"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculatorForBonusOne()
				.calculate(input);

		Assert.assertEquals("$2.00", actual.get(0).getDenomination());
		Assert.assertEquals(1, actual.get(0).getQuantity());
		Assert.assertEquals("$1.00", actual.get(1).getDenomination());
		Assert.assertEquals(1, actual.get(1).getQuantity());
		Assert.assertEquals("$0.50", actual.get(2).getDenomination());
		Assert.assertEquals(6, actual.get(2).getQuantity());
		Assert.assertEquals("$0.25", actual.get(3).getDenomination());
		Assert.assertEquals(10, actual.get(3).getQuantity());
		Assert.assertEquals("$0.10", actual.get(4).getDenomination());
		Assert.assertEquals(10, actual.get(4).getQuantity());
		Assert.assertEquals("$0.05", actual.get(5).getDenomination());
		Assert.assertEquals(10, actual.get(5).getQuantity());
	}

	@Test
	public void shouldReturnMaxChipsWhenGivenMaxBuyInAmount()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParserForBonusOne()
				.parse(getTestDataSecenarioTwo("$24.00"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculatorForBonusOne()
				.calculate(input);

		Assert.assertEquals("$2.00", actual.get(0).getDenomination());
		Assert.assertEquals(5, actual.get(0).getQuantity());
		Assert.assertEquals("$1.00", actual.get(1).getDenomination());
		Assert.assertEquals(5, actual.get(1).getQuantity());
		Assert.assertEquals("$0.50", actual.get(2).getDenomination());
		Assert.assertEquals(10, actual.get(2).getQuantity());
		Assert.assertEquals("$0.25", actual.get(3).getDenomination());
		Assert.assertEquals(10, actual.get(3).getQuantity());
		Assert.assertEquals("$0.10", actual.get(4).getDenomination());
		Assert.assertEquals(10, actual.get(4).getQuantity());
		Assert.assertEquals("$0.05", actual.get(5).getDenomination());
		Assert.assertEquals(10, actual.get(5).getQuantity());
	}

	@Test
	public void shouldReturnMaxChipsWhenGivenMinBuyInAmount()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParserForBonusOne()
				.parse(getTestDataSecenarioTwo("$3.90"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculatorForBonusOne()
				.calculate(input);

		Assert.assertEquals("$2.00", actual.get(0).getDenomination());
		Assert.assertEquals(1, actual.get(0).getQuantity());
		Assert.assertEquals("$1.00", actual.get(1).getDenomination());
		Assert.assertEquals(1, actual.get(1).getQuantity());
		Assert.assertEquals("$0.50", actual.get(2).getDenomination());
		Assert.assertEquals(1, actual.get(2).getQuantity());
		Assert.assertEquals("$0.25", actual.get(3).getDenomination());
		Assert.assertEquals(1, actual.get(3).getQuantity());
		Assert.assertEquals("$0.10", actual.get(4).getDenomination());
		Assert.assertEquals(1, actual.get(4).getQuantity());
		Assert.assertEquals("$0.05", actual.get(5).getDenomination());
		Assert.assertEquals(1, actual.get(5).getQuantity());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldReturnThrowIllegalArgumentExceptionWhenGivenBuyInAmountLessThanMinMandatoryBuyInAmount()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParserForBonusOne()
				.parse(getTestDataSecenarioTwo("$3.85"));
		new ChipDistributionCalculatorForBonusOne().calculate(input);
	}

	private final static String getTestDataSecenarioTwo(String buyInAmount) {
		return new StringBuffer("B1").append("\n").append(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append(buyInAmount)
				.toString();
	}
}
