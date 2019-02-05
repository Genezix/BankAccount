package com.carbon.kata.bank.test;

import static org.mockito.ArgumentMatchers.any;

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

import com.carbon.kata.bank.account.Account;
import com.carbon.kata.bank.account.Operation;
import com.carbon.kata.bank.account.OperationRepository;
import com.carbon.kata.bank.exceptions.NegativeAmountException;
import com.carbon.kata.bank.exceptions.NotEnoughMoneyException;

class AccountShould {
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
		final var expectedOperation = Operation.buildDepositOperation(clock.millis(), amountToDeposit, amountToDeposit);

		account.depositMoney(amountToDeposit);
		Mockito.verify(operationRepository).add(expectedOperation);
	}

	@Test
	@DisplayName("Account should throw an exception when try to deposit a negative amount of money")
	void throwAnExceptionWhenTryToDepositNegativeAmountOfMoney() {
		final var amountToDeposit = BigDecimal.valueOf(-10);
	    Assertions.assertThrows(NegativeAmountException.class, () -> {account.depositMoney(amountToDeposit);});
	    Mockito.verify(operationRepository, VerificationModeFactory.times(0)).add(any());
	}
	
	@Test
	@DisplayName("Account should throw an exception when try to withdraw a negative amount of money")
	void throwAnExceptionWhenTryToWithdrawNegativeAmountOfMoney() {
		final var amountToWithdraw = BigDecimal.valueOf(-10);
	    Assertions.assertThrows(NegativeAmountException.class, () -> {account.withdrawMoney(amountToWithdraw);});
	    Mockito.verify(operationRepository, VerificationModeFactory.times(0)).add(any());
	}
	
	@Test
	@DisplayName("Account should throw an exception when try to withdraw a negative amount of money")
	void throwAnExceptionWhenTryToWithdrawMoreMoneyThanPossible() {
		final var amountToWithdraw = BigDecimal.valueOf(10);
	    Assertions.assertThrows(NotEnoughMoneyException.class, () -> {account.withdrawMoney(amountToWithdraw);});
	    Mockito.verify(operationRepository, VerificationModeFactory.times(0)).add(any());
	}
	
	@Test
	@DisplayName("Account should accept to withdraw a positive amount of money when there is enough money")
	void acceptToWithdrawAPositiveAmountOfMoneyWhenThereIsEnoughMoney() {
		Mockito.when(operationRepository.getLast()).thenReturn(Optional.of(Operation.buildDepositOperation(clock.millis(), BigDecimal.valueOf(40), BigDecimal.valueOf(40))));
		final var amountToWithdraw = BigDecimal.valueOf(10);
		final var expectedOperation = Operation.buildWithdrawalOperation(clock.millis(), amountToWithdraw, BigDecimal.valueOf(30));

		account.withdrawMoney(amountToWithdraw);
		Mockito.verify(operationRepository).add(expectedOperation);
	}
}
