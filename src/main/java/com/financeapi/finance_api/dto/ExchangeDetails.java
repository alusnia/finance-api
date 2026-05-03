package com.financeapi.finance_api.dto;

import java.math.BigDecimal;

public record ExchangeDetails (
		BigDecimal receiverAmount,
		BigDecimal fee,
		BigDecimal rate
) {}
