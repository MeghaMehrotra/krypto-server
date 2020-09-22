package com.assignment.krypto.exception.custom;

import org.springframework.http.HttpStatus;

public class MindbowserMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final HttpStatus type;

	/**
	 * @return the type
	 */
	public HttpStatus getType() {
		return type;
	}


	public MindbowserMessageException(String message, HttpStatus type) {
		super(message);
		this.type = type;

	}

}
