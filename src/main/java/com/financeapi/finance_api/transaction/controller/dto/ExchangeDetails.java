package com.financeapi.finance_api.transaction.controller.dto;

import java.math.BigDecimal;

public record ExchangeDetails (
		BigDecimal receiverAmount,
		BigDecimal fee,
		BigDecimal rate
) {}
