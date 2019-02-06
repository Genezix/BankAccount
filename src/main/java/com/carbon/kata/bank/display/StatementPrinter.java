package com.carbon.kata.bank.display;

import java.util.List;

import com.carbon.kata.bank.account.Operation;

public interface StatementPrinter {
	void printStatement(List<Operation> operationList);
}
