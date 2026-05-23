package com.financeapi.finance_api.account.mapper;

import com.financeapi.finance_api.account.controller.dto.AccountDetails;
import com.financeapi.finance_api.account.entity.Account;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapper {

	public AccountMapper() {
	}

	public AccountDetails toDetails(Account account) {
		return new AccountDetails(
				account.getId(),
				account.getAccountNumber(),
				account.getBalance().toString(),
				account.getCurrency(),
				account.getUser().getId()
		);
	}

	public List<AccountDetails> toDetails(List<Account> accounts) {
		return  accounts.stream().map(account -> new AccountDetails(
				account.getId(),
				account.getAccountNumber(),
				account.getBalance().toString(),
				account.getCurrency(),
				account.getUser().getId()
		)).toList();
	}
}
