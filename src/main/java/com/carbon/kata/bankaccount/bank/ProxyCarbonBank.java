package com.carbon.kata.bankaccount.bank;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carbon.kata.bankaccount.display.OperationsDisplay;

public class ProxyCarbonBank extends CarbonBank {

	private List<OperationsDisplay> operationDisplays = new ArrayList<>();

	public ProxyCarbonBank(OperationsDisplay... displays) {
		this(Clock.systemUTC(), displays);
	}

	public ProxyCarbonBank(Clock clock, OperationsDisplay... displays) {
		super(clock);
		operationDisplays.addAll(Arrays.asList(displays));
	}

	public void displayOperations(String clientName) {
		operationDisplays.forEach(od -> od.displayOperations(clientName, getOperations(clientName)));
	}

	public void displayAccountBalance(String clientName) {
		operationDisplays.forEach(od -> od.displayAccountBalance(clientName, getBalance(clientName)));
	}
}
