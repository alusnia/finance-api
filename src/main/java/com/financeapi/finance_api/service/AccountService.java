package com.financeapi.finance_api.service;

import com.financeapi.finance_api.entity.Account;
import com.financeapi.finance_api.exception.InsufficientFundsException;
import com.financeapi.finance_api.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

	private final AccountRepository accountRepository;

	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Transactional
	public void transferMoney(Long accountId, String receiverAccountNumber, BigDecimal amount) {
		System.out.println(accountId + " " + receiverAccountNumber + " " + amount);
		Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber)
				.orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));
		Account senderAccount = accountRepository.findById(accountId)
				.orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
		if (senderAccount.getBalance().compareTo(amount) < 0) {
			throw new InsufficientFundsException("Insufficient funds in sender account");
		} else if (senderAccount.getId().equals(receiverAccount.getId()) ) {
			throw new IllegalArgumentException("Sender and receiver accounts cannot be the same");
		}
		senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
		receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
	}
}
