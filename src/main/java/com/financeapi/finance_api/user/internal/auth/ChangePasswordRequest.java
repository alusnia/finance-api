package com.financeapi.finance_api.user.internal.auth;


import com.financeapi.finance_api.core.validation.StrongPassword;
import lombok.Getter;

@Getter
class ChangePasswordRequest {
	@StrongPassword private String newPassword;
}
