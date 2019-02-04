package com.carbon.kata.bankaccount.display;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.carbon.kata.bankaccount.bank.Operation;

public interface OperationsDisplay {
	public void displayOperations(String clientName, List<Operation> operations);

	public void displayAccountBalance(String clientName, Optional<BigDecimal> optional);
}
