package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.time.Clock;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.carbon.kata.bankaccount.display.DisplayableData;

public class CarbonBank implements Bank {

	private Map<String, LinkedList<AccountOperation>> clientAccounts = new HashMap<>();
	private final Clock clock;

	CarbonBank(Clock clock) {
		this.clock = clock;
	}

	@Override
	public Bank addClient(String clientName) {
		LinkedList<AccountOperation> operations = new LinkedList<>();
		operations.add(new AccountOperation(clock.millis(), BigInteger.valueOf(0), BigInteger.valueOf(0)));
		clientAccounts.put(clientName, operations);
		return this;
	}

	/*
	 * return balance after applying operation.
	 * 
	 * return -1 if client does not exists.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.carbon.kata.bankaccount.Bank#depositOrWithdrawalOnClientAccount(java.lang
	 * .String, java.math.BigInteger)
	 */
	@Override
	public BigInteger depositOrWithdrawalOnClientAccount(String clientName, BigInteger amount) {
		BigInteger previousBalance = getClientAccountBalance(clientName);

		if (previousBalance.compareTo(BigInteger.valueOf(-1)) == 0) {
			return previousBalance;
		}

		BigInteger newBalance = previousBalance.add(amount);

		if (newBalance.compareTo(BigInteger.valueOf(0)) >= 0) {
			clientAccounts.get(clientName).add(new AccountOperation(clock.millis(), amount, newBalance));
		}

		return newBalance;
	}

	@Override
	public BigInteger getClientAccountBalance(String clientName) {
		if (!clientAccounts.containsKey(clientName)) {
			return BigInteger.valueOf(-1);
		}

		return clientAccounts.get(clientName).getLast().getBalance();
	}

	@Override
	public List<DisplayableData> getOperationsHistoricOnClientAccount(String clientName) {
		LinkedList<AccountOperation> list = clientAccounts.get(clientName);
		if (list == null) {
			return Collections.emptyList();
		}
		return new LinkedList<>(list);
	}
}
