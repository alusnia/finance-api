package com.financeapi.finance_api.user.mapper;

import com.financeapi.finance_api.user.entity.Profile;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.user.entity.UserCredentials;
import com.financeapi.finance_api.user.service.command.CreateUserCommand;
import com.financeapi.finance_api.user.service.dto.CreateUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
	//Entity
	@Mapping(target = "profile", ignore = true)
	@Mapping(target = "userCredentials", ignore = true)
	@Mapping(target = "id", ignore = true)
	User toUserEntity(CreateUserCommand command);

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "id", ignore = true)
	UserCredentials toUserCredentialsEntity(CreateUserCommand command);

	@Mapping(target = "user", ignore = true)
	@Mapping(target = "id", ignore = true)
	Profile toProfileEntity(CreateUserCommand command);

	//Details
	@Mapping(target = "token", source = "userCredentials.resetToken")
	@Mapping(target = "cif", source = "userCredentials.cif")
	@Mapping(target = "userId", source = "id")
	CreateUserDetails toDetails(User user);

}
