package com.carbon.kata.bank.display;

import static org.mockito.ArgumentMatchers.any;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import com.carbon.kata.bank.account.Operation;

class ConsoleStatementPrinterShouldTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@BeforeEach
	void init() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@Test
	@DisplayName("Console statement printer should display in console formatted operation list")
	void printOperationFormatedByAnOperationFormatter() {
		final var expectedHeader = "OperationHeader";
		final var expectedOperation = "OperationLine";
		final var expectedPrint = expectedHeader + System.lineSeparator() + expectedOperation
				+ System.lineSeparator() + expectedOperation + System.lineSeparator();

		final var operationFormatter = Mockito.mock(OperationFormatter.class);
		Mockito.when(operationFormatter.getHeader()).thenReturn(expectedHeader);
		Mockito.when(operationFormatter.formatOperation(any())).thenReturn(expectedOperation);

		Operation operation = Mockito.mock(Operation.class);

		// Act
		final var printer = new ConsoleStatementPrinter(operationFormatter);
		printer.printStatement(Arrays.asList(operation, operation));

		// Assert
		Mockito.verify(operationFormatter, VerificationModeFactory.times(1)).getHeader();
		Mockito.verify(operationFormatter, VerificationModeFactory.times(2)).formatOperation(operation);
		Mockito.verifyNoMoreInteractions(operationFormatter);
		Assertions.assertEquals(expectedPrint, outContent.toString());
	}

	@Test
	@DisplayName("Console statement printer should display in console formatted operation list")
	void printOnlyHeaderIfOperationListIsEmpty() {
		final var expectedHeader = "OperationHeader";
		final var expectedPrint = expectedHeader + System.lineSeparator();

		final var operationFormatter = Mockito.mock(OperationFormatter.class);
		Mockito.when(operationFormatter.getHeader()).thenReturn(expectedHeader);

		// Act
		final var printer = new ConsoleStatementPrinter(operationFormatter);
		printer.printStatement(Collections.emptyList());

		// Assert
		Mockito.verify(operationFormatter, VerificationModeFactory.times(1)).getHeader();
		Mockito.verifyNoMoreInteractions(operationFormatter);
		Assertions.assertEquals(expectedPrint, outContent.toString());
	}

	@AfterEach
	void tearDown(TestInfo testInfo) {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}
}
