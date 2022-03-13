package com.hexad.library.management.exception;

public class UserExceededBookCreditLimitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserExceededBookCreditLimitException(String msg) {
		super(msg);
	}

}
