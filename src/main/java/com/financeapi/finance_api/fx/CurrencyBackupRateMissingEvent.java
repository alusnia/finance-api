package com.financeapi.finance_api.fx;

import com.financeapi.finance_api.common.system.Currency;

import java.time.LocalDateTime;

public record CurrencyBackupRateMissingEvent(
		Currency currency,
		LocalDateTime timestamp
) {
}
