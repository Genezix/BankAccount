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

	private Map<String, LinkedList<Operation>> clientAccounts = new HashMap<>();
	private final Clock clock;

	CarbonBank(Clock clock) {
		this.clock = clock;
	}

	@Override
	public Bank addClient(String clientName) {
		LinkedList<Operation> operations = new LinkedList<>();
		operations.add(Operation.buildDepositOperation(clock.millis(), BigDecimal.ZERO, BigDecimal.ZERO));
		clientAccounts.put(clientName, operations);
		return this;
	}

	@Override
	public BigDecimal withdrawal(String clientName, BigDecimal amount) {
		BigDecimal previousBalance = getBalance(clientName);

		if (previousBalance.compareTo(BigDecimal.valueOf(-1)) == 0) {
			return previousBalance;
		}

		BigDecimal newBalance = previousBalance.subtract(amount);

		if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
			clientAccounts.get(clientName).add(Operation.buildWithDrawalOperation(clock.millis(), amount, newBalance));
		}

		return newBalance;
	}

	@Override
	public BigDecimal deposit(String clientName, BigDecimal amount) {
		BigDecimal previousBalance = getBalance(clientName);

		if (previousBalance.compareTo(BigDecimal.valueOf(-1)) == 0) {
			return previousBalance;
		}

		BigDecimal newBalance = previousBalance.add(amount);

		clientAccounts.get(clientName).add(Operation.buildDepositOperation(clock.millis(), amount, newBalance));

		return newBalance;
	}

	@Override
	public BigDecimal getBalance(String clientName) {
		if (!clientAccounts.containsKey(clientName)) {
			return BigDecimal.valueOf(-1);
		}

		return clientAccounts.get(clientName).getLast().getBalance();
	}

	@Override
	public List<DisplayableData> getOperations(String clientName) {
		LinkedList<Operation> list = clientAccounts.get(clientName);
		if (list == null) {
			return Collections.emptyList();
		}
		return new LinkedList<>(list);
	}
}
