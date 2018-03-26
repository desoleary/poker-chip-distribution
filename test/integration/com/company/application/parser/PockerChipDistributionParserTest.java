package com.company.application.parser;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.domain.PokerChipDistribution;
import com.company.application.exception.ParserException;

public class PockerChipDistributionParserTest {

	@Test
	public void shouldParseReturnPokerChipDistribution() throws IOException,
			ParserException {
		double expectedDenomination = 2.00;
		int expectedPlayerCount = 10;
		int expectedQuantity = 50; // original input quantity divided by number of players.
		double buyInAmount = 10.00;
		
		PokerChipDistribution result = new PockerChipDistributionParser().parse(getTestDataSecenarioOne());
		
		Assert.assertEquals(expectedDenomination, result.getChipDetailsList().get(0).getDenomination());
		Assert.assertEquals(expectedQuantity, result.getChipDetailsList().get(0).getQuantity());
		Assert.assertEquals(expectedPlayerCount, result.getPlayerCount());
		Assert.assertEquals(buyInAmount, result.getBuyInAmount());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowParseExceptionWhenInputIsEmpty() throws IOException,
			ParserException {
		new PockerChipDistributionParser().parse("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowParseExceptionWhenInputIsNull() throws IOException,
			ParserException {
		new PockerChipDistributionParser().parse(null);
	}
	
	@Test(expected=ParserException.class)
	public void shouldThrowParseExceptionWhenInputHasLessThanThreeLines() throws IOException,
			ParserException {
		new PockerChipDistributionParser().parse("some line");
	}
	
	@Test(expected=ParserException.class)
	public void shouldThrowParseExceptionWhenInputHasGreaterThanThreeLines() throws IOException,
			ParserException {
		new PockerChipDistributionParser().parse("\n\n\n\n");
	}
	
	private final static String getTestDataSecenarioOne() {
		return new StringBuffer(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append("$10.00")
				.toString();
	}
}
