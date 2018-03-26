/*
 * @(#)PockerChipDistributionParserBase.java        2010/06/03
 *
 * Copyright (c) 2010 XXXXXX
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of XXXXXXXX
 * ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with XXXXXX.
 */
package com.company.application.parser;

import java.util.regex.Pattern;

/**
 * Abstracts common functionality between poker chip distribution parsers.
 * 
 * @author Desmond O'Leary
 * 
 */
public class PockerChipDistributionParserBase {
	/** regex that matches on new line characters. */
	protected static final Pattern NEW_LINE_REGEX = Pattern.compile("[\n|\r]");
}
