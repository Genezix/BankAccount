package com.carbon.kata.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.ClientAccountAlreadyExists;
import exception.ClientAccountDoesNotExists;

class BankAccountExceptionTest {

	private Bank bank;
	private String clientName = "Bat man";

	@BeforeEach
	void onSetUp() {
		bank = new CarbonBank();
	}

	@Test
	void testToDepositOnNoExistingClientAccount() {
		Throwable exception = assertThrows(ClientAccountDoesNotExists.class, () -> {
			bank.depositOnClientAccount(clientName, BigInteger.valueOf(10));
		});
		assertEquals(clientName, exception.getMessage());
	}

	@Test
	void testToWithdrawalOnNoExistingClientAccount() {
		Throwable exception = assertThrows(ClientAccountDoesNotExists.class, () -> {
			bank.withdrawalOnClientAccount(clientName, BigInteger.valueOf(10));
		});
		assertEquals(clientName, exception.getMessage());
	}

	@Test
	void testToCreateExistingClientAccount() {
		Throwable exception = assertThrows(ClientAccountAlreadyExists.class, () -> {
			bank.addClient(clientName);
			bank.addClient(clientName);
		});
		assertEquals(clientName, exception.getMessage());
	}
}
