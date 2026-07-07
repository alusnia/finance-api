package com.financeapi.finance_api.fx.internal.exchange.core;

import com.financeapi.finance_api.common.system.Currency;

import java.math.BigDecimal;

public record ExchangeRequest(
		BigDecimal amount,
		Currency senderCurrency,
		Currency receiverCurrency
) {
}
