package com.carbon.kata.bank.exceptions;

import java.math.BigDecimal;

public class NotEnoughMoneyException extends Exception {

	private static final long serialVersionUID = -7967392745963070912L;

	public NotEnoughMoneyException(BigDecimal amount, BigDecimal balance) {
		super("Tried to withdraw " + amount + " but the account contains only " + balance);
	}
}
