package com.financeapi.finance_api.user.internal.registration;

import com.financeapi.finance_api.core.validation.Pesel;
import com.financeapi.finance_api.core.validation.ValidName;
import jakarta.validation.constraints.Email;

record RegisterRequest(
		@ValidName String firstName,
		@ValidName String lastName,
		@Email String email,
		@Pesel String pesel
		) {}
