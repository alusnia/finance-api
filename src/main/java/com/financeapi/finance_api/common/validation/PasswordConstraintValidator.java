package com.financeapi.finance_api.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<StrongPassword, String> {
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		boolean isValid = true;
		if (password == null || password.isBlank()) {
			return false;
		}
		if (password.length() < 12) {
			context.buildConstraintViolationWithTemplate("Password too short.")
					.addConstraintViolation();
			isValid = false;
		}
		if (!password.matches(".*[A-Z].*")) {
			context.buildConstraintViolationWithTemplate("Password does not contain uppercase letters.")
					.addConstraintViolation();
			isValid = false;
		}
		if (!password.matches(".*[a-z].*")) {
			context.buildConstraintViolationWithTemplate("Password does not contain lowercase letters.")
					.addConstraintViolation();
			isValid = false;
		}
		if (!password.matches(".*\\d.*")) {
			context.buildConstraintViolationWithTemplate("Password does not contain digits.")
					.addConstraintViolation();
			isValid = false;
		}
		if (!password.matches((".*[^a-zA-Z0-9\\s].*"))) {
			context.buildConstraintViolationWithTemplate("Password does not contain special characters.")
					.addConstraintViolation();
			isValid = false;
		}
		return isValid;
	}
}
