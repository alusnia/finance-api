package com.financeapi.finance_api.fx.internal.exchange.core;

import com.financeapi.finance_api.common.exception.BankingError;
import com.financeapi.finance_api.common.exception.BankingException;
import com.financeapi.finance_api.common.system.Currency;
import com.financeapi.finance_api.fx.CurrencyBackupRateMissingEvent;
import com.financeapi.finance_api.fx.CurrencyRateMissingEvent;
import com.financeapi.finance_api.fx.CurrencyReferenceFacade;
import com.financeapi.finance_api.fx.CurrencyReferenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.financeapi.finance_api.common.exception.BankingError.*;
import static com.financeapi.finance_api.fx.internal.exchange.core.ExchangeType.*;

@Component
@RequiredArgsConstructor
public class EmergencyExchangeRateCalculator {
	private final CurrencyReferenceFacade currencyReferenceFacade;
	private final RatesCache ratesCache;
	private final ApplicationEventPublisher applicationEventPublisher;
	@Value("${fx.exchange.CurrencyServiceErrorMinutes:15}")
	private int errorMinutes;

	private BigDecimal getSafeDelta(BackupRateResponse backupRate, CurrencyReferenceResponse response) {

		LocalDateTime currentTime = LocalDateTime.now();
		BigDecimal maxDelta = Stream.of(response.dailyMean(), response.weeklyMean(), response.monthlyMean())
				.map(mean -> mean.subtract(backupRate.value()).abs())
				.max(Comparator.naturalOrder())
				.get();
		double millis = Duration.between(backupRate.timestamp(), currentTime).toMillis();
		if (millis <= 0) {
			throw new BankingException(TIME_SYNC);
		}
		double extraPercentage = Math.pow((millis / TimeUnit.MINUTES.toMillis(errorMinutes + 1)), 2);
		return maxDelta.multiply(BigDecimal.valueOf(extraPercentage).add(BigDecimal.ONE));
	}

	private boolean timeCheck(LocalDateTime date) {
		LocalDateTime now = LocalDateTime.now();
		return now.isAfter(date.plusMinutes(errorMinutes));
	}

	public BigDecimal calculateMissingRate(Currency currency, ExchangeType type) {
		Optional<BackupRateResponse> potentialBackupResponse = ratesCache.getLastRate(currency);
		if (potentialBackupResponse.isEmpty()) {
			applicationEventPublisher.publishEvent(new CurrencyBackupRateMissingEvent(
					currency,
					LocalDateTime.now()
			));
			throw new BankingException(EMPTY_ARGUMENT);
		}
		BackupRateResponse backupResponse = potentialBackupResponse.get();
		if (timeCheck(backupResponse.timestamp()))
		{
			applicationEventPublisher.publishEvent(new CurrencyRateMissingEvent(
					currency,
					backupResponse.value(),
					backupResponse.timestamp()
			));
			throw new BankingException(CURRENCY_NO_RATES_AVAILABLE);
		}
		CurrencyReferenceResponse referenceResponse = currencyReferenceFacade.getCurrencyDetails(currency);
		BigDecimal deltaMax = getSafeDelta(backupResponse, referenceResponse);
		if (type == BUY)
			return backupResponse.value().add(deltaMax);
		else if (type == SELL)
			return backupResponse.value().subtract(deltaMax);
		throw new BankingException(WRONG_ARGUMENT)
				.withCustomMessage("Different exchange type than buy or sell");
	}
}
