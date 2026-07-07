package com.financeapi.finance_api.transaction.internal.core;

import com.financeapi.finance_api.core.validation.AccountNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@lombok.Getter
public class TransferRequest {
	@AccountNumber private String senderAccountNumber;
	@AccountNumber private String receiverAccountNumber;
	@NotBlank private String title;
	@Positive private BigDecimal amount;
}
