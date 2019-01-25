package com.carbon.kata.bankaccount;

import java.math.BigInteger;

public interface Bank {
	public Bank addClient(String clientName);

	public Bank depositOnClientAccount(String clientName, BigInteger amountToDeposit);

	public Bank WithdrawalOnClientAccount(String clientName, BigInteger amountToWithdrawal);

	public BigInteger getClientAccountBalance(String clientName);
}
