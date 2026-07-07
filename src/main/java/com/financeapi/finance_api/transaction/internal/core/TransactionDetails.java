package com.financeapi.finance_api.transaction.internal.core;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionDetails {
	private final String id;
	private final String userId;
	private final String title;
	private final String receiverAccountNumber;
	private final String senderAccountNumber;
	private final String receiverName;
	private final String senderName;
	private final BigDecimal receiverAmount;
	private final BigDecimal senderAmount;
	private final String receiverCurrency;
	private final String senderCurrency;
	private final BigDecimal exchangeRate;
	private final BigDecimal exchangeFee;
	private final String date;

	public TransactionDetails(String id, String userId, String title, String receiverAccountNumber,
							  String senderAccountNumber, String receiverName, String senderName,
							  BigDecimal receiverAmount, BigDecimal senderAmount, String receiverCurrency,
							  String senderCurrency, BigDecimal exchangeRate, BigDecimal exchangeFee, String date) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.receiverAccountNumber = receiverAccountNumber;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverName = receiverName;
		this.senderName = senderName;
		this.receiverAmount = receiverAmount;
		this.senderAmount = senderAmount;
		this.receiverCurrency = receiverCurrency;
		this.senderCurrency = senderCurrency;
		this.exchangeRate = exchangeRate;
		this.exchangeFee = exchangeFee;
		this.date = date;
	}
}
