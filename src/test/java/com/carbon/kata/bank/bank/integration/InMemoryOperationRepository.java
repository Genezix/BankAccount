package com.carbon.kata.bank.bank.integration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.carbon.kata.bank.account.Operation;
import com.carbon.kata.bank.account.OperationRepository;

public class InMemoryOperationRepository implements OperationRepository {
	private final LinkedList<Operation> operationList = new LinkedList<>();

	@Override
	public void add(Operation operation) {
		operationList.add(operation);
	}

	@Override
	public List<Operation> find() {
		return Collections.unmodifiableList(operationList);
	}

	@Override
	public Optional<Operation> getLast() {
		if (operationList.isEmpty()) {
			return Optional.empty();
		}

		return Optional.ofNullable(operationList.getLast());
	}
}
