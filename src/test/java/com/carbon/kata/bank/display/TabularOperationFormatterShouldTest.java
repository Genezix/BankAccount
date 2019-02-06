package com.carbon.kata.bank.display;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.carbon.kata.bank.account.Operation;

class TabularOperationFormatterShouldTest {

	@DisplayName("Test operation format")
	void formatAnOperation() {
		final var expectedFormatedOperation = " | Deposit    | 01-01-1970 01:00 | 10     | 100     | ";
		final var formatter = new TabularOperationFormatter();
		final var operation = Operation.ofDeposit(10l, new BigDecimal("10"), new BigDecimal("100"));

		// Act
		final var formatedOperation = formatter.formatOperation(operation);

		// Assert
		Assertions.assertEquals(expectedFormatedOperation, formatedOperation);
	}
}
