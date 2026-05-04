package com.financeapi.finance_api.dto;

import java.math.BigDecimal;

@lombok.Getter
public class TransferRequest {

	private Long accountId;
	private String receiverAccountNumber;
	private String title;
	private BigDecimal amount;

}
