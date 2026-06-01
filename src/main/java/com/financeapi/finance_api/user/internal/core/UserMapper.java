package com.financeapi.finance_api.user.internal.core;

import com.financeapi.finance_api.user.internal.profile.Profile;
import com.financeapi.finance_api.user.internal.auth.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface UserMapper {
	//Entity
	@Mapping(target = "profile", ignore = true)
	@Mapping(target = "userCredentials", ignore = true)
	@Mapping(target = "id", ignore = true)
	User toUserEntity(CreateUserCommand command);

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "id", ignore = true)
	Auth toAuthEntity(CreateUserCommand command);

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "id", ignore = true)
	Profile toProfileEntity(CreateUserCommand command);

	//Details
	@Mapping(target = "token", source = "userCredentials.resetToken")
	@Mapping(target = "cif", source = "userCredentials.cif")
	@Mapping(target = "userId", source = "id")
	CreateUserRespond toDetails(User user);

}
