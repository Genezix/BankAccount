package com.carbon.kata.bank.display;

import java.util.List;
import java.util.ListIterator;

import com.carbon.kata.bank.account.Operation;

public class ConsoleStatementPrinter implements StatementPrinter {

	private final OperationFormatter operationFormatter;

	public ConsoleStatementPrinter(OperationFormatter operationFormatter) {
		this.operationFormatter = operationFormatter;
	}

	@Override
	public void printStatement(List<Operation> operationList) {
		System.out.println(operationFormatter.getHeader());

		if (operationList == null || operationList.isEmpty()) {
			return;
		}

		// Print operations starting by the last operation
		ListIterator<Operation> listIterator = operationList.listIterator(operationList.size());
		while (listIterator.hasPrevious()) {
			System.out.println(operationFormatter.formatOperation(listIterator.previous()));
		}
	}
}
