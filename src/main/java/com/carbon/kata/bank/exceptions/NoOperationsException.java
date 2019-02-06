package com.carbon.kata.bank.exceptions;

public class NoOperationsException extends Exception {

	private static final long serialVersionUID = -6795476244607862836L;

	public NoOperationsException(String message) {
		super(message);
	}
}
