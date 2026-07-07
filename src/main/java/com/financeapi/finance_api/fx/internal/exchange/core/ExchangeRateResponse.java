package com.financeapi.finance_api.fx.internal.exchange.core;

import java.math.BigDecimal;

public record ExchangeRateResponse(
		BigDecimal rate,
		ExchangeRateType type
) {
}
