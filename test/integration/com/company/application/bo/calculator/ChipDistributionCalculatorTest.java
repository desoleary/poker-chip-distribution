package com.company.application.bo.calculator;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.bo.calculator.ChipDistributionCalculator;
import com.company.application.domain.PokerChipDistribution;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.exception.ParserException;
import com.company.application.parser.PockerChipDistributionParser;

public class ChipDistributionCalculatorTest {

	@Test
	public void shouldReturnChipsAndAssociatedCurrencyToMaximizeAmountOfChips()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParser()
				.parse(getTestDataSecenarioOne("$10.00"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculator()
				.calculate(input);

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

	@Test
	public void shouldReturnMaxChipsWhenGivenMaxBuyInAmount()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParser()
				.parse(getTestDataSecenarioOne("$24.00"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculator()
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
	public void shouldReturnMaxChipsWhenGivenLeastValidBuyInAmount()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParser()
				.parse(getTestDataSecenarioOne("$00.05"));
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculator()
				.calculate(input);

		Assert.assertEquals("$2.00", actual.get(0).getDenomination());
		Assert.assertEquals(0, actual.get(0).getQuantity());
		Assert.assertEquals("$1.00", actual.get(1).getDenomination());
		Assert.assertEquals(0, actual.get(1).getQuantity());
		Assert.assertEquals("$0.50", actual.get(2).getDenomination());
		Assert.assertEquals(0, actual.get(2).getQuantity());
		Assert.assertEquals("$0.25", actual.get(3).getDenomination());
		Assert.assertEquals(0, actual.get(3).getQuantity());
		Assert.assertEquals("$0.10", actual.get(4).getDenomination());
		Assert.assertEquals(0, actual.get(4).getQuantity());
		Assert.assertEquals("$0.05", actual.get(5).getDenomination());
		Assert.assertEquals(1, actual.get(5).getQuantity());
	}

	@Test
	public void shouldReturnMaxChipsWhenGivenSpecificValues()
			throws ParserException {
		PokerChipDistribution input = new PockerChipDistributionParser()
				.parse(getTestDataScenariosFour());
		List<PokerChipDistributionResult> actual = new ChipDistributionCalculator()
		.calculate(input);
		
		Assert.assertEquals("$1.00", actual.get(0).getDenomination());
		Assert.assertEquals(1, actual.get(0).getQuantity());
		Assert.assertEquals("$0.75", actual.get(1).getDenomination());
		Assert.assertEquals(3, actual.get(1).getQuantity());
		Assert.assertEquals("$0.10", actual.get(2).getDenomination());
		Assert.assertEquals(1, actual.get(2).getQuantity());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldReturnThrowIllegalArgumentExceptionWhenGivenBuyInAmountEqualToZero()
			throws IOException, ParserException {
		PokerChipDistribution input = new PockerChipDistributionParser()
				.parse(getTestDataSecenarioOne("$00.04"));
		new ChipDistributionCalculator().calculate(input);
	}

	private final static String getTestDataSecenarioOne(String buyInAmount) {
		return new StringBuffer(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append(buyInAmount)
				.toString();
	}

	private final static String getTestDataScenariosFour() {
		return new StringBuffer("2/$1.00,3/$0.75,1/$0.10").append("\n").append(
				"1").append("\n").append("$3.35").toString();
	}
}
