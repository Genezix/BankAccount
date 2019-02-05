package com.carbon.kata.bank.display;

import java.util.List;

import com.carbon.kata.bank.account.Operation;

public interface StatementPrinter {
	public void printStatement(List<Operation> operationList);
}
