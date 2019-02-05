package com.carbon.kata.bank.test.integration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bank.account.Account;

class IntegrationAccountTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;
	@Test
	void receiveOperationListWhenAccountHaveToPrintStatement() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		// 1548718177581 = 29-01-2019 00:29
		Clock clock = Clock.fixed(Instant.ofEpochMilli(1548718177581l), ZoneId.systemDefault());
		
		StringBuilder builder = new StringBuilder();
		builder.append(" | Operation  | Date             | Amount | Balance | " + System.lineSeparator());
		builder.append(" | Deposit    | 29-01-2019 00:29 | 10     | 10      | " + System.lineSeparator());
		builder.append(" | Withdrawal | 29-01-2019 00:29 | 5      | 5       | " + System.lineSeparator());
		builder.append(" | Deposit    | 29-01-2019 00:29 | 20     | 25      | " + System.lineSeparator());
		builder.append(" | Withdrawal | 29-01-2019 00:29 | 15     | 10      | " + System.lineSeparator());
		
		Account account = new Account(new IntegrationTestOperationRepository(), clock);
		
		account.depositMoney(BigDecimal.valueOf(10));
		account.withdrawMoney(BigDecimal.valueOf(5));
		account.depositMoney(BigDecimal.valueOf(20));
		account.withdrawMoney(BigDecimal.valueOf(15));
		
		account.printStatement(new IntegrationTestConsolePrinter());
		
		System.setOut(originalOut);
		System.setErr(originalErr);
		
		Assertions.assertEquals(builder.toString(), outContent.toString());
	}
}
