package com.financeapi.finance_api.fx;

import java.math.BigDecimal;

public record CurrencyReferenceResponse(
		BigDecimal dailyMean,
		BigDecimal weeklyMean,
		BigDecimal monthlyMean
) {
}
