package com.carbon.kata.bank.display;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bank.account.Operation;

class TabularOperationFormatterShouldTest {

	@Test
	@DisplayName("Test operation format")
	void formatAnOperation() {
		final var expectedFormatedOperation = " | Deposit    | 01-01-1970 01:00 | 10     | 100     | ";
		final var formatter = new TabularOperationFormatter();
		final var operation = Operation.ofDeposit(10l, BigDecimal.valueOf(10), BigDecimal.valueOf(100));

		// Act
		final var formatedOperation = formatter.formatOperation(operation);

		// Assert
		Assertions.assertEquals(expectedFormatedOperation, formatedOperation);
	}
}
