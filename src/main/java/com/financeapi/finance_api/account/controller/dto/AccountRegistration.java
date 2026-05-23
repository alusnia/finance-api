package com.financeapi.finance_api.account.controller.dto;

import lombok.Getter;

@Getter
public class AccountRegistration {
	private String currency;

	public AccountRegistration(String currency) {
		this.currency = currency;
	}
}
