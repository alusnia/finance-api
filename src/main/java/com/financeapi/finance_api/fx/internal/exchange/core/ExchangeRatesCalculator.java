package com.financeapi.finance_api.fx.internal.exchange.core;

import com.financeapi.finance_api.common.exception.BankingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.financeapi.finance_api.common.exception.BankingError.*;
import static com.financeapi.finance_api.fx.internal.exchange.core.ExchangeType.*;

@Component
public class ExchangeRatesCalculator {
	@Value("${fx.exchange.CurrencyFee:0.01}")
	private BigDecimal exchangeFee;

	public BigDecimal calculateRate(BigDecimal rate, ExchangeType exchangeType) throws BankingException {
		if (rate == null) {
			throw new BankingException(DAMAGED_DATA);
		}
		if (exchangeType == SELL) {
			return rate.subtract(rate.multiply(exchangeFee));
		} else if (exchangeType == BUY) {
			return rate.add(rate.multiply(exchangeFee));
		} else {
			throw new BankingException(WRONG_ARGUMENT)
					.withCustomMessage("Different exchange type than buy or sell");
		}
	}
}
