package com.financeapi.finance_api.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BankingError {

	// --- ACCOUNT (ACCT_xxx) ---
	ACCOUNT_NOT_FOUND("ACCT_001", "Account not found.", HttpStatus.NOT_FOUND),

	// --- AUTH (AUTH_xxx) ---
	AUTH_ACCESS_DENIED_LOGIN("AUTH_001", "Invalid login or password.", HttpStatus.UNAUTHORIZED, "Login was incorrect."),
	AUTH_ACCESS_DENIED_PASSWORD("AUTH_001", "Invalid login or password.", HttpStatus.UNAUTHORIZED, "Password was incorrect."),

	// --- CURRENCY (CURR_xxx) ---
	CURRENCY_NO_RATES_AVAILABLE("CURR_001", "Currency rates are not available.\nTry again later.", HttpStatus.GATEWAY_TIMEOUT),

	// --- EMAIL (EMIL_xxx) ---
	EMAIL_NOT_SENT("EMIL_001", "Email message attempt failed.", HttpStatus.INTERNAL_SERVER_ERROR),

	// --- INTERNAL (ITRL_xxx) ---
	NUMBER_GENERATION("ITRL_001", "Could not generate unique number.", HttpStatus.INTERNAL_SERVER_ERROR),

	// --- JWT (JWT_xxx) ---
	JWT_TOKEN_NOT_VALID("JWT_001", "Token not valid.", HttpStatus.UNAUTHORIZED),
	JWT_TOKEN_EXPIRED("JWT_002", "Token expired.", HttpStatus.UNAUTHORIZED),

	// --- MISC (MISC_xxx) ---
	EMPTY_ARGUMENT("MISC_001", "One of required information is empty.", HttpStatus.BAD_REQUEST),
	WRONG_ARGUMENT("MISC_002", "One of required information is not valid.", HttpStatus.BAD_REQUEST),
	NOT_FOUND("MISC_003", "Not found.", HttpStatus.NOT_FOUND),

	// --- REGISTRATION (RGSR_xxx) ---
	REGISTRATION_MAIL_NOT_SEND("RGSR_001", "Email message attempt failed.", HttpStatus.INTERNAL_SERVER_ERROR),
	// --- SECURITY (SEC_xxx) ---

	// --- TRANSACTION (TRAN_xxx) ---
	TRANSACTION_SAME_ACCOUNTS("TRAN_001", "Sender and receiver accounts cannot be the same.", HttpStatus.BAD_REQUEST),
	TRANSACTION_INSUFFICIENT_FUNDS("TRAN_002", "Insufficient funds.", HttpStatus.BAD_REQUEST),

	// --- USER (USR_xxx) ---
	USER_NOT_FOUND("USER_001", "User not found.", HttpStatus.NOT_FOUND),
	USER_PESEL_CONFLICT("USER_002", "User database conflict.", HttpStatus.CONFLICT, "PESEL number is not unique."),
	USER_EMAIL_CONFLICT("USER_002", "User database conflict.", HttpStatus.CONFLICT, "email is not unique."),
	USER_INVALID_ROLE("USER_003", "Invalid role.", HttpStatus.BAD_REQUEST, "Invalid role."),

	// --- USER_CREDENTIALS (USRC_xxx) ---
	USER_CREDENTIALS_TOKEN_NOT_FOUND("USRC_001", "Token was already used or never requested.", HttpStatus.IM_USED),
	;
	private final String code;
	private final String message;
	private final HttpStatus status;
	private final String devLog;

	BankingError(String code, String message, HttpStatus status) {
		this.code = code;
		this.message = message;
		this.status = status;
		this.devLog = null;
	}

	BankingError(String code, String message, HttpStatus status, String devLog) {
		this.code = code;
		this.message = message;
		this.status = status;
		this.devLog = devLog;
	}
}
