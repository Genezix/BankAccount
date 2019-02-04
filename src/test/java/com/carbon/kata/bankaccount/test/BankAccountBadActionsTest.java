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
		Assertions.assertFalse(bank.deposit(clientName, BigDecimal.valueOf(10)).isPresent());
	}

	@Test
	void testToWithdrawalOnNoExistingClientAccount() {
		Assertions.assertFalse(bank.withdrawal(clientName, BigDecimal.valueOf(10)).isPresent());
	}

	@Test
	void testToCreateExistingClientAccount() {
		Assertions.assertEquals(Collections.emptyList(), bank.getOperations(clientName));
	}
}
