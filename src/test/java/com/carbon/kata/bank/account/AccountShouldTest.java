package com.carbon.kata.bank.account;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import com.carbon.kata.bank.exceptions.NegativeAmountException;
import com.carbon.kata.bank.exceptions.NotEnoughMoneyException;

class AccountShouldTest {
	private Account account;
	private Clock clock;
	private OperationRepository operationRepository;

	@BeforeEach
	void onSetUp() {
		clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
		operationRepository = Mockito.mock(OperationRepository.class);
		account = new Account(operationRepository, clock);
	}

	@Test
	@DisplayName("Account should accept to deposit a positive amount of money")
	void acceptToDepositAPositiveAmountOfMoney() {
		final var amountToDeposit = BigDecimal.valueOf(10);
		final var expectedOperation = Operation.ofDeposit(clock.millis(), amountToDeposit, amountToDeposit);

		try {
			account.depositMoney(amountToDeposit);
		} catch (NegativeAmountException e) {
			fail(e);
		}

		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verify(operationRepository).add(expectedOperation);
		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@Test
	@DisplayName("Account should throw an exception trying to deposit a negative amount of money")
	void throwAnExceptionWhenTryToDepositNegativeAmountOfMoney() {
		final var amountToDeposit = BigDecimal.valueOf(-10);
		Assertions.assertThrows(NegativeAmountException.class, () -> {
			account.depositMoney(amountToDeposit);
		});
		Mockito.verifyZeroInteractions(operationRepository);
	}

	@Test
	@DisplayName("Account should throw an exception trying  to withdraw a negative amount of money")
	void throwAnExceptionWhenTryToWithdrawNegativeAmountOfMoney() {
		final var amountToWithdraw = BigDecimal.valueOf(-10);
		Assertions.assertThrows(NegativeAmountException.class, () -> {
			account.withdrawMoney(amountToWithdraw);
		});
		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@Test
	@DisplayName("Account should throw an exception trying  to withdraw a negative amount of money")
	void throwAnExceptionWhenTryToWithdrawMoreMoneyThanPossible() {
		final var amountToWithdraw = BigDecimal.valueOf(10);
		Assertions.assertThrows(NotEnoughMoneyException.class, () -> {
			account.withdrawMoney(amountToWithdraw);
		});
		// The getLast will be call in order to get the current balance
		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@Test
	@DisplayName("Account should accept to withdraw a positive amount of money when there is enough money")
	void acceptToWithdrawAPositiveAmountOfMoneyWhenThereIsEnoughMoney() {
		final var intialDepositOperation = Operation.ofDeposit(clock.millis(), BigDecimal.valueOf(40),
				BigDecimal.valueOf(40));
		Mockito.when(operationRepository.getLast()).thenReturn(Optional.of(intialDepositOperation));
		final var amountToWithdraw = BigDecimal.valueOf(10);
		final var expectedOperation = Operation.ofWithdrawal(clock.millis(), amountToWithdraw, BigDecimal.valueOf(30));
		try {
			account.withdrawMoney(amountToWithdraw);
		} catch (NegativeAmountException | NotEnoughMoneyException e) {
			fail(e);
		}

		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verify(operationRepository).add(expectedOperation);
		Mockito.verifyNoMoreInteractions(operationRepository);
	}
}
