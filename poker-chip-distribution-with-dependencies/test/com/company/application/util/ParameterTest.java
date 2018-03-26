package com.company.application.util;

import junit.framework.Assert;

import org.junit.Test;

public class ParameterTest {
	
	@Test
	public void isIntegerFor_shouldReturnTrueWhenGivenIntegerValues(){
		Assert.assertTrue(Parameter.isIntegerFor("2"));
	}
	
	@Test
	public void isIntegerFor_shouldReturnFalseWhenGivenDoubleFormatValues(){
		Assert.assertFalse(Parameter.isIntegerFor("2.00"));
	}
	
	@Test
	public void isIntegerFor_shouldReturnFalseWhenGivenCharacters(){
		Assert.assertFalse(Parameter.isIntegerFor("$2.00"));
	}
	
	@Test
	public void isIntegerFor_shouldReturnFalseWhenEmpty(){
		Assert.assertFalse(Parameter.isIntegerFor(""));
	}
	
	@Test
	public void isIntegerFor_shouldReturnFalseWhenNull(){
		Assert.assertFalse(Parameter.isIntegerFor(null));
	}
	
	@Test
	public void hasValidNumericMonetaryValueFor_shouldReturnTrueWhenGivenValidMonetaryValue(){
		Assert.assertTrue(Parameter.hasValidNumericMonetaryValueFor("$2.00"));
	}
	
	@Test
	public void hasValidNumericMonetaryValueFor_shouldReturnFalseWhenGivenValidMonetaryValuWithGreaterThanTwoDecimalPlaces(){
		Assert.assertFalse(Parameter.hasValidNumericMonetaryValueFor("$2.00000"));
	}
	
	@Test
	public void hasValidNumericMonetaryValueFor_shouldReturnFalseWhenGivenValidMonetaryValuWithLessThanTwoDecimalPlaces(){
		Assert.assertFalse(Parameter.hasValidNumericMonetaryValueFor("$2.0"));
	}
	
	@Test
	public void hasValidNumericMonetaryValueFor_shouldReturnFalseWhenMissingDollarCharacter(){
		Assert.assertFalse(Parameter.hasValidNumericMonetaryValueFor("2.00"));
	}
}
