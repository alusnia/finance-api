package com.financeapi.finance_api.account.internal.core;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public enum AccountNumberDetails {
	POLISH("42424242","252100"),
	;

	private final String prefix;
	private final String suffix;
	AccountNumberDetails(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}
}
