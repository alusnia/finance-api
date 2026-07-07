package com.financeapi.finance_api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank(message = "CIF is required.")
@Pattern(regexp = "^\\d{10}$", message = "CIF should have exactly 10 digits.")
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cif {
	String message() default "CIF is invalid";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
