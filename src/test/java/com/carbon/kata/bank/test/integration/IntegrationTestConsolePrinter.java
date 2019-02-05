package com.carbon.kata.bank.test.integration;

import java.util.List;

import com.carbon.kata.bank.account.Operation;
import com.carbon.kata.bank.display.StatementPrinter;

public class IntegrationTestConsolePrinter implements StatementPrinter {
	@Override
	public void printStatement(List<Operation> operationList) {
		if (operationList != null && !operationList.isEmpty()) {
			var formatter = new IntegrationTestOperationFormatter();
			System.out.println(IntegrationTestOperationFormatter.HEADER);
			operationList.forEach(o -> System.out.println(formatter.formatOperation(o)));
		}
	}
}
