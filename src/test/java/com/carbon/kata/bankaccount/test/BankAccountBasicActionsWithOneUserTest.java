package com.carbon.kata.bankaccount.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.bank.Bank;
import com.carbon.kata.bankaccount.bank.ProxyCarbonBank;

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
		final BigDecimal expectedAccountBalance = bDecimal(0);
		Optional<BigDecimal> balance = bank.getBalance(clientName);
		assertTrue(balance.isPresent());
		assertTrue(expectedAccountBalance.compareTo(balance.get()) == 0);
	}

	@Test
	void testToDepositOnClientAccount() {
		final BigDecimal amountToDeposit = bDecimal(10.00000014);
		final BigDecimal expectedAccountBalance = bDecimal(10.00000014);

		bank.deposit(clientName, amountToDeposit);

		Optional<BigDecimal> balance = bank.getBalance(clientName);
		assertTrue(balance.isPresent());
		assertTrue(expectedAccountBalance.compareTo(balance.get()) == 0);
	}

	@Test
	void testToWithdrawalFromClientAccount() {
		final BigDecimal amountToDeposit = bDecimal(10);
		final BigDecimal amountToWithdrawal = bDecimal(6);
		final BigDecimal expectedAccountBalance = amountToDeposit.subtract(amountToWithdrawal);

		bank.deposit(clientName, amountToDeposit);
		bank.withdrawal(clientName, amountToWithdrawal);

		Optional<BigDecimal> balance = bank.getBalance(clientName);
		assertTrue(balance.isPresent());
		assertTrue(expectedAccountBalance.compareTo(balance.get()) == 0);
	}

	@Test
	void testToWithdrawlMoreThanPositionFromClientAccount() {
		final BigDecimal amountToDeposit = bDecimal(10);
		final BigDecimal amountToWithdrawal = bDecimal(15);

		final BigDecimal expectedAccountBalance = bDecimal(10);

		bank.deposit(clientName, amountToDeposit);
		bank.withdrawal(clientName, amountToWithdrawal);

		Optional<BigDecimal> balance = bank.getBalance(clientName);
		assertTrue(balance.isPresent());
		assertTrue(expectedAccountBalance.compareTo(balance.get()) == 0);
	}

	private BigDecimal bDecimal(double value) {
		return BigDecimal.valueOf(value);
	}
}
