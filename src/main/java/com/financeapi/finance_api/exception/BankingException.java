package com.financeapi.finance_api.exception;

import com.financeapi.finance_api.entity.enums.BankingError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class BankingException extends RuntimeException {
	private final HttpStatus status;
	private final String errorCode;

	public enum LogType {
		INFO,
		WARNING,
		ERROR
	}

	//[Custom]Custom Exception
	public BankingException(String errorCode, String message, HttpStatus status) {
		super(message);
		this.errorCode = errorCode;
		this.status = status;
	}
	//[Silent]Exception without log
	public BankingException(BankingError bankingError) {
		super(bankingError.getMessage());
		this.status = bankingError.getStatus();
		this.errorCode = bankingError.getCode();
	}
	//[Standard log]Exception with standard log
	public BankingException(BankingError bankingError, LogType logType) {
		super(bankingError.getMessage());
		this.status = bankingError.getStatus();
		this.errorCode = bankingError.getCode();
		String devLog = bankingError.getDevLog();
		log(devLog, logType);
	}

	//[Silent, Standard log]
	public BankingException withCustomMessage(String message) {
		return new BankingException(this.errorCode, message, this.status);
	}

	//[Silent, Standard log]
	public BankingException extendMessage(String message) {
		String newMessage = String.format("%s %s", this.getMessage(), message);
		return new BankingException(this.errorCode, newMessage, this.status);
	}

	//[Silent, Standard log]
	public BankingException withCustomStatus(HttpStatus status) {
		return new BankingException(this.errorCode, this.getMessage(), status);
	}

	//[Silent, Standard log]
	public BankingException withCustomErrorCode(String errorCode) {
		return new BankingException(errorCode, this.getMessage(), this.status);
	}

	//[*]Custom log, logs may duplicate if used with [standard log]constructor
	public BankingException log(String devLog, LogType logType) {
		if (devLog != null && logType != null) {
			switch (logType) {
				case INFO -> log.info(devLog);
				case WARNING -> log.warn(devLog);
				case ERROR -> log.error(devLog);
			}
		}
		return this;
	}
}
