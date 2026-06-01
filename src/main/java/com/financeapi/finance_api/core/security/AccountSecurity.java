package com.financeapi.finance_api.core.security;

import com.financeapi.finance_api.account.internal.core.Account;
import com.financeapi.finance_api.account.internal.core.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accountSecurity")
public class AccountSecurity {
	private final AccountRepository accountRepository;

	public AccountSecurity(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public boolean isOwner(String userId, Long accountId) {
		Optional<Account> possibleAccount = accountRepository.findById(accountId);
		if (possibleAccount.isEmpty()) {
			return false;
		}
		Account account = possibleAccount.get();
		return account.getUser().getId().equals(Long.parseLong(userId));
	}
}
