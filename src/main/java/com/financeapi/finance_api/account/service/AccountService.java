package com.financeapi.finance_api.account.service;

import com.financeapi.finance_api.core.client.Currencies;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.core.client.CurrencyService;
import com.financeapi.finance_api.transaction.service.TransactionService;
import com.financeapi.finance_api.transaction.mapper.TransactionMapper;
import com.financeapi.finance_api.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static com.financeapi.finance_api.core.exception.BankingError.*;

@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final TransactionService transactionService;
	private final CurrencyService currencyService;
	private final TransactionMapper transactionMapper;

	public AccountService(AccountRepository accountRepository, TransactionService transactionService, CurrencyService currencyService, TransactionMapper transactionMapper) {
		this.accountRepository = accountRepository;
		this.transactionService = transactionService;
		this.currencyService = currencyService;
		this.transactionMapper = transactionMapper;
	}

	private String generateAccountNumber() {
		String prefix = "PL42424242";
		StringBuilder accountNumberBuilder = new StringBuilder();
		accountNumberBuilder.append(prefix);
		for (int i = 0; i < 16; i++) {
			accountNumberBuilder.append(ThreadLocalRandom.current().nextInt(10));
		}
		return accountNumberBuilder.toString();
	}
//
//	public String generateUniqueAccountNumber() {
//		String accountNumber;
//
//		for(int i = 0; i < 1000; i++) {
//			accountNumber = generateAccountNumber();
//			if (!accountRepository.existsByAccountNumber(accountNumber)) {
//				return accountNumber;
//			}
//		}
//		throw new BankingException(NUMBER_GENERATION);
//	}
//
//	public boolean isCurrencyValid(String currency) {
//		return Arrays.stream(Currencies.values()).anyMatch(currencies -> currencies.name().equals(currency));
//	}
}
