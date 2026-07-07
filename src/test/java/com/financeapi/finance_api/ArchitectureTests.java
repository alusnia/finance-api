package com.financeapi.finance_api;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ArchitectureTests {

	@Test
	void verifyModulithArchitecture() {
		ApplicationModules.of(FinanceApiApplication.class).verify();
	}
}