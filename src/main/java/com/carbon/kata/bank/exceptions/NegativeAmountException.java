package com.carbon.kata.bank.exceptions;

import java.math.BigDecimal;

public class NegativeAmountException extends Exception {
	private static final long serialVersionUID = 1224419502041068349L;

	public NegativeAmountException(BigDecimal amount) {
		super("Amount should be positive : " + amount);
	}
}
