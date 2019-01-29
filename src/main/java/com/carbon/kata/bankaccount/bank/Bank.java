package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
import java.util.List;

import com.carbon.kata.bankaccount.display.DisplayableData;

public interface Bank {
	public Bank addClient(String clientName);

	public BigDecimal depositOrWithdrawalOnClientAccount(String clientName, BigDecimal amountToDeposit);

	public BigDecimal getClientAccountBalance(String clientName);

	public List<DisplayableData> getOperationsHistoricOnClientAccount(String clientName);
}
