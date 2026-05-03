package com.financeapi.finance_api.dto;

import java.time.LocalDateTime;

@lombok.Getter
public class ErrorResponse {
	private final LocalDateTime timestamp;
	private final int status;
	private final String error;
	private final String message;

	public ErrorResponse(int status, String error, String message) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.error = error;
		this.message = message;
	}
}
