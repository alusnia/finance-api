package com.financeapi.finance_api.fx.internal.exchange.core;

import com.financeapi.finance_api.common.system.Currency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface RatesCache {
	Optional<BigDecimal> getRate(Currency currency);
	Optional<BackupRateResponse> getLastRate(Currency currency);
	Optional<BigDecimal> getManualRate(Currency currency);
	void setRates(Map<Currency, BigDecimal> rates);
	void setManualRates(Map<Currency, BigDecimal> quotes, int minutes);
	void setManualRate(Currency currency, BigDecimal rate, int minutes);
}
