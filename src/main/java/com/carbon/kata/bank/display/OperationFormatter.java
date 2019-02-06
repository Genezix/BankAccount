package com.carbon.kata.bank.display;

import com.carbon.kata.bank.account.Operation;

public interface OperationFormatter {
	String getHeader();

	String formatOperation(Operation operation);
}
