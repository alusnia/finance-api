package com.financeapi.finance_api.transaction.internal.core;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper {

	public TransactionMapper() {
	}

	public TransactionDetails toDetails(Transaction transaction) {
		return new TransactionDetails(
				transaction.getId().toString(),
				transaction.getInitiatorId(),
				transaction.getTitle(),
				transaction.getReceiverAccountNumber(),
				transaction.getSenderAccountNumber(),
				transaction.getReceiverName(),
				transaction.getSenderName(),
				transaction.getReceiverAmount(),
				transaction.getSenderAmount(),
				transaction.getReceiverCurrency(),
				transaction.getSenderCurrency(),
				transaction.getExchangeRate(),
				transaction.getExchangeFee(),
				transaction.getDate().toString()
		);
	}

	public List<TransactionDetails> toDetails(List<Transaction> transactions) {
		return  transactions.stream().map(transaction -> new TransactionDetails(
				transaction.getId().toString(),
				transaction.getInitiatorId(),
				transaction.getTitle(),
				transaction.getReceiverAccountNumber(),
				transaction.getSenderAccountNumber(),
				transaction.getReceiverName(),
				transaction.getSenderName(),
				transaction.getReceiverAmount(),
				transaction.getSenderAmount(),
				transaction.getReceiverCurrency(),
				transaction.getSenderCurrency(),
				transaction.getExchangeRate(),
				transaction.getExchangeFee(),
				transaction.getDate().toString()
		)).toList();
	}
}
