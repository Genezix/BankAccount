package com.carbon.kata.bankaccount.display;

import java.util.List;

public interface DisplayableData {
	public List<String> getHeaders();
	
	public List<String> getOperationsAsString();
}
