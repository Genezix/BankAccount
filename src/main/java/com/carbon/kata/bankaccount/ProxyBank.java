package com.carbon.kata.bankaccount;

import java.math.BigInteger;
import java.time.Clock;
import java.util.List;

import com.carbon.kata.bankaccount.event.EventManager;
import com.carbon.kata.bankaccount.exception.ClientAccountAlreadyExists;
import com.carbon.kata.bankaccount.exception.ClientAccountDoesNotExists;

public class ProxyBank extends CarbonBank {

	
	
	private EventManager eventManager;

	public ProxyBank(Clock clock, EventManager eventManager) {
		super(clock);
		this.eventManager = eventManager;
	}

	@Override
	public Bank addClient(String clientName) throws ClientAccountAlreadyExists {
		return super.addClient(clientName);
	}

	@Override
	public Bank operationOnClientAccount(String clientName, BigInteger amount)
			throws ClientAccountDoesNotExists {
		return super.operationOnClientAccount(clientName, amount);
	}

	@Override
	public BigInteger getClientAccountBalance(String clientName) {
		return super.getClientAccountBalance(clientName);
	}

	@Override
	public List<String> getOperationsHistoricOnClientAccount(String clientName) {
		return super.getOperationsHistoricOnClientAccount(clientName);
	}

}
