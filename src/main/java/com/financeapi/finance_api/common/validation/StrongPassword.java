package com.financeapi.finance_api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
	String message() default "Password does not meet security standards.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
