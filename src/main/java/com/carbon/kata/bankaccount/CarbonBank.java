package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class CarbonBank implements Bank {
	private Map<String, BigInteger> clientAccounts = new HashMap<>();

	public Bank addClient(String clientName) {
		clientAccounts.put(clientName, BigInteger.valueOf(0));
		return this;
	}

	public Bank depositOnClientAccount(String clientName, BigInteger amountToDeposit) {
		clientAccounts.put(clientName, clientAccounts.get(clientName).add(amountToDeposit));
		return this;
	}

	public Bank WithdrawalOnClientAccount(String clientName, BigInteger amountToWithdrawal) {
		clientAccounts.put(clientName, clientAccounts.get(clientName).subtract(amountToWithdrawal));
		return this;
	}

	public BigInteger getClientAccountBalance(String clientName) {
		return clientAccounts.get(clientName);
	}

}
