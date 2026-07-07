package com.financeapi.finance_api.account;

public record NewAccountEvent(
		String email,
		String firstName,
		String currency,
		String accountNumber
) {}
