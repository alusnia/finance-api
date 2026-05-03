package com.financeapi.finance_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@lombok.Getter
@Entity
@Table(name = "bank_transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private	String receiverAccount;

	@Column(nullable = false)
	private String senderAccount;

	@Column(nullable = false)
	private String receiverName;

	@Column(nullable = false)
	private String senderName;

	@Positive
	@Column(nullable = false)
	private BigDecimal receiverAmount;

	@Positive
	@Column(nullable = false)
	private BigDecimal senderAmount;

	@Column(nullable = false)
	private String senderCurrency;

	@Column(nullable = false)
	private String receiverCurrency;

	@Column()
	private BigDecimal exchangeRate;

	@Column()
	private BigDecimal exchangeFee;

	@Column(name = "transaction_date", nullable = false)
	private LocalDateTime date;

	public Transaction() {
	}

	public Transaction(String title, String receiverAccount, String senderAccount,
					   String receiverName, String senderName, BigDecimal receiverAmount, BigDecimal senderAmount,
					   String receiverCurrency, String senderCurrency, BigDecimal exchangeRate, BigDecimal exchangeFee) {
		this.title = title;
		this.receiverAccount = receiverAccount;
		this.senderAccount = senderAccount;
		this.receiverName = receiverName;
		this.senderName = senderName;
		this.receiverAmount = receiverAmount;
		this.senderAmount = senderAmount;
		this.senderCurrency = senderCurrency;
		this.receiverCurrency = receiverCurrency;
		this.exchangeRate = exchangeRate;
		this.exchangeFee = exchangeFee;
		this.date = LocalDateTime.now();
	}

}
