package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.carbon.kata.bankaccount.display.DisplayableData;

public class Operation implements DisplayableData {
	private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
	
	private static final List<String> headers;
	static {
		headers = new ArrayList<>();
		headers.add("Operation ");
		headers.add(String.format("%-" + DATE_FORMAT.length() + "s", "Date"));
		headers.add("Amount");
		headers.add("Balance");
	}

	private List<String> datas;

	private final long time;
	private final OperationType type;
	private final BigDecimal operationAmount;
	private final BigDecimal balance;

	public static Operation buildDepositOperation(long time, BigDecimal operationAmount, BigDecimal balance) {
		return new Operation(time, operationAmount, balance, OperationType.DEPOSIT);
	}
	
	public static Operation buildWithDrawalOperation(long time, BigDecimal operationAmount, BigDecimal balance) {
		return new Operation(time, operationAmount, balance, OperationType.WISTHDRAWAL);
	}
	
	private Operation(long time, BigDecimal operationAmount, BigDecimal balance, OperationType type) {
		this.time = time;
		this.operationAmount = operationAmount;
		this.balance = balance;
		this.type = type;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public List<String> getHeaders() {
		return headers;
	}

	@Override
	public List<String> getDatas() {
		if (datas == null) {
			datas = new ArrayList<>();
			datas.add(this.type.getValue());
			LocalDateTime date = Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDateTime();
			DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(DATE_FORMAT);
			datas.add(date.format(ofPattern));
			datas.add(operationAmount.toString());
			datas.add(balance.toString());
		}
		return datas;
	}
}
