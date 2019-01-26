package com.carbon.kata.bankaccount;

import java.math.BigInteger;

public class AccountOperation {

	private long operationTime;
	private BigInteger operationAmount;
	private BigInteger balance;

	public AccountOperation(long time, BigInteger operationAmount, BigInteger balance) {
		this.operationTime = time;
		this.operationAmount = operationAmount;
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "AccountOperation [operationTime=" + operationTime + ", operationAmount=" + operationAmount + ", balance="
				+ balance + "]";
	}

	public long getOperationTime() {
		return operationTime;
	}

	public BigInteger getOperationAmount() {
		return operationAmount;
	}

	public BigInteger getBalance() {
		return balance;
	}
}
