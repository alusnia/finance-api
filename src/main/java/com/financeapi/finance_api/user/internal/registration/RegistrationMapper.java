package com.financeapi.finance_api.user.internal.registration;

import com.financeapi.finance_api.core.security.global.JwtPrincipal;
import com.financeapi.finance_api.core.security.global.TokenExpiration;
import com.financeapi.finance_api.user.internal.auth.Auth;
import com.financeapi.finance_api.user.internal.core.CreateUserCommand;
import com.financeapi.finance_api.user.internal.profile.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface RegistrationMapper {

	@Mapping(target = "cif", source = "principal.cif")
	SaveCheckPeselCommand toCommand(CheckPeselRequest request, JwtPrincipal principal);

	CheckPeselQuery toQuery(CheckPeselRequest request);

	RegisterCommand toCommand(RegisterRequest request);

	CreateUserCommand toCommand(RegisterCommand command, TokenExpiration tokenPurpose);

	PeselSearch toEntity(SaveCheckPeselCommand command);

	Auth toAuth(RegisterCommand command, String cif, String hashedPassword);

	Profile toProfile(RegisterCommand command);

	RegisterUserRespond toRespond(Long id, String token);
}
