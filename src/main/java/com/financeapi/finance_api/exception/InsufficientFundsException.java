package com.financeapi.finance_api.exception;

public class InsufficientFundsException extends RuntimeException{
	public InsufficientFundsException(String message, String errorCode, ) {
		super(message);
	}
}
