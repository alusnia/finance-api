package com.financeapi.finance_api.core.security.global;

import com.financeapi.finance_api.core.system.Role;

public interface TokenRequests {
	record Standard(String cif, Long id, Role role, String email, Long timeInMillis ) implements TokenRequests {}

	record Register(String cif, Long timeInMillis ) implements TokenRequests {}

	record ChangePassword(String cif, Long timeInMillis ) implements TokenRequests {}
}
