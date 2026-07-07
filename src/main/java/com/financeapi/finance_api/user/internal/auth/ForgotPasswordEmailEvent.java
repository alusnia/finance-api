package com.financeapi.finance_api.user.internal.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

record ForgotPasswordEmailEvent(
		@NotBlank @Email String email,
		@NotBlank String token
) {
}
