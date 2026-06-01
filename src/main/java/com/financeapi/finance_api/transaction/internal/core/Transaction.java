package com.financeapi.finance_api.transaction.internal.core;

import com.financeapi.finance_api.core.system.Currencies;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@lombok.Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bank_transactions")
public class Transaction {
	public enum Status {
		PENDING, COMPLETED, FAILED, CANCELED, REJECTED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	private Long initiatorId;

	@Column(nullable = false, length = 100, updatable = false)
	private String title;

	@Column(nullable = false, length = 26, updatable = false)
	private	String receiverAccountNumber;

	@Column(nullable = false, length = 26, updatable = false)
	private String senderAccountNumber;

	@Column(nullable = false,  length = 100, updatable = false)
	private String receiverName;

	@Column(nullable = false,   length = 100, updatable = false)
	private String senderName;

	@Positive
	@Column(nullable = false, precision = 19, scale = 2, updatable = false)
	private BigDecimal receiverAmount;

	@Positive
	@Column(nullable = false,  precision = 19, scale = 2, updatable = false)
	private BigDecimal senderAmount;

	@Column(nullable = false, length = 3, updatable = false)
	@Enumerated(EnumType.STRING)
	private Currencies receiverCurrency;

	@Column(nullable = false,   length = 3, updatable = false)
	@Enumerated(EnumType.STRING)
	private Currencies senderCurrency;

	@Column(precision = 19, scale = 4, updatable = false)
	private BigDecimal exchangeRate;

	@Column(precision = 19, scale = 2, updatable = false)
	private BigDecimal exchangeFee;

	@Column(nullable = false, updatable = false)
	private LocalDateTime transactionDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Status status;

	public Transaction(Long initiatorId, String title, String receiverAccountNumber, String senderAccountNumber,
	                   String receiverName, String senderName, BigDecimal receiverAmount, BigDecimal senderAmount,
	                   Currencies receiverCurrency, Currencies senderCurrency, BigDecimal exchangeRate, BigDecimal exchangeFee) {
		this.initiatorId = initiatorId;
		this.title = title;
		this.receiverAccountNumber = receiverAccountNumber;
		this.senderAccountNumber = senderAccountNumber;
		this.receiverName = receiverName;
		this.senderName = senderName;
		this.receiverAmount = receiverAmount;
		this.senderAmount = senderAmount;
		this.senderCurrency = senderCurrency;
		this.receiverCurrency = receiverCurrency;
		this.exchangeRate = exchangeRate;
		this.exchangeFee = exchangeFee;
		this.transactionDate = LocalDateTime.now();
		this.status = Status.PENDING;
	}

}
