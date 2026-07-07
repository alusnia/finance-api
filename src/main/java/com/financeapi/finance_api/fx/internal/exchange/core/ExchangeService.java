package com.financeapi.finance_api.fx.internal.exchange.core;

import com.financeapi.finance_api.common.system.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.financeapi.finance_api.fx.internal.exchange.core.ExchangeRateType.*;

@Service
@RequiredArgsConstructor
public class ExchangeService {
	private final RatesCache ratesCache;
	private final ExchangeRateClient exchangeRateClient;
	private final EmergencyExchangeRateCalculator emergencyExchangeRateCalculator;
	private final ExchangeRatesCalculator exchangeRatesCalculator;

	ExchangeRateResponse getRate(Currency currency, ExchangeType exchangeType) {
		Optional<BigDecimal> potentialRate = ratesCache.getRate(currency);
		if (potentialRate.isPresent()) {
			return new ExchangeRateResponse(
					exchangeRatesCalculator.calculateRate(potentialRate.get(), exchangeType),
					STANDARD);
		}
		Map<Currency, BigDecimal> rates = initRatesDictionary();
		rates = exchangeRateClient.downloadRates(rates);
		if (rates.containsValue(null)) {
			rates = fillMissingRates(rates);
		}
		ratesCache.setRates(rates);
		BigDecimal rate = rates.get(currency);
		if (rate == null) {
			return new ExchangeRateResponse(
					emergencyExchangeRateCalculator.calculateMissingRate(currency, exchangeType),
					EMERGENCY);
		}
		return new ExchangeRateResponse(
				exchangeRatesCalculator.calculateRate(rate, exchangeType),
				STANDARD);
	}

	private Map<Currency, BigDecimal> fillMissingRates(Map<Currency, BigDecimal> rates) {
		rates.replaceAll((currency, rate) ->
			rate == null ? ratesCache.getManualRate(currency).orElse(null) : rate);
		return rates;
	}

	private Map<Currency, BigDecimal> initRatesDictionary() {
		Map<Currency, BigDecimal> map = new HashMap<>();
		Arrays.stream(Currency.values()).forEach(currency -> map.put(currency, null));
		return map;
	}
}
