package com.company.application.handler;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.bo.calculator.ChipDistributionCalculatorForBonusOne;
import com.company.application.domain.PokerChipDistribution;
import com.company.application.domain.PokerChipDistributionResult;
import com.company.application.exception.ParserException;
import com.company.application.parser.PockerChipDistributionParserForBonusOne;

public class RequestHandlerTest {

	@Test
	public void shouldReturnExpectedChipsAndAssociatedCurrency()
			throws IOException, ParserException, IllegalAccessException {
		String input = getTestDataSecenarioOne("$10.00");
		IRequestHandler handler = RequestHandlerFactory
				.createRequstHandlerFor(getTestDataSecenarioOne("$10.00"));
		List<PokerChipDistributionResult> actual = handler
				.handleRequestFor(input);

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
	public void shouldReturnExpectedChipsAndAssociatedCurrencyForBonusOne()
			throws IOException, ParserException {
		String input = getTestDataSecenarioTwo("$10.00");
		IRequestHandler handler = RequestHandlerFactory
				.createRequstHandlerFor(input);
		List<PokerChipDistributionResult> actual = handler
				.handleRequestFor(input);

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

	private final static String getTestDataSecenarioOne(String buyInAmount) {
		return new StringBuffer(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append(buyInAmount)
				.toString();
	}

	private final static String getTestDataSecenarioTwo(String buyInAmount) {
		return new StringBuffer("B1").append("\n").append(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append(buyInAmount)
				.toString();
	}
}
