package com.financeapi.finance_api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank(message = "Pesel is required.")
@Pattern(regexp = "^\\d{11}$", message = "Pesel should have exactly 11 digits.")
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Pesel {
	String message() default "Pesel is invalid.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
