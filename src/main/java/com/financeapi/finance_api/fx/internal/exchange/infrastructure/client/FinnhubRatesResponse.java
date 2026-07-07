package com.financeapi.finance_api.fx.internal.exchange.infrastructure.client;

import java.math.BigDecimal;
import java.util.Map;

public record FinnhubRatesResponse(
		Map<String, BigDecimal> quote
) {
}
