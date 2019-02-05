package com.carbon.kata.bank.exceptions;

import java.math.BigDecimal;

public class NotEnoughMoneyException extends RuntimeException {

	private static final long serialVersionUID = -7967392745963070912L;

	public NotEnoughMoneyException(BigDecimal amount, BigDecimal balance) {
		super("Try to withdraw " + amount + " but the account contains only " + balance);
	}
}
