package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.time.Clock;
import java.util.List;

import com.carbon.kata.bankaccount.display.DisplayManager;
import com.carbon.kata.bankaccount.display.DisplayableData;

public class ProxyCarbonBank extends CarbonBank {

	private DisplayManager displayManager;

	public ProxyCarbonBank(Clock clock) {
		super(clock);
	}

	public ProxyCarbonBank(Clock clock, DisplayManager displayManager) {
		super(clock);
		this.displayManager = displayManager;
	}

	@Override
	public Bank addClient(String clientName) {
		return super.addClient(clientName);
	}

	@Override
	public BigInteger depositOrWithdrawalOnClientAccount(String clientName, BigInteger amount) {
		return super.depositOrWithdrawalOnClientAccount(clientName, amount);
	}

	@Override
	public BigInteger getClientAccountBalance(String clientName) {
		return super.getClientAccountBalance(clientName);
	}

	@Override
	public List<DisplayableData> getOperationsHistoricOnClientAccount(String clientName) {
		return super.getOperationsHistoricOnClientAccount(clientName);
	}

	public void displayOperations(String clientName) {
		displayManager.displayOperations(getOperationsHistoricOnClientAccount(clientName));
	}
}
