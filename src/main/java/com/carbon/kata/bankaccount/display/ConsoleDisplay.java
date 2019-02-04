package com.carbon.kata.bankaccount.display;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ConsoleDisplay implements OperationsDisplay {
	public static final String SEPARATOR = " | ";

	public void displayOperations(String clientName, List<DisplayableData> operations) {
		if (operations != null && !operations.isEmpty()) {
			StringBuilder headerBuilder = new StringBuilder();
			List<String> headers = operations.get(0).getHeaders();
			headers.forEach(h -> headerBuilder.append(SEPARATOR + h));
			System.out.println(headerBuilder + SEPARATOR);

			for (DisplayableData operation : operations) {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < operation.getOperationsAsString().size(); i++) {
					builder.append(SEPARATOR + String.format("%-" + headers.get(i).length() + "s", operation.getOperationsAsString().get(i)));
				}
				System.out.println(builder + SEPARATOR);
			}
		}

	}

	public void displayAccountBalance(String clientName, Optional<BigDecimal> balance) {
		if(balance.isPresent()) {
			System.out.println(clientName + " account balance = " + balance.get());
		} else {
			System.out.println(clientName + " does not exists in the bank");
		}
	}
}
