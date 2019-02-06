package com.carbon.kata.bank.display;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import com.carbon.kata.bank.account.Account;
import com.carbon.kata.bank.account.Operation;
import com.carbon.kata.bank.account.OperationRepository;

class StatementPrinterShouldTest {

	@ParameterizedTest(name = "run #{index} with [{arguments}]")
	@DisplayName("Verify that printer receive the good operation list to print")
	@CsvSource({ "40, 10", "100, 50", "1214.12, 30.541" })
	void receiveOperationListWhenAccountHaveToPrintStatement(String init, String withdraw) {
		final var initialValue = new BigDecimal(init);
		final var withdrawValue = new BigDecimal(withdraw);

		final var operationRepositoryMock = Mockito.mock(OperationRepository.class);

		final List<Operation> expectedOperationList = new LinkedList<>();
		var balance = initialValue;
		expectedOperationList.add(Operation.ofDeposit(0l, initialValue, initialValue));
		balance = balance.add(initialValue);
		expectedOperationList.add(Operation.ofDeposit(0l, initialValue, balance));
		balance = balance.subtract(withdrawValue);
		expectedOperationList.add(Operation.ofWithdrawal(0l, withdrawValue, balance));
		Mockito.when(operationRepositoryMock.findAll()).thenReturn(expectedOperationList);
		Account account = new Account(operationRepositoryMock);

		final var printerMock = Mockito.mock(StatementPrinter.class);
		account.printStatement(printerMock);

		Mockito.verify(printerMock).printStatement(expectedOperationList);
		Mockito.verifyNoMoreInteractions(printerMock);
	}
}
