package com.financeapi.finance_api.service;

import com.financeapi.finance_api.dto.ExchangeDetails;
import com.financeapi.finance_api.dto.NbpExchangeRateResponse;
import com.financeapi.finance_api.exception.BankingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Service
public class CurrencyService {

	@Value("${bank.currency.sender-currency-fee-multiplyer}")
	private BigDecimal feeMultiplier;
	private final RestClient restClient;

	public CurrencyService() {
		JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
		requestFactory.setReadTimeout(Duration.ofSeconds(5));
		this.restClient = RestClient.builder()
				.baseUrl("https://api.nbp.pl/api/exchangerates/rates/a/")
				.requestFactory(requestFactory)
				.build();
	}

	public NbpExchangeRateResponse getNbpExchangeRateResponse(String currency) {
		try {
			return restClient.get()
					.uri("/{currency}/?format=json", currency)
					.retrieve()
					.body(NbpExchangeRateResponse.class);
		}
		catch (Exception e) {
			throw new BankingException("CURRENCY_001", "Currency rates are not available currently", HttpStatus.GATEWAY_TIMEOUT);
		}
	}


	public BigDecimal getMidRate(String currency) {
		return getNbpExchangeRateResponse(currency).rates().get(0).midRate();
	}

	public ExchangeDetails makeExchange(BigDecimal amount, String senderCurrency, String receiverCurrency) {
		BigDecimal senderExchangeRate = BigDecimal.ONE;
		BigDecimal receiverExchangeRate = BigDecimal.ONE;

		if (!senderCurrency.equals("PLN")) {
			senderExchangeRate = getMidRate(senderCurrency);
		}
		if (!receiverCurrency.equals("PLN")) {
			receiverExchangeRate = getMidRate(receiverCurrency);
		}
		BigDecimal exchangeRate = senderExchangeRate.divide(receiverExchangeRate, 4, RoundingMode.HALF_UP);
		BigDecimal receiverAmount = amount.multiply(exchangeRate);
		BigDecimal fee = amount.multiply(feeMultiplier);
		return new ExchangeDetails(receiverAmount, fee, exchangeRate);
	}
}
