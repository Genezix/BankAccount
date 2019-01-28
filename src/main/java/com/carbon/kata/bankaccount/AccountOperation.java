package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.carbon.kata.bankaccount.display.DisplayableData;

public class AccountOperation implements DisplayableData {
	private static final String DEPOSIT = "Deposit";
	private static final String WITHDRAWAL = "Withdrawal";
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
	private final BigInteger operationAmount;
	private final BigInteger balance;

	public AccountOperation(long time, BigInteger operationAmount, BigInteger balance) {
		this.time = time;
		this.operationAmount = operationAmount;
		this.balance = balance;

	}

	public long getOperationTime() {
		return time;
	}

	public BigInteger getOperationAmount() {
		return operationAmount;
	}

	public BigInteger getBalance() {
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
			datas.add(operationAmount.compareTo(BigInteger.valueOf(0)) == -1 ? WITHDRAWAL : DEPOSIT);
			LocalDateTime date = Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDateTime();
			DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern(DATE_FORMAT);
			datas.add(date.format(ofPattern));
			datas.add(operationAmount.toString());
			datas.add(balance.toString());
		}
		return datas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((datas == null) ? 0 : datas.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + ((operationAmount == null) ? 0 : operationAmount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountOperation other = (AccountOperation) obj;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (datas == null) {
			if (other.datas != null)
				return false;
		} else if (!datas.equals(other.datas))
			return false;
		if (time != other.time)
			return false;
		if (operationAmount == null) {
			if (other.operationAmount != null)
				return false;
		} else if (!operationAmount.equals(other.operationAmount))
			return false;
		return true;
	}
}
