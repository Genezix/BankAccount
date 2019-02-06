package com.carbon.kata.bank.display;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.carbon.kata.bank.account.Operation;

public class TabularOperationFormatter implements OperationFormatter {
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
	public static final String SEPARATOR = " | ";

	private static String header = initHeader();

	private final static int columnOperationSize = 10;
	private final static int columnAmountSize = 6;
	private final static int columnBalanceSize = 7;

	private static String initHeader() {
		final var builder = new StringBuilder();
		builder.append(SEPARATOR);
		builder.append(formatString("Operation", columnOperationSize));
		builder.append(SEPARATOR);
		builder.append(formatString("Date", DATE_FORMAT.length()));
		builder.append(SEPARATOR);
		builder.append(formatString("Amount", columnAmountSize));
		builder.append(SEPARATOR);
		builder.append(formatString("Balance", columnBalanceSize));
		builder.append(SEPARATOR);
		return builder.toString();
	}

	@Override
	public String getHeader() {
		return header;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.carbon.kata.bank.display.OperationFormatter#formatOperation(com.carbon.
	 * kata.bank.account.Operation)
	 */
	@Override
	public String formatOperation(Operation operation) {
		final var builder = new StringBuilder();
		builder.append(SEPARATOR);
		builder.append(formatString(operation.getType().getValue(), columnOperationSize));
		builder.append(SEPARATOR);
		final var date = Instant.ofEpochMilli(operation.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final var ofPattern = DateTimeFormatter.ofPattern(DATE_FORMAT);
		builder.append(formatString(date.format(ofPattern), DATE_FORMAT.length()));
		builder.append(SEPARATOR);
		builder.append(formatString(operation.getAmount().toString(), columnAmountSize));
		builder.append(SEPARATOR);
		builder.append(formatString(operation.getBalance().toString(), columnBalanceSize));
		builder.append(SEPARATOR);
		return builder.toString();
	}
	private static String formatString(String value, int size) {
		return String.format("%-" + size + "s", value);
	}
}
