package com.financeapi.finance_api.user.internal.registration;

import com.financeapi.finance_api.core.validation.Pesel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

record CheckPeselRequest(
		@Pesel String pesel,
		@NotBlank @Email String email
		) {}
