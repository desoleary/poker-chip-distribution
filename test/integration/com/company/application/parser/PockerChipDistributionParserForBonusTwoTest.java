package com.company.application.parser;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.domain.PokerChipDistributionForBonusTwo;
import com.company.application.exception.ParserException;

public class PockerChipDistributionParserForBonusTwoTest {
	
	@Test
	public void shouldParseReturnPokerChipDistribution() throws IOException,
			ParserException {
		String expectedColor = "Red";
		int expectedPlayerCount = 10;
		int expectedQuantity = 50; 
		double buyInAmount = 10.00;
		
		PokerChipDistributionForBonusTwo result = new PockerChipDistributionParserForBonusTwo().parse(getTestDataSecenarioThree());
		
		Assert.assertEquals(expectedColor, result.getChipDetailsList().get(0).getColor());
		Assert.assertEquals(expectedQuantity, result.getChipDetailsList().get(0).getQuantity());
		Assert.assertEquals(expectedPlayerCount, result.getPlayerCount());
		Assert.assertEquals(buyInAmount, result.getBuyInAmount());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowParseExceptionWhenInputIsEmpty() throws IOException,
			ParserException {
		new PockerChipDistributionParserForBonusTwo().parse("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowParseExceptionWhenInputIsNull() throws IOException,
			ParserException {
		new PockerChipDistributionParserForBonusTwo().parse(null);
	}
	
	@Test(expected=ParserException.class)
	public void shouldThrowParseExceptionWhenInputHasLessThanThreeLines() throws IOException,
			ParserException {
		new PockerChipDistributionParserForBonusTwo().parse("some line");
	}
	
	@Test(expected=ParserException.class)
	public void shouldThrowParseExceptionWhenInputHasGreaterThanFourLines() throws IOException,
			ParserException {
		new PockerChipDistributionParserForBonusTwo().parse("\n\n\n\n\n");
	}
	
	private final static String getTestDataSecenarioThree() {
		return new StringBuffer("B2").append("\n").append(
				"50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe")
				.append("\n").append("10").append("\n").append("$10.00")
				.toString();
	}
}
