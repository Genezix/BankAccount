package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.util.List;

import com.carbon.kata.bankaccount.display.DisplayableData;

public interface Bank {
	public Bank addClient(String clientName);

	public BigInteger depositOrWithdrawalOnClientAccount(String clientName, BigInteger amountToDeposit);

	public BigInteger getClientAccountBalance(String clientName);

	public List<DisplayableData> getOperationsHistoricOnClientAccount(String clientName);
}
