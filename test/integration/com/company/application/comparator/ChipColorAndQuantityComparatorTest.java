package com.company.application.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.company.application.comparator.ChipColorAndQuantityComparator;
import com.company.application.domain.ChipColorAndQuantity;

public class ChipColorAndQuantityComparatorTest {

	@Test
	public void shouldOrderByQuantityInAscendingOrder() {

		int quantity1 = 25;
		int quantity2 = 30;
		int quantity3 = 15;

		List<ChipColorAndQuantity> list = new ArrayList<ChipColorAndQuantity>();
		list.add(new ChipColorAndQuantity("Red", quantity1));
		list.add(new ChipColorAndQuantity("Black", quantity2));
		list.add(new ChipColorAndQuantity("Blue", quantity3));

		Collections.sort(list, new ChipColorAndQuantityComparator());

		Assert.assertEquals(quantity3, list.get(0).getQuantity());
		Assert.assertEquals(quantity1, list.get(1).getQuantity());
		Assert.assertEquals(quantity2, list.get(2).getQuantity());
	}
}
