package com.financeapi.finance_api.fx.internal.exchange.infrastructure.client;

import com.financeapi.finance_api.common.system.Currency;
import com.financeapi.finance_api.fx.internal.exchange.core.ExchangeRateClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class ResilientExchangeRateClientAdapter implements ExchangeRateClient {
	private final List<ExchangeRateProvider> providers;

	@Override
	public Map<Currency, BigDecimal> downloadRates(Map<Currency, BigDecimal> rates) {
		for (ExchangeRateProvider provider : providers) {
			try {
				Map<String, BigDecimal> rawNetworkData = provider.fetchRawData();
				if (rawNetworkData == null || rawNetworkData.isEmpty()) {
					continue;
				}
				rates.replaceAll((currency, rate) -> {
					if (rate == null) {
						return rawNetworkData.get(currency.name());
					}
					return rate;
				});
				if (!rates.containsValue(null)) {
					break;
				}
			} catch (Exception e) {
				log.error("Error while fetching rates for provider " + provider.getClass().getName(), e);
			}
		}
		return rates;
	}
}
