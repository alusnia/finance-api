package com.financeapi.finance_api.account.internal.core;

@lombok.Getter
public class AccountRegistrationResponse {
	private String message;
	private String accountNumber;

	public AccountRegistrationResponse(String message, String accountNumber) {
		this.message = message;
		this.accountNumber = accountNumber;
	}
}
