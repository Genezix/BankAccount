package com.carbon.kata.bankaccount;

import java.math.BigInteger;

import com.carbon.kata.bankaccount.exception.ClientAccountAlreadyExists;
import com.carbon.kata.bankaccount.exception.ClientAccountDoesNotExists;

public interface Bank {
	public Bank addClient(String clientName) throws ClientAccountAlreadyExists;

	public Bank depositOnClientAccount(String clientName, BigInteger amountToDeposit) throws ClientAccountDoesNotExists;

	public Bank withdrawalOnClientAccount(String clientName, BigInteger amountToWithdrawal) throws ClientAccountDoesNotExists;

	public BigInteger getClientAccountBalance(String clientName);
}
