package com.financeapi.finance_api.user.service.command;

import com.financeapi.finance_api.core.security.global.TokenExpiration;
import com.financeapi.finance_api.user.entity.Role;

public record CreateUserCommand(Role role, String firstName, String lastName, String email, String address, String password, String pesel, TokenExpiration tokenPurpose, String mothersMaidenName) {
}
