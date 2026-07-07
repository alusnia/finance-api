package com.financeapi.finance_api.fx.internal.exchange.infrastructure.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Order(1)
@Component
@RequiredArgsConstructor
class FinnhubExchangeRateClientAdapter implements ExchangeRateProvider {

	private final RestClient finnhubRestClient;

	@Override
	 public Map<String, BigDecimal> fetchRawData() {
		FinnhubRatesResponse response = finnhubRestClient.get()
				.uri("/forex/rates?base=PLN")
				.retrieve()
				.body(FinnhubRatesResponse.class);
		return response != null ? response.quote() : null;
	}
}
