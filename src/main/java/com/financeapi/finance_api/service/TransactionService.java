package com.financeapi.finance_api.service;

import com.financeapi.finance_api.entity.Account;
import com.financeapi.finance_api.entity.Transaction;
import com.financeapi.finance_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void saveTransfer(TransferDataRecord data) {

		Transaction transaction = new Transaction(
				data.title(),
				data.receiverAccount().getAccountNumber(),
				data.senderAccount().getAccountNumber(),
				data.receiverAccount().getUser().getFullName(),
				data.senderAccount().getUser().getFullName(),
				data.receiverAmount(),
				data.senderAmount(),
				data.receiverAccount().getCurrency(),
				data.senderAccount().getCurrency(),
				data.exchangeRate(),
				data.exchangeFee()
		);
		transactionRepository.save(transaction);
	}
}
