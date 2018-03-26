package com.company.application.handler;

import junit.framework.Assert;

import org.junit.Test;

public class RequestHandlerFactoryTest {

	@Test
	public void shouldReturnRequestHandlerWhenGivenValidInputForScenarioOne()
			throws IllegalAccessException {
		IRequestHandler handler = RequestHandlerFactory
				.createRequstHandlerFor(getTestDataSecenarioOne());
		Assert.assertTrue("handler is not an instance of RequestHandler",
				handler instanceof RequestHandler);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenInputISEmpty()
			throws IllegalAccessException {
		RequestHandlerFactory.createRequstHandlerFor("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenInputISNull()
			throws IllegalAccessException {
		RequestHandlerFactory.createRequstHandlerFor(null);
	}

	@Test
	public void shouldReturnRequestHandlerForBonusOneWhenGivenInputScenarioTwo()
			throws IllegalAccessException {
		IRequestHandler handler = RequestHandlerFactory
				.createRequstHandlerFor(getTestDataSecenarioTwo());
		Assert.assertTrue("handler is not an instance of RequestHandler",
				handler instanceof RequestHandler);
	}
	
	@Test
	public void shouldReturnRequestHandlerForBonusOneWhenGivenInputScenarioThree()
			throws IllegalAccessException {
		IRequestHandler handler = RequestHandlerFactory
				.createRequstHandlerFor(getTestDataSecenarioThree());
		Assert.assertTrue("handler is not an instance of RequestHandler",
				handler instanceof RequestHandlerForBonusTwo);
	}

	private final static String getTestDataSecenarioOne() {
		return new StringBuffer(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append("$10.00")
				.toString();
	}

	private final static String getTestDataSecenarioTwo() {
		return new StringBuffer("B1").append("\n").append(
				"50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05")
				.append("\n").append("10").append("\n").append("$10.00")
				.toString();
	}
	
	private final static String getTestDataSecenarioThree() {
		return new StringBuffer("B2").append("\n").append(
				"50/Red,50/Blue,100/Black,100/Green,100/Yellow,100/Taupe")
				.append("\n").append("10").append("\n").append("$10.00")
				.toString();
	}
}
