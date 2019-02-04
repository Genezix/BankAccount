package com.carbon.kata.bankaccount.test;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.bank.Bank;
import com.carbon.kata.bankaccount.bank.ProxyCarbonBank;

class BankAccountBadActionsTest {

	private Bank bank;
	private String clientName = "Bat man";

	@BeforeEach
	void onSetUp() {
		bank = new ProxyCarbonBank();
	}

	@Test
	void testToDepositOnNoExistingClientAccount() {
		Assertions.assertEquals(BigDecimal.valueOf(-1), bank.deposit(clientName, BigDecimal.valueOf(10)));
	}

	@Test
	void testToWithdrawalOnNoExistingClientAccount() {
		Assertions.assertEquals(BigDecimal.valueOf(-1), bank.withdrawal(clientName, BigDecimal.valueOf(10)));
	}

	@Test
	void testToCreateExistingClientAccount() {
		Assertions.assertEquals(Collections.emptyList(), bank.getOperations(clientName));
	}
}
