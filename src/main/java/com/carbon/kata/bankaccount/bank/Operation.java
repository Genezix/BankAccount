package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Operation {
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
	public static final String SEPARATOR = " | ";

	public static final String HEADER = initHader();
	private final String operationAsString;

	private final long time;
	private final OperationType type;
	private final BigDecimal amount;
	private final BigDecimal balance;

	private final static int columnOperationSize = 10;
	private final static int columnAmountSize = 6;
	private final static int columnBalanceSize = 7;

	private static String initHader() {
		StringBuilder builder = new StringBuilder();
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

	public static Operation buildDepositOperation(long time, BigDecimal operationAmount, BigDecimal balance) {
		return new Operation(time, operationAmount, balance, OperationType.DEPOSIT);
	}

	public static Operation buildWithDrawalOperation(long time, BigDecimal operationAmount, BigDecimal balance) {
		return new Operation(time, operationAmount, balance, OperationType.WISTHDRAWAL);
	}

	private Operation(long time, BigDecimal amount, BigDecimal balance, OperationType type) {
		this.time = time;
		this.amount = amount;
		this.balance = balance;
		this.type = type;
		this.operationAsString = initOperationAsString();
	}

	private String initOperationAsString() {
		StringBuilder builder = new StringBuilder();
		builder.append(SEPARATOR);
		builder.append(formatString(this.type.getValue(), columnOperationSize));
		builder.append(SEPARATOR);
		LocalDateTime date = Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDateTime();
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(DATE_FORMAT);
		builder.append(formatString(date.format(ofPattern), DATE_FORMAT.length()));
		builder.append(SEPARATOR);
		builder.append(formatString(amount.toString(), columnAmountSize));
		builder.append(SEPARATOR);
		builder.append(formatString(balance.toString(), columnBalanceSize));
		builder.append(SEPARATOR);
		return builder.toString();
	}

	private static String formatString(String value, int size) {
		return String.format("%-" + size + "s", value);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getHeader() {
		return HEADER;
	}

	public String getOperationsAsString() {
		return this.operationAsString;

	}
}
