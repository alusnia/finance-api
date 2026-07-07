package com.financeapi.finance_api.fx;

import com.financeapi.finance_api.common.system.Currency;
import com.financeapi.finance_api.fx.internal.exchange.core.ExchangeRequest;
import com.financeapi.finance_api.fx.internal.reference.core.ExchangeCommand;
import com.financeapi.finance_api.fx.internal.exchange.core.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyReferenceFacade {
	private final ExchangeService exchangeService;

	public ExchangeDetails getExchangeDetails(ExchangeRequest request) {
		return exchangeService.makeExchange(ExchangeCommand.from(request));
	}

	public CurrencyReferenceResponse getCurrencyDetails(Currency currency) {

	}
}
