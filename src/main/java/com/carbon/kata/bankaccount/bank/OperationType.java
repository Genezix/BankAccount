package com.carbon.kata.bankaccount.bank;

public enum OperationType {
	DEPOSIT("Deposit"), WISTHDRAWAL("Withdrawal");

	private final String value;

	private OperationType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
