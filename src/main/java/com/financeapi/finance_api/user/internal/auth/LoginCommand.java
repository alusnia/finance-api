package com.financeapi.finance_api.user.internal.auth;

record LoginCommand(
		String cif,
		String password
) {
}
