package com.carbon.kata.bankaccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.exception.ClientAccountAlreadyExists;
import com.carbon.kata.bankaccount.exception.ClientAccountDoesNotExists;

class BankAccountBasicActionsWithOneUserTest {

	private Bank bank;
	private String clientName = "Bat man";
	private Clock clock;

	@BeforeEach
	void onSetUp() {
		clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
		bank = new CarbonBank(clock);

		try {
			bank.addClient(clientName);
		} catch (ClientAccountAlreadyExists e) {
			fail("Client already exists", e);
		}
	}

	@Test
	void testToCreateAClientWithEmptyAccount() {
		final BigInteger expectedAccountBalance = bInt(0);
		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToDepositOnClientAccount() {
		final BigInteger amountToDeposit = bInt(10);
		final BigInteger expectedAccountBalance = bInt(10);
		try {
			bank.operationOnClientAccount(clientName, amountToDeposit);
		} catch (ClientAccountDoesNotExists e) {
			fail("Client does not exists", e);
		}
		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToWithdrawalFromClientAccount() {
		final BigInteger amountToDeposit = bInt(10);
		final BigInteger amountToWithdrawal = bInt(-6);
		final BigInteger expectedAccountBalance = amountToDeposit.add(amountToWithdrawal);

		try {
			bank.operationOnClientAccount(clientName, amountToDeposit);
			bank.operationOnClientAccount(clientName, amountToWithdrawal);
		} catch (ClientAccountDoesNotExists e) {
			fail("Client does not exists", e);
		}

		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToWithdrawlMoreThanPositionFromClientAccount() {
		final BigInteger amountToDeposit = bInt(10);
		final BigInteger amountToWithdrawal = bInt(-15);

		final BigInteger expectedAccountBalance = bInt(10);

		try {
			bank.operationOnClientAccount(clientName, amountToDeposit);
			bank.operationOnClientAccount(clientName, amountToWithdrawal);
		} catch (ClientAccountDoesNotExists e) {
			fail("Client does not exists", e);
		}

		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToGetOperationHistoryOfClientAccount() {
		final List<String> expectedOperations = new LinkedList<>();

		Random random = new Random();
		expectedOperations.add(new AccountOperation(clock.millis(), bInt(0), bInt(0)).toString());
		long currentValue = 10000l;
		expectedOperations.add(new AccountOperation(clock.millis(), bInt(currentValue), bInt(currentValue)).toString());
		List<String> resultOperations = null;

		try {
			bank.operationOnClientAccount(clientName, bInt(currentValue));

			for (int i = 0; i < 100; i++) {
				long moneyValue = random.nextLong() % 20;
				currentValue += moneyValue;
				expectedOperations.add(new AccountOperation(clock.millis(), bInt(moneyValue), bInt(currentValue)).toString());
				bank.operationOnClientAccount(clientName, bInt(moneyValue));
			}
			resultOperations = bank.getOperationsHistoricOnClientAccount(clientName);
		} catch (ClientAccountDoesNotExists e) {
			fail("Client does not exists", e);
		}
		Assertions.assertLinesMatch(expectedOperations, resultOperations);
	}

	private BigInteger bInt(long value) {
		return BigInteger.valueOf(value);
	}
}
