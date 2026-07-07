package com.financeapi.finance_api.user.internal.core;

import com.financeapi.finance_api.core.security.global.TokenExpiration;

public record CreateUserCommand(String firstName, String lastName, String email, String pesel, TokenExpiration tokenPurpose) {
}
