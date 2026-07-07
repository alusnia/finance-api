package com.financeapi.finance_api.fx.internal.exchange.core;

import com.financeapi.finance_api.common.system.Currency;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRateClient {
	Map<Currency, BigDecimal> downloadRates(Map<Currency, BigDecimal> rates);
}
