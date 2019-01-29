package com.carbon.kata.bankaccount.display;

import java.math.BigDecimal;
import java.util.List;

public interface OperationsDisplay {
	public void displayOperations(List<DisplayableData> operations);

	public void displayAccountBalance(BigDecimal balance);
}
