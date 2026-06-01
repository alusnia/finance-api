package com.financeapi.finance_api.account.internal.core;

@lombok.Getter
public class AccountDetails {
	private final Long id;
	private final String accountNumber;;
	private final String balance;
	private final String currency;
	private final Long ownerId;

	public AccountDetails(Long id, String accountNumber, String balance, String currency, Long ownerId) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.currency = currency;
		this.ownerId = ownerId;
	}
}
