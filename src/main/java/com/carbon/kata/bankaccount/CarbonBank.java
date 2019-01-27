package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.time.Clock;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.carbon.kata.bankaccount.exception.ClientAccountAlreadyExists;
import com.carbon.kata.bankaccount.exception.ClientAccountDoesNotExists;

public class CarbonBank implements Bank {

	private Map<String, LinkedList<AccountOperation>> clientAccounts = new HashMap<>();
	private final Clock clock;

	public CarbonBank(Clock clock) {
		this.clock = clock;
	}

	@Override
	public Bank addClient(String clientName) throws ClientAccountAlreadyExists {
		if (clientAccounts.containsKey(clientName)) {
			throw new ClientAccountAlreadyExists(clientName);
		}

		LinkedList<AccountOperation> operations = new LinkedList<>();
		operations.add(new AccountOperation(clock.millis(), BigInteger.valueOf(0), BigInteger.valueOf(0)));
		clientAccounts.put(clientName, operations);
		return this;
	}

	@Override
	public Bank operationOnClientAccount(String clientName, BigInteger amount) throws ClientAccountDoesNotExists {
		if (!clientAccounts.containsKey(clientName)) {
			throw new ClientAccountDoesNotExists(clientName);
		}

		BigInteger newBalance = getClientAccountBalance(clientName).add(amount);

		if (newBalance.compareTo(BigInteger.valueOf(0)) >= 0) {
			clientAccounts.get(clientName).add(new AccountOperation(clock.millis(), amount, newBalance));
		}

		return this;
	}

	@Override
	public BigInteger getClientAccountBalance(String clientName) {
		return clientAccounts.get(clientName).getLast().getBalance();
	}

	@Override
	public List<String> getOperationsHistoricOnClientAccount(String clientName) {
		List<String> operationsStrings = new LinkedList<>();

		LinkedList<AccountOperation> operations = clientAccounts.get(clientName);
		if (operations != null) {
			operations.forEach(o -> operationsStrings.add(o.toString()));
		}

		return operationsStrings;
	}
}
