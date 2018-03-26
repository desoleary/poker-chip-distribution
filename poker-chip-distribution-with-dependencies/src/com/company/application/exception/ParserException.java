package com.company.application.exception;

/**
 * General purpose Exception for errors that occur during parsing
 * 
 * @author Desmond O'Leary
 * 
 */
public class ParserException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParserException(final String string) {
		super(string);
	}
}
