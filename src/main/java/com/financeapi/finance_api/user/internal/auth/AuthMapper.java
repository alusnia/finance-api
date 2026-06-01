package com.financeapi.finance_api.user.internal.auth;

import com.financeapi.finance_api.core.security.global.TokenCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface AuthMapper {
	LoginCommand toCommand(LoginRequest request);
	ForgotPasswordCommand toCommand(ForgotPasswordRequest request);
	ChangePasswordCommand toCommand(ChangePasswordRequest request, String token);

	@Mapping(target = "role", source = "user.role")
	@Mapping(target = "email", source = "user.profile.email")
	TokenCommand toCommand(Auth auth, Long timeInMillis);

	@Mapping(target = "email", source = "user.profile.email")
	@Mapping(target = "token", source = "resetToken")
	ForgotPasswordEmailEvent toEvent(Auth auth);
}
