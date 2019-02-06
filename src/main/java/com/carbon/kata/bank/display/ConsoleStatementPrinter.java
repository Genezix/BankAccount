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
		System.out.println(operationFormatter.getHeader());

		if (operationList == null || operationList.isEmpty()) {
			return;
		}
		
		operationList.forEach(o -> System.out.println(operationFormatter.formatOperation(o)));
	}
}
