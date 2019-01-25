package com.carbon.kata.bankaccount;

import java.math.BigInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankAccountBasicTestWithOneUserTest {

	private Bank bank;
	private String clientName = "Bat man";

	@BeforeEach
	void onSetUp() {
		bank = new CarbonBank();
		bank.addClient(clientName);
	}

	@Test
	void testToCreateAClientWithEmptyAccount() {
		final BigInteger expectedAccountBalance = BigInteger.valueOf(0);
		Assertions.assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToDepositOnClientAccount() {
		final BigInteger amountToDeposit = BigInteger.valueOf(10);
		final BigInteger expectedAccountBalance = BigInteger.valueOf(10);
		bank.depositOnClientAccount(clientName, amountToDeposit);
		Assertions.assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToWithdrawalOnClientAccount() {
		final BigInteger amountToDeposit = BigInteger.valueOf(10);
		final BigInteger amountToWithdrawal = BigInteger.valueOf(6);
		final BigInteger expectedAccountBalance = amountToDeposit.subtract(amountToWithdrawal);

		bank.depositOnClientAccount(clientName, amountToDeposit);
		bank.WithdrawalOnClientAccount(clientName, amountToWithdrawal);
		Assertions.assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}
}
