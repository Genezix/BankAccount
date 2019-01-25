package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import exception.ClientAccountAlreadyExists;
import exception.ClientAccountDoesNotExists;

public class CarbonBank implements Bank {
	private Map<String, BigInteger> clientAccounts = new HashMap<>();

	public Bank addClient(String clientName) throws ClientAccountAlreadyExists {
		if (clientAccounts.containsKey(clientName)) {
			throw new ClientAccountAlreadyExists(clientName);
		}

		clientAccounts.put(clientName, BigInteger.valueOf(0));
		return this;
	}

	public Bank depositOnClientAccount(String clientName, BigInteger amountToDeposit) throws ClientAccountDoesNotExists {
		if (!clientAccounts.containsKey(clientName)) {
			throw new ClientAccountDoesNotExists(clientName);
		}

		clientAccounts.put(clientName, clientAccounts.get(clientName).add(amountToDeposit));
		return this;
	}

	public Bank withdrawalOnClientAccount(String clientName, BigInteger amountToWithdrawal) throws ClientAccountDoesNotExists {
		if (!clientAccounts.containsKey(clientName)) {
			throw new ClientAccountDoesNotExists(clientName);
		}

		if (getClientAccountBalance(clientName).subtract(amountToWithdrawal).compareTo(BigInteger.valueOf(0)) >= 0) {
			clientAccounts.put(clientName, clientAccounts.get(clientName).subtract(amountToWithdrawal));
		}
		return this;
	}

	public BigInteger getClientAccountBalance(String clientName) {
		return clientAccounts.get(clientName);
	}

}
