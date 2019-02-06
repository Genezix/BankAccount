package com.carbon.kata.bank.account;

import java.math.BigDecimal;
import java.time.Clock;

import com.carbon.kata.bank.display.StatementPrinter;
import com.carbon.kata.bank.exceptions.NegativeAmountException;
import com.carbon.kata.bank.exceptions.NotEnoughMoneyException;

public class Account {
	private final OperationRepository operationRepository;
	private final Clock clock;

	public Account(OperationRepository operationRepository) {
		this(operationRepository, Clock.systemUTC());
	}

	public Account(OperationRepository operationRepository, Clock clock) {
		this.operationRepository = operationRepository;
		this.clock = clock;
	}

	public BigDecimal withdraw(BigDecimal amount) throws NegativeAmountException, NotEnoughMoneyException {
		verifyAmount(amount);

		final var currentBalance = getBalance();
		final var newBalance = currentBalance.subtract(amount);

		if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
			throw new NotEnoughMoneyException(amount, currentBalance);
		}

		operationRepository.add(Operation.ofWithdrawal(clock.millis(), amount, newBalance));

		return newBalance;
	}

	public BigDecimal deposit(BigDecimal amount) throws NegativeAmountException {
		verifyAmount(amount);

		final var newBalance = getBalance().add(amount);

		operationRepository.add(Operation.ofDeposit(clock.millis(), amount, newBalance));

		return newBalance;
	}

	private void verifyAmount(BigDecimal amount) throws NegativeAmountException {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new NegativeAmountException(amount);
		}
	}

	private BigDecimal getBalance() {
		var lastOperation = operationRepository.getLast();

		if (!lastOperation.isPresent()) {
			return BigDecimal.ZERO;
		}

		return lastOperation.get().getBalance();
	}

	public void printStatement(StatementPrinter printer) {
		printer.printStatement(operationRepository.findAll());
	}
}
