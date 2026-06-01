package com.financeapi.finance_api.exchange.internal;

import java.math.BigDecimal;

public record ExchangeDetails (
		BigDecimal receiverAmount,
		BigDecimal fee,
		BigDecimal rate
) {}
