package com.financeapi.finance_api.user.internal.auth;

import com.financeapi.finance_api.core.validation.Cif;

record ForgotPasswordRequest(
		@Cif String cif) {
}
