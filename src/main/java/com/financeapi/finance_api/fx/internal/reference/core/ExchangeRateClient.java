package com.financeapi.finance_api.fx.internal.reference.core;

import com.financeapi.finance_api.common.system.Currency;

import java.math.BigDecimal;
import java.util.Map;

public interface ExchangeRateClient {
	String getProviderName();
	Map<Currency, BigDecimal> fetchRates();
}
