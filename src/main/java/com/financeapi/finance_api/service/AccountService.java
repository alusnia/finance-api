package com.financeapi.finance_api.service;

import com.financeapi.finance_api.entity.Account;
import com.financeapi.finance_api.exception.BankingException;
import com.financeapi.finance_api.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountService {

	private final AccountRepository accountRepository;
	private final TransactionService transactionService;
	private final CurrencyService currencyService;

	public AccountService(AccountRepository accountRepository, TransactionService transactionService, CurrencyService currencyService) {
		this.accountRepository = accountRepository;
		this.transactionService = transactionService;
		this.currencyService = currencyService;
	}

	@Transactional
	public void transferMoney(Long accountId, String receiverAccountNumber, String title, BigDecimal senderAmount) {
		BigDecimal exchangeFee = null;
		BigDecimal exchangeRate = null;
		BigDecimal receiverAmount = senderAmount;

		log.info("{}: {} {} {}", title, accountId, receiverAccountNumber, senderAmount);
		Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber)
				.orElseThrow(() -> new IllegalArgumentException("Receiver account not found"));
		Account senderAccount = accountRepository.findById(accountId)
				.orElseThrow(() -> new IllegalArgumentException("Sender account not found"));
		if (senderAccount.isTheSameAccount(receiverAccount)) {
			throw new BankingException("TRANSFER_001", "Sender and receiver accounts cannot be the same", HttpStatus.BAD_REQUEST);
		}
		if (!senderAccount.isTheSameCurrency(receiverAccount))
		{
			com.financeapi.finance_api.dto.ExchangeDetails exchangeDetails = currencyService.makeExchange(
					senderAmount,
					senderAccount.getCurrency(),
					receiverAccount.getCurrency()
			);
			senderAmount = senderAmount.add(exchangeDetails.fee());
			receiverAmount = exchangeDetails.receiverAmount();
			exchangeRate = exchangeDetails.rate();
			exchangeFee = exchangeDetails.fee();
		}
		if (senderAccount.hasInsufficientFunds(senderAmount)) {
			throw new BankingException("FUNDS_001", "Insufficient funds to make a transfer", HttpStatus.BAD_REQUEST);
		}

		senderAccount.withdraw(senderAmount);
		receiverAccount.deposit(receiverAmount);

		TransactionService.TransferDataRecord dataRecord = new TransactionService.TransferDataRecord(
				title,
				receiverAccount,
				senderAccount,
				receiverAmount,
				senderAmount,
				exchangeRate,
				exchangeFee
		);
		transactionService.saveTransfer(dataRecord);
	}
}
