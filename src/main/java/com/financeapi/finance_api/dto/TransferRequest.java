package com.financeapi.finance_api.dto;

import java.math.BigDecimal;

public class TransferRequest {

	private Long accountId;
	private String receiverAccountNumber;
	private String title;
	private BigDecimal amount;

	public Long getAccountId() {
		return accountId;
	}

	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public String getTitle() {
		return title;
	}

	public BigDecimal getAmount() {
		return amount;
	}

}
