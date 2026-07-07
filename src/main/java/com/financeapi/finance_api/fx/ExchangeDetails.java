package com.financeapi.finance_api.fx;

import java.math.BigDecimal;

public record ExchangeDetails (
		BigDecimal receiverAmount,
		BigDecimal fee,
		BigDecimal rate
) {}
