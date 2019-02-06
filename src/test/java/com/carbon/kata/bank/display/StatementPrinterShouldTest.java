package com.carbon.kata.bank.display;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.carbon.kata.bank.account.Account;
import com.carbon.kata.bank.account.Operation;
import com.carbon.kata.bank.account.OperationRepository;

class StatementPrinterShouldTest {
	@Test
	@DisplayName("Verify that printer receive the good operation list to print")
	void receiveOperationListWhenAccountHaveToPrintStatement() {
		final var operationRepositoryMock = Mockito.mock(OperationRepository.class);

		final List<Operation> expectedOperationList = new LinkedList<>();
		expectedOperationList.add(Operation.ofDeposit(0l, BigDecimal.valueOf(10), BigDecimal.valueOf(10)));
		expectedOperationList.add(Operation.ofDeposit(0l, BigDecimal.valueOf(20), BigDecimal.valueOf(30)));
		expectedOperationList.add(Operation.ofWithdrawal(0l, BigDecimal.valueOf(5), BigDecimal.valueOf(25)));
		Mockito.when(operationRepositoryMock.findAll()).thenReturn(expectedOperationList);
		Account account = new Account(operationRepositoryMock);

		final var printerMock = Mockito.mock(StatementPrinter.class);
		account.printStatement(printerMock);

		Mockito.verify(printerMock).printStatement(expectedOperationList);
		Mockito.verifyNoMoreInteractions(printerMock);
	}
}
