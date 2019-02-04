package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	public Optional<BigDecimal> withdrawal(String clientName, BigDecimal amount) {
		Optional<BigDecimal> previousBalance = getBalance(clientName);
		BigDecimal newBalance = null;

		if (previousBalance.isPresent()) {
			newBalance = previousBalance.get().subtract(amount);

			if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
				clientAccounts.get(clientName).add(Operation.buildWithDrawalOperation(clock.millis(), amount, newBalance));
			}
		}

		return Optional.ofNullable(newBalance);
	}

	@Override
	public Optional<BigDecimal> deposit(String clientName, BigDecimal amount) {
		Optional<BigDecimal> previousBalance = getBalance(clientName);

		BigDecimal newBalance = null;

		if (previousBalance.isPresent()) {
			newBalance = previousBalance.get().add(amount);
			clientAccounts.get(clientName).add(Operation.buildDepositOperation(clock.millis(), amount, newBalance));
		}

		return Optional.ofNullable(newBalance);
	}

	@Override
	public Optional<BigDecimal> getBalance(String clientName) {
		BigDecimal balance = null;
		if (clientAccounts.containsKey(clientName)) {
			balance = clientAccounts.get(clientName).getLast().getBalance();
		}

		return Optional.ofNullable(balance);
	}

	@Override
	public List<Operation> getOperations(String clientName) {
		LinkedList<Operation> list = clientAccounts.get(clientName);
		if (list == null) {
			return Collections.emptyList();
		}
		return new LinkedList<>(list);
	}
}
