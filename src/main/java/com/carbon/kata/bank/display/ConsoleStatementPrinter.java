package com.carbon.kata.bank.display;

import java.util.List;

import com.carbon.kata.bank.account.Operation;

public class ConsoleStatementPrinter implements StatementPrinter {

	private final OperationFormatter operationFormatter;

	public ConsoleStatementPrinter(OperationFormatter operationFormatter) {
		this.operationFormatter = operationFormatter;
	}

	@Override
	public void printStatement(List<Operation> operationList) {
		if (operationList == null || operationList.isEmpty()) {
			return;
		}

		System.out.println(operationFormatter.getHeader());
		operationList.forEach(o -> System.out.println(operationFormatter.formatOperation(o)));
	}
}
