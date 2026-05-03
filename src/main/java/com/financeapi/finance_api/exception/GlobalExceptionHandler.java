package com.financeapi.finance_api.exception;

import com.financeapi.finance_api.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BankingException.class)
	public ResponseEntity<ErrorResponse> handleBankingException(BankingException ex) {
		ErrorResponse error = new ErrorResponse(
				ex.getStatus().value(),
				ex.getErrorCode(),
				ex.getMessage()
		);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleJsonExceptions(HttpMessageNotReadableException ex) {
		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"JSON_ERROR",
				"Wrong data format sent. Check for strings instead numericals."
		);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ErrorResponse error = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"VALIDATION_ERROR",
				"Data sent is wrong or incomplete."
		);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleIntegrityExceptions(DataIntegrityViolationException ex) {
		ErrorResponse error = new ErrorResponse(
				HttpStatus.CONFLICT.value(),
				"DB_CONFLICT",
				"Data is not unique."
		);
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NoResourceFoundException ex) {
		log.warn("Someone tryied to enter not existing address: {}", ex.getResourcePath());

		ErrorResponse error = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				"NOT_FOUND",
				"Searched address does not exist."
		);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllOtherExceptions(Exception ex) {
		log.error("CRITICAL SYSTEM ERROR: Unhandled exception occurred", ex);
		ErrorResponse error = new ErrorResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"SYSTEM_ERROR",
				"Something went wrong. Try again later."
		);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
