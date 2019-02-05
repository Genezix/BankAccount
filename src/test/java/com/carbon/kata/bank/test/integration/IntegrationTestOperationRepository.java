package com.carbon.kata.bank.test.integration;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.carbon.kata.bank.account.Operation;
import com.carbon.kata.bank.account.OperationRepository;

public class IntegrationTestOperationRepository implements OperationRepository {
	private LinkedList<Operation> operationList = new LinkedList<>();

	@Override
	public void add(Operation operation) {
		operationList.add(operation);
	}

	@Override
	public List<Operation> find() {
		return operationList;
	}

	@Override
	public Optional<Operation> getLast() {
		if (operationList.isEmpty()) {
			return Optional.ofNullable(null);
		}

		return Optional.ofNullable(operationList.getLast());
	}
}
