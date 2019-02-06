package com.carbon.kata.bank.account;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import com.carbon.kata.bank.exceptions.NegativeAmountException;
import com.carbon.kata.bank.exceptions.NotEnoughMoneyException;

class AccountShouldTest {
	private Account account;
	private Clock clock;
	private long clockMillis;
	private OperationRepository operationRepository;

	private final BigDecimal bigDecimal10 = new BigDecimal("10");
	private final BigDecimal bigDecimal40 = new BigDecimal("40");

	@BeforeEach
	void onSetUp() {
		clock = Clock.fixed(Instant.ofEpochSecond(561294300), ZoneId.systemDefault());
		clockMillis = clock.millis();
		operationRepository = Mockito.mock(OperationRepository.class);
		account = new Account(operationRepository, clock);
	}

	@DisplayName("Account should accept to deposit a positive amount of money")
	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@ValueSource(strings = { "1.4", "1.0000058", "10", "20", "30150", "123" })
	void acceptToDepositAPositiveAmountOfMoney(String amount) throws NegativeAmountException {
		final var amountToDeposit = new BigDecimal(amount);
		final var expectedOperation = Operation.ofDeposit(clockMillis, amountToDeposit, amountToDeposit);

		// Act
		account.depositMoney(amountToDeposit);

		// Assert
		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verify(operationRepository).add(expectedOperation);
		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@DisplayName("Account should throw an exception trying to deposit a negative amount of money")
	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@ValueSource(strings = { "0", "-10" })
	void throwAnExceptionWhenTryToDepositNegativeAmountOfMoney(String amount) {
		final var amountToDeposit = new BigDecimal(amount);

		Assertions.assertThrows(NegativeAmountException.class, () -> {
			account.depositMoney(amountToDeposit);
		});

		Mockito.verifyZeroInteractions(operationRepository);
	}

	@DisplayName("Account should throw an exception trying  to withdraw a negative amount of money")
	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@ValueSource(strings = { "0", "-10" })
	void throwAnExceptionWhenTryToWithdrawNegativeAmountOfMoney(String amount) {
		final var amountToWithdraw = new BigDecimal(amount);
		Assertions.assertThrows(NegativeAmountException.class, () -> {
			account.withdrawMoney(amountToWithdraw);
		});

		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@Test
	@DisplayName("Account should throw an exception trying  to withdraw a negative amount of money")
	void throwAnExceptionWhenTryToWithdrawMoreMoneyThanPossible() {
		final var amountToWithdraw = bigDecimal10;

		Assertions.assertThrows(NotEnoughMoneyException.class, () -> {
			account.withdrawMoney(amountToWithdraw);
		});

		// The getLast will be call in order to get the current balance
		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@Test
	@DisplayName("Account should accept to withdraw a positive amount of money when there is enough money")
	void acceptToWithdrawAPositiveAmountOfMoneyWhenThereIsEnoughMoney()
			throws NegativeAmountException, NotEnoughMoneyException {
		final var intialDepositOperation = Operation.ofDeposit(clockMillis, bigDecimal40, bigDecimal40);
		Mockito.when(operationRepository.getLast()).thenReturn(Optional.of(intialDepositOperation));

		final var amountToWithdraw = bigDecimal10;
		final var expectedOperation = Operation.ofWithdrawal(clockMillis, amountToWithdraw,
				bigDecimal40.subtract(bigDecimal10));

		// Act
		account.withdrawMoney(amountToWithdraw);

		// Assert
		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verify(operationRepository).add(expectedOperation);
		Mockito.verifyNoMoreInteractions(operationRepository);
	}
}
