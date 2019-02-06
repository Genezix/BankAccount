package com.carbon.kata.bank.account;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

	@BeforeEach
	void onSetUp() {
		clock = Clock.fixed(Instant.ofEpochSecond(561294300), ZoneId.systemDefault());
		clockMillis = clock.millis();
		operationRepository = Mockito.mock(OperationRepository.class);
		account = new Account(operationRepository, clock);
	}

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@DisplayName("Account should accept to deposit a positive amount of money")
	@ValueSource(strings = { "1.4", "1.0000058", "10", "20", "30150", "123" })
	void acceptToDepositAPositiveAmountOfMoney(String amount) throws NegativeAmountException {
		final var amountToDeposit = new BigDecimal(amount);
		final var expectedOperation = Operation.ofDeposit(clockMillis, amountToDeposit, amountToDeposit);

		final var expectedBalance = amountToDeposit;

		// Act
		final var balance = account.deposit(amountToDeposit);

		// Assert
		Assertions.assertEquals(expectedBalance, balance);
		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verify(operationRepository).add(expectedOperation);
		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@DisplayName("Account should throw an exception trying to deposit a negative amount of money")
	@ValueSource(strings = { "0", "-10" })
	void throwAnExceptionWhenTryToDepositNegativeAmountOfMoney(String amount) {
		final var amountToDeposit = new BigDecimal(amount);

		Assertions.assertThrows(NegativeAmountException.class, () -> {
			account.deposit(amountToDeposit);
		});

		Mockito.verifyZeroInteractions(operationRepository);
	}

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@DisplayName("Account should throw an exception trying  to withdraw a negative amount of money")
	@ValueSource(strings = { "0", "-10" })
	void throwAnExceptionWhenTryToWithdrawNegativeAmountOfMoney(String amount) {
		final var amountToWithdraw = new BigDecimal(amount);

		Assertions.assertThrows(NegativeAmountException.class, () -> {
			account.withdraw(amountToWithdraw);
		});

		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@DisplayName("Account should throw an exception trying  to withdraw a negative amount of money")
	@ValueSource(strings = { "10", "1.4", "100254.12" })
	void throwAnExceptionWhenTryToWithdrawMoreMoneyThanPossible() {
		final var amountToWithdraw = new BigDecimal("10");

		Assertions.assertThrows(NotEnoughMoneyException.class, () -> {
			account.withdraw(amountToWithdraw);
		});

		// The getLast will be called in order to get the current balance
		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verifyNoMoreInteractions(operationRepository);
	}

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@DisplayName("Account should accept to withdraw a positive amount of money when there is enough money")
	@CsvSource({ "40, 10", "100, 50", "1214.12, 30.541" })
	void acceptToWithdrawAPositiveAmountOfMoneyWhenThereIsEnoughMoney(String init, String withdraw)
			throws NegativeAmountException, NotEnoughMoneyException {
		final var initialValue = new BigDecimal(init);
		final var initialDepositOperation = Operation.ofDeposit(clockMillis, initialValue, initialValue);
		Mockito.when(operationRepository.getLast()).thenReturn(Optional.of(initialDepositOperation));

		final var amountToWithdraw = new BigDecimal(withdraw);
		final var expectedOperation = Operation.ofWithdrawal(clockMillis, amountToWithdraw,
				initialValue.subtract(amountToWithdraw));

		final var expectedBalance = initialValue.subtract(amountToWithdraw);

		// Act
		final var balance = account.withdraw(amountToWithdraw);

		// Assert
		Assertions.assertEquals(expectedBalance, balance);
		Mockito.verify(operationRepository, VerificationModeFactory.times(1)).getLast();
		Mockito.verify(operationRepository).add(expectedOperation);
		Mockito.verifyNoMoreInteractions(operationRepository);
	}
}
