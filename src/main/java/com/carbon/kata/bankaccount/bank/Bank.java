package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.carbon.kata.bankaccount.display.DisplayableData;

public interface Bank {
	public Bank addClient(String clientName);

	public Optional<BigDecimal> withdrawal(String clientName, BigDecimal amount);
	
	public Optional<BigDecimal> deposit(String clientName, BigDecimal amount);

	public Optional<BigDecimal> getBalance(String clientName);

	public List<DisplayableData> getOperations(String clientName);
}
