package com.carbon.kata.bankaccount;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

import com.carbon.kata.bankaccount.event.EventManager;

class DisplayOperationsTest {

	@Test
	void testToCreatePrinterAndDisplayd() {
		Bank bank = new ProxyBank(Clock.fixed(Instant.now(), ZoneId.systemDefault()), new EventManager());
	}

}
