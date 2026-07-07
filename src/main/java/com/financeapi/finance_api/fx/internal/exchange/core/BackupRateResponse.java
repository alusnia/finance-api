package com.financeapi.finance_api.fx.internal.exchange.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BackupRateResponse(
		BigDecimal value,
		LocalDateTime timestamp
) {
}
