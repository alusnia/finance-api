package com.financeapi.finance_api.transaction.internal.core;

import com.financeapi.finance_api.exchange.internal.CurrencyService;
import com.financeapi.finance_api.account.internal.core.Account;
import com.financeapi.finance_api.account.internal.core.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionService {

	public record TransferDataRecord(
			String title,
			Account receiverAccount,
			Account senderAccount,
			BigDecimal receiverAmount,
			BigDecimal senderAmount,
			BigDecimal exchangeRate,
			BigDecimal exchangeFee
	) {}

	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	private final CurrencyService currencyService;
	private final TransactionMapper transactionMapper;

	public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, CurrencyService currencyService, TransactionMapper transactionMapper) {
		this.transactionRepository = transactionRepository;
		 this.accountRepository = accountRepository;
		 this.currencyService = currencyService;
		 this.transactionMapper = transactionMapper;
	}

//	@Transactional
//	public TransactionDetails transferMoney(Long accountId, String receiverAccountNumber, String title, BigDecimal senderAmount) {
//		BigDecimal exchangeFee = null;
//		BigDecimal exchangeRate = null;
//		BigDecimal receiverAmount = senderAmount;
//
//		Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber)
//				.orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND).log("Receiver account not found", INFO));
//		Account senderAccount = accountRepository.findById(accountId)
//				.orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND).log("Sender account not found", INFO));
//		if (senderAccount.isTheSameAccount(receiverAccount)) {
//			throw new BankingException(TRANSACTION_SAME_ACCOUNTS);
//		}
//		if (!senderAccount.isTheSameCurrency(receiverAccount))
//		{
//			ExchangeDetails exchangeDetails = currencyService.makeExchange(
//					senderAmount,
//					senderAccount.getCurrency(),
//					receiverAccount.getCurrency()
//			);
//			senderAmount = senderAmount.add(exchangeDetails.fee());
//			receiverAmount = exchangeDetails.receiverAmount();
//			exchangeRate = exchangeDetails.rate();
//			exchangeFee = exchangeDetails.fee();
//		}
//		if (senderAccount.hasInsufficientFunds(senderAmount)) {
//			throw new BankingException(TRANSACTION_INSUFFICIENT_FUNDS);
//		}
//
//		senderAccount.withdraw(senderAmount);
//		receiverAccount.deposit(receiverAmount);
//		accountRepository.saveAll(List.of(senderAccount, receiverAccount));
//
//		TransactionService.TransferDataRecord dataRecord = new TransactionService.TransferDataRecord(
//				title,
//				receiverAccount,
//				senderAccount,
//				receiverAmount,
//				senderAmount,
//				exchangeRate,
//				exchangeFee
//		);
//		Transaction transaction = saveTransfer(dataRecord);
//		return transactionMapper.toDetails(transaction);
//	}

//	private Transaction saveTransfer(TransferDataRecord data) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String userId = authentication.getPrincipal().toString();
//
//		Transaction transaction = new Transaction(
//				userId,
//				data.title(),
//				data.receiverAccount().getAccountNumber(),
//				data.senderAccount().getAccountNumber(),
//				data.receiverAccount().getUser().getFullName(),
//				data.senderAccount().getUser().getFullName(),
//				data.receiverAmount(),
//				data.senderAmount(),
//				data.receiverAccount().getCurrency(),
//				data.senderAccount().getCurrency(),
//				data.exchangeRate(),
//				data.exchangeFee()
//		);
//		transactionRepository.save(transaction);
//		return transaction;
//	}
}
