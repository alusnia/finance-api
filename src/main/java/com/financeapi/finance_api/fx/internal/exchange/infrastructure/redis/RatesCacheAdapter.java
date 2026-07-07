package com.financeapi.finance_api.fx.internal.exchange.infrastructure.redis;

import com.financeapi.finance_api.common.exception.BankingException;
import com.financeapi.finance_api.common.system.Currency;
import com.financeapi.finance_api.fx.internal.exchange.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.financeapi.finance_api.common.exception.BankingError.*;

@Repository
@RequiredArgsConstructor
public class RatesCacheAdapter implements RatesCache{
	private final StringRedisTemplate redisTemplate;
	private static final String KEY_LIVE_PREFIX = "exchange_rates:";
	private static final String KEY_BACKUP_PREFIX = "last_exchange_rates:";
	private static final String KEY_MANUAL_PREFIX = "exchange_rates_manual:";
	private static final Duration TTL = Duration.ofSeconds(5);

	private Map<String, String> translateMapToStrings(Map<Currency, BigDecimal> map) {
		Map<String, String> result = new HashMap<>();
		 map.forEach((key, value) -> {
			result.put(key.name(), value == null ? null : value.toPlainString());
		 });
		 return result;
	}

	@Override
	public Optional<BigDecimal> getRate(Currency currency) {
		Object value = redisTemplate.opsForHash().get(KEY_LIVE_PREFIX, currency.name());
		if (value != null) {
			return Optional.of(new BigDecimal(value.toString()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<BackupRateResponse> getLastRate(Currency currency) {
		Object value = redisTemplate.opsForHash().get(KEY_BACKUP_PREFIX, currency.name());
		if (value != null) {
			String[] parts = value.toString().split("\\|");
			if (parts.length == 2) {
				return Optional.of(new BackupRateResponse(
						new BigDecimal(parts[0]),
						LocalDateTime.parse(parts[1])));
			} else throw new BankingException(DAMAGED_DATA);
		}
		return Optional.empty();
	}

	@Override
	public Optional<BigDecimal> getManualRate(Currency currency) {
		String currencyName = currency.name();
		Object value = redisTemplate.opsForHash().get(KEY_MANUAL_PREFIX, currencyName);
		if (value != null) {
			return Optional.of(new BigDecimal(value.toString()));
		} else {
			value = redisTemplate.opsForHash().get(KEY_MANUAL_PREFIX + currencyName, currencyName);
			if (value != null) {
				return Optional.of(new BigDecimal(value.toString()));
			}
		}
		return Optional.empty();
	}

	@Override
	public void setManualRates(Map<Currency, BigDecimal> quotes, int minutes) {
		Map<String, String> stringQuotes = translateMapToStrings(quotes);
		if (!stringQuotes.isEmpty()) {
			redisTemplate.opsForHash().putAll(KEY_MANUAL_PREFIX, stringQuotes);
			redisTemplate.expire(KEY_MANUAL_PREFIX, Duration.ofMinutes(minutes));
		}
	}

	@Override
	public void setManualRate(Currency currency, BigDecimal rate, int minutes) {
		String currencyName = currency.name();
		redisTemplate.opsForHash().put(KEY_MANUAL_PREFIX + currencyName, currencyName, rate.toPlainString());
		redisTemplate.expire(KEY_MANUAL_PREFIX + currencyName, Duration.ofMinutes(minutes));
	}

	private void setBackupRates(Map<String, String> validQuotes) {
		String now = LocalDateTime.now().toString();
		Map<String, String> backupQuotes = new HashMap<>();
		validQuotes.forEach((key, value) -> {
			backupQuotes.put(key , value + "|" + now);
		});
		redisTemplate.opsForHash().putAll(KEY_BACKUP_PREFIX, backupQuotes);
	}

	@Override
	public void setRates(Map<Currency, BigDecimal> quotes) {
		Map<String, String> stringQuotes = translateMapToStrings(quotes);
		Map<String, String> validQuotes = new HashMap<>();
		List<String> failedQuotes = new ArrayList<>();
		stringQuotes.forEach((currency, rate) -> {
			if (rate != null) {
				validQuotes.put(currency, rate);
			} else  {
				failedQuotes.add(currency);
			}
		});
		if (!validQuotes.isEmpty()) {
			redisTemplate.opsForHash().putAll(KEY_LIVE_PREFIX, validQuotes);
			redisTemplate.expire(KEY_LIVE_PREFIX, TTL);
			setBackupRates(validQuotes);
		}
		if (!failedQuotes.isEmpty()) {
			redisTemplate.opsForHash().delete(KEY_LIVE_PREFIX, failedQuotes.toArray());
		}
	}
}
