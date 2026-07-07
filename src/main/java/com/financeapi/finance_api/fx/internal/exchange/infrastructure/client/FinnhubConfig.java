package com.financeapi.finance_api.fx.internal.exchange.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class FinnhubConfig {

	@Value("${finnhub.api.key}")
	private String apiKey;
	@Value("${finnhub.api.url}")
	private String baseUrl;

	@Bean
	public RestClient finnhubRestClient() {
		JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
		requestFactory.setReadTimeout(Duration.ofSeconds(3));

		return  RestClient.builder()
				.baseUrl(baseUrl)
				.defaultHeader("X-Finnhub-Token", apiKey)
				.requestFactory(requestFactory)
				.build();
	}
}
