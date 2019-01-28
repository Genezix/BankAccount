package com.carbon.kata.bankaccount.test;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.Bank;
import com.carbon.kata.bankaccount.ProxyCarbonBank;

class BankAccountBadActionsTest {

	private Bank bank;
	private String clientName = "Bat man";

	@BeforeEach
	void onSetUp() {
		bank = new ProxyCarbonBank(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
	}

	@Test
	void testToDepositOnNoExistingClientAccount() {
		Assertions.assertEquals(BigInteger.valueOf(-1),
				bank.depositOrWithdrawalOnClientAccount(clientName, BigInteger.valueOf(10)));
	}

	@Test
	void testToWithdrawalOnNoExistingClientAccount() {
		Assertions.assertEquals(BigInteger.valueOf(-1),
				bank.depositOrWithdrawalOnClientAccount(clientName, BigInteger.valueOf(-10)));
	}

	@Test
	void testToCreateExistingClientAccount() {
		Assertions.assertEquals(Collections.emptyList(), bank.getOperationsHistoricOnClientAccount(clientName));
	}
}
