package com.financeapi.finance_api.core.security.global;

import com.financeapi.finance_api.core.system.Role;

public record TokenResponse(String cif, Long id, Role role, String email) {
}
