package com.carbon.kata.bank.account;

import java.math.BigDecimal;
import java.util.Objects;

public class Operation {
	private final long time;
	private final OperationType type;
	private final BigDecimal amount;
	private final BigDecimal balance;

	public static Operation ofDeposit(long time, BigDecimal operationAmount, BigDecimal balance) {
		return new Operation(time, operationAmount, balance, OperationType.DEPOSIT);
	}

	public static Operation ofWithdrawal(long time, BigDecimal operationAmount, BigDecimal balance) {
		return new Operation(time, operationAmount, balance, OperationType.WITHDRAWAL);
	}

	private Operation(long time, BigDecimal amount, BigDecimal balance, OperationType type) {
		this.time = time;
		this.amount = amount;
		this.balance = balance;
		this.type = type;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public long getTime() {
		return time;
	}

	public OperationType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, balance, time, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(balance, other.balance) && time == other.time
				&& type == other.type;
	}


}
