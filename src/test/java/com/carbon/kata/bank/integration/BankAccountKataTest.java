package com.carbon.kata.bank.integration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.carbon.kata.bank.account.Account;
import com.carbon.kata.bank.display.ConsoleStatementPrinter;
import com.carbon.kata.bank.display.TabularOperationFormatter;
import com.carbon.kata.bank.exceptions.NegativeAmountException;
import com.carbon.kata.bank.exceptions.NotEnoughMoneyException;

class BankAccountKataTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@BeforeEach
	void init() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@Test
	@DisplayName("Integration test with console printer")
	void receiveOperationListWhenAccountHaveToPrintStatement() throws NegativeAmountException, NotEnoughMoneyException {
		// 1548718177581 = 29-01-2019 00:29
		final var clock = Clock.fixed(Instant.ofEpochMilli(1548718177581l), ZoneId.systemDefault());

		final var builder = new StringBuilder();
		builder.append(" | Operation  | Date             | Amount | Balance | " + System.lineSeparator());
		builder.append(" | Deposit    | 29-01-2019 00:29 | 10     | 10      | " + System.lineSeparator());
		builder.append(" | Withdrawal | 29-01-2019 00:29 | 5      | 5       | " + System.lineSeparator());
		builder.append(" | Deposit    | 29-01-2019 00:29 | 20     | 25      | " + System.lineSeparator());
		builder.append(" | Withdrawal | 29-01-2019 00:29 | 1.4    | 23.6    | " + System.lineSeparator());

		final var expectedStatement = builder.toString();

		// Act
		final var account = new Account(new InMemoryOperationRepository(), clock);

		account.depositMoney(new BigDecimal("10"));
		account.withdrawMoney(new BigDecimal("5"));
		account.depositMoney(new BigDecimal("20"));
		account.withdrawMoney(new BigDecimal("1.4"));

		account.printStatement(new ConsoleStatementPrinter(new TabularOperationFormatter()));

		// Assert
		Assertions.assertEquals(expectedStatement, outContent.toString());
	}
	
	@AfterEach
    void tearDown(TestInfo testInfo) {
		System.setOut(originalOut);
		System.setErr(originalErr);
    }
}
