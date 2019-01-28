package com.carbon.kata.bankaccount.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.carbon.kata.bankaccount.AccountOperation;
import com.carbon.kata.bankaccount.Bank;
import com.carbon.kata.bankaccount.ProxyCarbonBank;
import com.carbon.kata.bankaccount.display.DisplayableData;

class BankAccountBasicActionsWithOneUserTest {

	private Bank bank;
	private String clientName = "Bat man";
	private Clock clock;

	@BeforeEach
	void onSetUp() {
		clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
		bank = new ProxyCarbonBank(clock);
		bank.addClient(clientName);
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

		bank.depositOrWithdrawalOnClientAccount(clientName, amountToDeposit);

		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToWithdrawalFromClientAccount() {
		final BigInteger amountToDeposit = bInt(10);
		final BigInteger amountToWithdrawal = bInt(-6);
		final BigInteger expectedAccountBalance = amountToDeposit.add(amountToWithdrawal);

		bank.depositOrWithdrawalOnClientAccount(clientName, amountToDeposit);
		bank.depositOrWithdrawalOnClientAccount(clientName, amountToWithdrawal);

		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToWithdrawlMoreThanPositionFromClientAccount() {
		final BigInteger amountToDeposit = bInt(10);
		final BigInteger amountToWithdrawal = bInt(-15);

		final BigInteger expectedAccountBalance = bInt(10);

		bank.depositOrWithdrawalOnClientAccount(clientName, amountToDeposit);
		bank.depositOrWithdrawalOnClientAccount(clientName, amountToWithdrawal);

		assertEquals(expectedAccountBalance, bank.getClientAccountBalance(clientName));
	}

	@Test
	void testToGetOperationHistoryOfClientAccount() {
		final List<DisplayableData> expectedOperations = new LinkedList<>();

		Random random = new Random();
		expectedOperations.add(new AccountOperation(clock.millis(), bInt(0), bInt(0)));
		long currentValue = 10000l;
		expectedOperations.add(new AccountOperation(clock.millis(), bInt(currentValue), bInt(currentValue)));

		bank.depositOrWithdrawalOnClientAccount(clientName, bInt(currentValue));

		for (int i = 0; i < 100; i++) {
			long moneyValue = random.nextLong() % 20;
			currentValue += moneyValue;
			expectedOperations.add(new AccountOperation(clock.millis(), bInt(moneyValue), bInt(currentValue)));
			bank.depositOrWithdrawalOnClientAccount(clientName, bInt(moneyValue));
		}
		List<DisplayableData> resultOperations = bank.getOperationsHistoricOnClientAccount(clientName);
		
		Assertions.assertIterableEquals(expectedOperations, resultOperations);
	}

	private BigInteger bInt(long value) {
		return BigInteger.valueOf(value);
	}
}
