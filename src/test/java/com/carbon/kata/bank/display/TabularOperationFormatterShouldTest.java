package com.carbon.kata.bank.display;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bank.account.Operation;

class TabularOperationFormatterShouldTest {

	@Test
	@DisplayName("Test to format a deposit operation")
	void formatADepositOperation() {
		final var expectedFormatedOperation = "Deposit    | 01-01-1970 01:00 | 10     | 100    ";
		final var formatter = new TabularOperationFormatter();
		final var operation = Operation.ofDeposit(10l, new BigDecimal("10"), new BigDecimal("100"));

		// Act
		final var formatedOperation = formatter.formatOperation(operation);

		// Assert
		Assertions.assertEquals(expectedFormatedOperation, formatedOperation);
	}

	@Test
	@DisplayName("Test to format a withdrawal operation")
	void formatAWithdrawalOperation() {
		final var expectedFormatedOperation = "Withdrawal | 01-01-1970 01:00 | 10     | 100    ";
		final var formatter = new TabularOperationFormatter();
		final var operation = Operation.ofWithdrawal(10l, new BigDecimal("10"), new BigDecimal("100"));

		// Act
		final var formatedOperation = formatter.formatOperation(operation);

		// Assert
		Assertions.assertEquals(expectedFormatedOperation, formatedOperation);
	}
}
