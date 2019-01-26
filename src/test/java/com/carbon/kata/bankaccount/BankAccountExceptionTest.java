package com.carbon.kata.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.exception.ClientAccountAlreadyExists;
import com.carbon.kata.bankaccount.exception.ClientAccountDoesNotExists;

class BankAccountExceptionTest {

	private Bank bank;
	private String clientName = "Bat man";

	@BeforeEach
	void onSetUp() {
		bank = new CarbonBank(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
	}

	@Test
	void testToDepositOnNoExistingClientAccount() {
		Throwable exception = assertThrows(ClientAccountDoesNotExists.class, () -> {
			bank.operationOnClientAccount(clientName, BigInteger.valueOf(10));
		});
		assertEquals(clientName, exception.getMessage());
	}

	@Test
	void testToWithdrawalOnNoExistingClientAccount() {
		Throwable exception = assertThrows(ClientAccountDoesNotExists.class, () -> {
			bank.operationOnClientAccount(clientName, BigInteger.valueOf(-10));
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
