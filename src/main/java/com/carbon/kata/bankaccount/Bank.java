package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.util.List;

import com.carbon.kata.bankaccount.exception.ClientAccountAlreadyExists;
import com.carbon.kata.bankaccount.exception.ClientAccountDoesNotExists;

public interface Bank {
	public Bank addClient(String clientName) throws ClientAccountAlreadyExists;

	public Bank operationOnClientAccount(String clientName, BigInteger amountToDeposit) throws ClientAccountDoesNotExists;

	public BigInteger getClientAccountBalance(String clientName);

	public List<String> getOperationsHistoricOnClientAccount(String clientName);
}
