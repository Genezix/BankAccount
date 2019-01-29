package com.carbon.kata.bankaccount.bank;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carbon.kata.bankaccount.display.DisplayableData;
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

	@Override
	public Bank addClient(String clientName) {
		return super.addClient(clientName);
	}

	@Override
	public BigDecimal depositOrWithdrawalOnClientAccount(String clientName, BigDecimal amount) {
		return super.depositOrWithdrawalOnClientAccount(clientName, amount);
	}

	@Override
	public BigDecimal getClientAccountBalance(String clientName) {
		return super.getClientAccountBalance(clientName);
	}

	@Override
	public List<DisplayableData> getOperationsHistoricOnClientAccount(String clientName) {
		return super.getOperationsHistoricOnClientAccount(clientName);
	}

	public void displayOperations(String clientName) {
		operationDisplays.forEach(od -> od.displayOperations(getOperationsHistoricOnClientAccount(clientName)));
	}

	public void displayAccountBalance(String clientName) {
		operationDisplays.forEach(od -> od.displayAccountBalance(getClientAccountBalance(clientName)));
	}
}
