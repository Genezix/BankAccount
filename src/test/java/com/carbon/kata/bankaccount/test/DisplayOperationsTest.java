package com.carbon.kata.bankaccount.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.bank.ProxyCarbonBank;
import com.carbon.kata.bankaccount.display.ConsoleDisplay;

class DisplayOperationsTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Test
	void testToPrinterOperations() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		// 1548718177581 = 29-01-2019 00:29
		Clock clock = Clock.fixed(Instant.ofEpochMilli(1548718177581l), ZoneId.systemDefault());

		String clientName = "Bat man";
		ProxyCarbonBank bank = new ProxyCarbonBank(clock, new ConsoleDisplay());

		bank.addClient(clientName);

		StringBuilder builder = new StringBuilder();
		builder.append(" | Operation  | Date             | Amount | Balance | " + System.lineSeparator());
		builder.append(" | Deposit    | 29-01-2019 00:29 | 0      | 0       | " + System.lineSeparator());
		builder.append(" | Deposit    | 29-01-2019 00:29 | 10     | 10      | " + System.lineSeparator());
		builder.append(" | Withdrawal | 29-01-2019 00:29 | 5      | 5       | " + System.lineSeparator());
		builder.append(" | Deposit    | 29-01-2019 00:29 | 20     | 25      | " + System.lineSeparator());
		builder.append(" | Withdrawal | 29-01-2019 00:29 | 15     | 10      | " + System.lineSeparator());

		bank.deposit(clientName, BigDecimal.valueOf(10));
		bank.withdrawal(clientName, BigDecimal.valueOf(5));
		bank.deposit(clientName, BigDecimal.valueOf(20));
		bank.withdrawal(clientName, BigDecimal.valueOf(15));

		bank.displayOperations(clientName);

		System.setOut(originalOut);
		System.setErr(originalErr);

		Assertions.assertEquals(builder.toString(), outContent.toString());
	}

	@Test
	void testToPrintBalance() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		String clientName = "Bat man";
		ProxyCarbonBank bank = new ProxyCarbonBank(new ConsoleDisplay());
		BigDecimal balance = BigDecimal.valueOf(10);

		bank.addClient(clientName);
		bank.deposit(clientName, balance);

		bank.displayAccountBalance(clientName);

		System.setOut(originalOut);
		System.setErr(originalErr);

		Assertions.assertEquals(clientName + " account balance = " + balance.toString() + System.lineSeparator(), outContent.toString());
	}
}
