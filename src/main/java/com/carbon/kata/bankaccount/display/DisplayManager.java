package com.carbon.kata.bankaccount.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayManager {
	private List<OperationsDisplay> operationDisplays = new ArrayList<>();
	
	public void addDisplay(OperationsDisplay... displays) {
		operationDisplays.addAll(Arrays.asList(displays));
	}
	
	public void displayOperations(List<DisplayableData> operations) {
		operationDisplays.forEach(od -> od.displayOperations(operations));
	}
}
