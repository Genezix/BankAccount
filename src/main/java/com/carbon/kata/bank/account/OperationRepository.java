package com.carbon.kata.bank.account;

import java.util.List;
import java.util.Optional;

public interface OperationRepository {
	void add(Operation operation);

	List<Operation> findAll();

	Optional<Operation> getLast();
}
