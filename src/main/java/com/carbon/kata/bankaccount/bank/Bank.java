package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
import java.util.List;

import com.carbon.kata.bankaccount.display.DisplayableData;

public interface Bank {
	public Bank addClient(String clientName);

	public BigDecimal withdrawal(String clientName, BigDecimal amount);
	
	public BigDecimal deposit(String clientName, BigDecimal amount);

	public BigDecimal getBalance(String clientName);

	public List<DisplayableData> getOperations(String clientName);
}
