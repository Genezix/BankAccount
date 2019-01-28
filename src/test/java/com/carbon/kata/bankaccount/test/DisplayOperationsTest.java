package com.carbon.kata.bankaccount.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.ProxyCarbonBank;
import com.carbon.kata.bankaccount.display.DisplayManager;
import com.carbon.kata.bankaccount.display.OperationsDisplay;

class DisplayOperationsTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Test
	void testToCreatePrinterAndDisplayd() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		// 1548718177581 = 29-01-2019 00:29
		Clock clock = Clock.fixed(Instant.ofEpochMilli(1548718177581l), ZoneId.systemDefault());

		String clientName = "Bat man";
		DisplayManager displayManager = new DisplayManager();
		ProxyCarbonBank bank = new ProxyCarbonBank(clock, displayManager);
		displayManager.addDisplay(new OperationsDisplay());

		bank.addClient(clientName);

		StringBuilder builder = new StringBuilder();
		String sp = OperationsDisplay.SEPARATOR;
		builder.append(
				sp + "Operation " + sp + "Date            " + sp + "Amount" + sp + "Balance" + sp + "" + System.lineSeparator());
		builder.append(
				sp + "Deposit   " + sp + "29-01-2019 00:29" + sp + "0     " + sp + "0      " + sp + "" + System.lineSeparator());
		builder.append(
				sp + "Deposit   " + sp + "29-01-2019 00:29" + sp + "10    " + sp + "10     " + sp + "" + System.lineSeparator());
		builder.append(
				sp + "Withdrawal" + sp + "29-01-2019 00:29" + sp + "-5    " + sp + "5      " + sp + "" + System.lineSeparator());
		builder.append(
				sp + "Deposit   " + sp + "29-01-2019 00:29" + sp + "20    " + sp + "25     " + sp + "" + System.lineSeparator());
		builder.append(
				sp + "Withdrawal" + sp + "29-01-2019 00:29" + sp + "-15   " + sp + "10     " + sp + "" + System.lineSeparator());

		bank.depositOrWithdrawalOnClientAccount(clientName, BigInteger.valueOf(10));
		bank.depositOrWithdrawalOnClientAccount(clientName, BigInteger.valueOf(-5));
		bank.depositOrWithdrawalOnClientAccount(clientName, BigInteger.valueOf(20));
		bank.depositOrWithdrawalOnClientAccount(clientName, BigInteger.valueOf(-15));

		bank.displayOperations(clientName);

		System.setOut(originalOut);
		System.setErr(originalErr);

		System.out.println(outContent.toString());

		Assertions.assertEquals(builder.toString(), outContent.toString());
	}

}
