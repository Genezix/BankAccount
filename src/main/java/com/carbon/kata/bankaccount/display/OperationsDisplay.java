package com.carbon.kata.bankaccount.display;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OperationsDisplay {
	public void displayOperations(String clientName, List<DisplayableData> operations);

	public void displayAccountBalance(String clientName, Optional<BigDecimal> optional);
}
