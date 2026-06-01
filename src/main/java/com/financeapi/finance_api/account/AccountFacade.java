package com.financeapi.finance_api.account;


import com.financeapi.finance_api.account.internal.core.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AccountFacade {
	private final AccountRepository accountRepository;
	}
}
