package com.financeapi.finance_api.core.system;

import lombok.Getter;

@Getter
public enum SuccessDetails {
	// --- ACCOUNT ---

	// --- AUTH ---
	LOGIN_SUCCESS("LOGIN_SUCCESS", "User logged in successfully."),
	NO_PROFILE("NO_PROFILE", "User logged in but some data is required.", "/api/user/profile"),

	// --- CURRENCY ---

	// --- JWT ---

	// --- MISC ---

	// --- REGISTRATION ---
	PESEL_FREE("PESEL_FREE", "Pesel is free", "/api/user/register"),
	PESEL_NOT_FREE("PESEL_NOT_FREE", "Pesel is not free, please verify conflict", "/api/user/register"),

	// --- SECURITY ---

	// --- TRANSACTION ---

	// --- USER ---

	// --- USER_CREDENTIALS ---
	;
	private final String status;
	private final String message;
	private final String nextActionUrl;

	SuccessDetails(String status, String message) {
		this.status = status;
		this.message = message;
		this.nextActionUrl = null;
	}

	SuccessDetails(String status, String message, String nextActionUrl) {
		this.status = status;
		this.message = message;
		this.nextActionUrl = nextActionUrl;
	}
}
