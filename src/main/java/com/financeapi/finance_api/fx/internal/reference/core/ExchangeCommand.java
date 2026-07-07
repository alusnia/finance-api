package com.financeapi.finance_api.fx.internal.reference.core;

import com.financeapi.finance_api.common.system.Currency;
import com.financeapi.finance_api.fx.internal.exchange.core.ExchangeRequest;

import java.math.BigDecimal;

public record ExchangeCommand(
		BigDecimal amount,
		Currency senderCurrency,
		Currency receiverCurrency
) {
	public static ExchangeCommand from(ExchangeRequest request) {
		return new ExchangeCommand(
				request.amount(),
				request.senderCurrency(),
				request.receiverCurrency()
		);
	}
}
