package com.financeapi.finance_api.account.internal.core;

import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.core.system.Currencies;
import com.financeapi.finance_api.user.UserFacade;
import com.financeapi.finance_api.user.UserNotificationDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.financeapi.finance_api.core.exception.BankingError.NUMBER_GENERATION;
import static com.financeapi.finance_api.core.exception.BankingError.WRONG_CURRENCY;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;
	private final AccountMapper accountMapper;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final UserFacade userFacade;

	private String generateAccountNumber() {
		AccountNumberDetails details = AccountNumberDetails.POLISH;
		StringBuilder accountNumberBuilder = new StringBuilder();
		accountNumberBuilder.append(details.getPrefix());
		for (int i = 0; i < 16; i++) {
			accountNumberBuilder.append(ThreadLocalRandom.current().nextInt(10));
		}
		String accountNumber = accountNumberBuilder.toString();
		accountNumberBuilder.append(details.getSuffix());
		int remainder = new BigInteger(accountNumberBuilder.toString()).mod(BigInteger.valueOf(97)).intValue();
		int controlNumber = 98 - remainder;
		String formattedControlNumber = String.format("%02d", controlNumber);
		return formattedControlNumber +  accountNumber;
	}

	private String generateUniqueAccountNumber() {
		String accountNumber;

		for(int i = 0; i < 1000; i++) {
			accountNumber = generateAccountNumber();
			if (!accountRepository.existsByAccountNumber(accountNumber)) {
				return accountNumber;
			}
		}
		throw new BankingException(NUMBER_GENERATION);
	}

	private boolean isCurrencyValid(String currency) {
		return Arrays.stream(Currencies.values()).anyMatch(currencies -> currencies.name().equals(currency));
	}

	@Transactional
	Long createNewAccount(NewAccountCommand command) {
		if (!isCurrencyValid(command.currency())) {
			throw new BankingException(WRONG_CURRENCY).log("Asked currency is " + command.currency(), WARNING);
		}
		Account account = accountMapper.toEntity(command, generateUniqueAccountNumber());
		accountRepository.save(account);
		UserNotificationDetails details = userFacade.exportUserNotificationDetails(command.userId());
		applicationEventPublisher.publishEvent(accountMapper.toEvent(account, details));
		return account.getId();
	}
}
