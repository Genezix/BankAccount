package com.carbon.kata.bank.account;

public enum OperationType {
	DEPOSIT("Deposit"), WITHDRAWAL("Withdrawal");

	private final String value;

	private OperationType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
