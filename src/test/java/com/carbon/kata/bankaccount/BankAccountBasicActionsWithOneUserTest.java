package com.carbon.kata.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.ClientAccountAlreadyExists;
import exception.ClientAccountDoesNotExists;

class BankAccountBasicActionsWithOneUserTest {

	private Bank bank;
	private String clientName = "Bat man";

	@BeforeEach
	void onSetUp() {
		bank = new CarbonBank();

		try {
			bank.addClient(clientName);
		} catch (ClientAccountAlreadyExists e) {
			fail("Client already exists", e);
		}
	}

	@Test
	void testToCreateAClientWithEmptyAccount() {
		final BigInteger expectedAccountBalance = BigInteger.valueOf(0);
		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToDepositOnClientAccount() {
		final BigInteger amountToDeposit = BigInteger.valueOf(10);
		final BigInteger expectedAccountBalance = BigInteger.valueOf(10);
		try {
			bank.depositOnClientAccount(clientName, amountToDeposit);
		} catch (ClientAccountDoesNotExists e) {
			fail("Client does not exists", e);
		}
		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToWithdrawalFromClientAccount() {
		final BigInteger amountToDeposit = BigInteger.valueOf(10);
		final BigInteger amountToWithdrawal = BigInteger.valueOf(6);
		final BigInteger expectedAccountBalance = amountToDeposit.subtract(amountToWithdrawal);

		try {
			bank.depositOnClientAccount(clientName, amountToDeposit);
			bank.withdrawalOnClientAccount(clientName, amountToWithdrawal);
		} catch (ClientAccountDoesNotExists e) {
			fail("Client does not exists", e);
		}

		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToWithdrawlMoreThanPositionFromClientAccount() {
		final BigInteger amountToDeposit = BigInteger.valueOf(10);
		final BigInteger amountToWithdrawal = BigInteger.valueOf(15);

		final BigInteger expectedAccountBalance = BigInteger.valueOf(10);

		try {
			bank.depositOnClientAccount(clientName, amountToDeposit);
			bank.withdrawalOnClientAccount(clientName, amountToWithdrawal);
		} catch (ClientAccountDoesNotExists e) {
			fail("Client does not exists", e);
		}
		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}
}
