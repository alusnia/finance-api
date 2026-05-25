package com.financeapi.finance_api.registration.mapper;

import com.financeapi.finance_api.core.security.global.TokenExpiration;
import com.financeapi.finance_api.registration.controller.dto.CheckPeselRequest;
import com.financeapi.finance_api.registration.controller.dto.RegisterRequest;
import com.financeapi.finance_api.registration.entity.PeselSearch;
import com.financeapi.finance_api.registration.service.command.RegisterCommand;
import com.financeapi.finance_api.user.service.query.CheckPeselQuery;
import com.financeapi.finance_api.registration.service.command.SaveCheckPeselCommand;
import com.financeapi.finance_api.user.service.command.CreateUserCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

	//verify
	SaveCheckPeselCommand toCommand(CheckPeselRequest request, String callerId);

	CheckPeselQuery toQuery(CheckPeselRequest request);

	//register
	RegisterCommand toCommand(RegisterRequest request, String pesel);

	CreateUserCommand toCommand(RegisterCommand command, TokenExpiration tokenPurpose);

	PeselSearch toEntity(SaveCheckPeselCommand command);
}
