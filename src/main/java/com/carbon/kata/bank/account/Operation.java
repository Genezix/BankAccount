package com.carbon.kata.bank.account;

import java.math.BigDecimal;

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
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
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (time != other.time)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
