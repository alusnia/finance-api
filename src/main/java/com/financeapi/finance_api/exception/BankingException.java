package com.financeapi.finance_api.exception;

import org.springframework.http.HttpStatus;

public class BankingException extends RuntimeException {
	private final HttpStatus status;
	private final String errorCode;

	public BankingException(String errorCode, String message, HttpStatus status) {
		super(message);
		this.errorCode = errorCode;
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
