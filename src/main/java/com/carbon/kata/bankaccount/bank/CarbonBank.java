package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
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
		operations.add(new AccountOperation(clock.millis(), BigDecimal.ZERO, BigDecimal.ZERO));
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
	 * .String, java.math.BigDecimal)
	 */
	@Override
	public BigDecimal depositOrWithdrawalOnClientAccount(String clientName, BigDecimal amount) {
		BigDecimal previousBalance = getClientAccountBalance(clientName);

		if (previousBalance.compareTo(BigDecimal.valueOf(-1)) == 0) {
			return previousBalance;
		}

		BigDecimal newBalance = previousBalance.add(amount);

		if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
			clientAccounts.get(clientName).add(new AccountOperation(clock.millis(), amount, newBalance));
		}

		return newBalance;
	}

	@Override
	public BigDecimal getClientAccountBalance(String clientName) {
		if (!clientAccounts.containsKey(clientName)) {
			return BigDecimal.valueOf(-1);
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
