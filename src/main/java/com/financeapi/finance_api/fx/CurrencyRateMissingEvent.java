package com.financeapi.finance_api.fx;

import com.financeapi.finance_api.common.system.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CurrencyRateMissingEvent(
		Currency currency,
		BigDecimal lastKnownRate,
		LocalDateTime lastUpdate
) {
}
