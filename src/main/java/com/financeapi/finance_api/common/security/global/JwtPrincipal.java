package com.financeapi.finance_api.core.security.global;

import com.financeapi.finance_api.core.system.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record JwtPrincipal(
		Long id,
		String cif,
		String email,
		Role role,
		Collection<? extends GrantedAuthority> authorities
) {
}
