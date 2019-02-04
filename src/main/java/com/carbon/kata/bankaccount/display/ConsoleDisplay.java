package com.carbon.kata.bankaccount.display;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.carbon.kata.bankaccount.bank.Operation;

public class ConsoleDisplay implements OperationsDisplay {

	@Override
	public void displayOperations(String clientName, List<Operation> operations) {
		if (operations != null && !operations.isEmpty()) {
			System.out.println(Operation.HEADER);
			operations.forEach(o -> System.out.println(o.getOperationsAsString()));
		}
	}

	@Override
	public void displayAccountBalance(String clientName, Optional<BigDecimal> balance) {
		if (balance.isPresent()) {
			System.out.println(clientName + " account balance = " + balance.get());
		} else {
			System.out.println(clientName + " does not exists in the bank");
		}
	}
}
