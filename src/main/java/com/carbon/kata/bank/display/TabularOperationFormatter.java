package com.carbon.kata.bank.display;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

import com.carbon.kata.bank.account.Operation;

public class TabularOperationFormatter implements OperationFormatter {
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
	private static final String SEPARATOR = " | ";

	private static final String header = initHeader();

	private final static int columnOperationSize = 10;
	private final static int columnAmountSize = 6;
	private final static int columnBalanceSize = 7;

	private static String initHeader() {
		return new StringJoiner(SEPARATOR)
				.add(formatString("Operation", columnOperationSize))
				.add(formatString("Date", DATE_FORMAT.length()))
				.add(formatString("Amount", columnAmountSize))
				.add(formatString("Balance", columnBalanceSize))
				.toString();
	}

	@Override
	public String getHeader() {
		return header;
	}

	@Override
	public String formatOperation(Operation operation) {
		final var date = Instant.ofEpochMilli(operation.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final var ofPattern = DateTimeFormatter.ofPattern(DATE_FORMAT);
		
		return new StringJoiner(SEPARATOR)
				.add(formatString(operation.getType().getValue(), columnOperationSize))
				.add(formatString(date.format(ofPattern), DATE_FORMAT.length()))
				.add(formatString(operation.getAmount().toString(), columnAmountSize))
				.add(formatString(operation.getBalance().toString(), columnBalanceSize))
				.toString();
	}

	private static String formatString(String value, int size) {
		return String.format("%-" + size + "s", value);
	}
}
