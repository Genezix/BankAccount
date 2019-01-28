package com.carbon.kata.bankaccount.display;

import java.util.List;

public class OperationsDisplay {
	public static final String SEPARATOR = " | ";

	public void displayOperations(List<DisplayableData> operations) {
		if (operations != null && !operations.isEmpty()) {
			StringBuilder headerBuilder = new StringBuilder();
			List<String> headers = operations.get(0).getHeaders();
			headers.forEach(h -> headerBuilder.append(SEPARATOR + h));
			System.out.println(headerBuilder + SEPARATOR);

			for (DisplayableData operation : operations) {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < operation.getDatas().size(); i++) {
					builder.append(SEPARATOR + String.format("%-" + headers.get(i).length() + "s", operation.getDatas().get(i)));
				}
				System.out.println(builder + SEPARATOR);
			}
		}

	}
}
