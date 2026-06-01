package com.financeapi.finance_api.core.security.global;

public enum TokenExpiration {
	PASSWORD_RESET(5),
	LOGIN(15),
	REGISTRATION(30);

	private long minutes;
	TokenExpiration(int minutes) {
		this.minutes = minutes;
	}

	public long getMinutes() {
		return minutes;
	}
}
