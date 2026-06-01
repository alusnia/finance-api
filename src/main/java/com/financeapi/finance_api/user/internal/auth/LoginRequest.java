package com.financeapi.finance_api.user.internal.auth;

import com.financeapi.finance_api.core.validation.Cif;
import jakarta.validation.constraints.NotBlank;

@lombok.Getter
class LoginRequest {
	@Cif private String cif;
	@NotBlank private String password;
}
