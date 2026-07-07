package com.financeapi.finance_api.account.internal.core;

import com.financeapi.finance_api.core.system.Currencies;

import java.math.BigDecimal;

@lombok.Getter
public record AccountDetails (
		Long id,
		String userCif,
		String accountNumber,
		BigDecimal balance,
		Currencies currency,
		Account.Status status,
) {
}
