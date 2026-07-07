package com.financeapi.finance_api.core.security.global;

import com.financeapi.finance_api.core.system.Role;
import jakarta.validation.constraints.NotBlank;

public record TokenCommand(@NotBlank String cif, Long id, Role role, String email, Long timeInMillis) {
}
