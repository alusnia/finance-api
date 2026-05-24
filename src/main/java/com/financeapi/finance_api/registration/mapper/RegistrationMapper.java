package com.financeapi.finance_api.registration.mapper;

import com.financeapi.finance_api.registration.controller.dto.CheckPeselRequest;
import com.financeapi.finance_api.registration.controller.dto.RegisterRequest;
import com.financeapi.finance_api.registration.service.command.RegisterProfileCommand;
import com.financeapi.finance_api.registration.service.command.RegisterUserCredentialsCommand;
import com.financeapi.finance_api.registration.service.command.RegisterCommand;
import com.financeapi.finance_api.registration.service.dto.RegisterUserCredentialsDetails;
import com.financeapi.finance_api.registration.service.query.CheckPeselQuery;
import com.financeapi.finance_api.registration.service.command.SaveCheckPeselCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

	//verify
	SaveCheckPeselCommand toCommand(CheckPeselRequest request, String callerId);

	CheckPeselQuery toQuery(CheckPeselRequest request);

	//register
	@Mapping(target = "pesel", source = "pesel")
	RegisterCommand toCommand(RegisterRequest request, String pesel);
	RegisterUserCredentialsCommand toCommand(RegisterCommand command);
	@Mapping(target = "email", source = "email")
	@Mapping(target = "token", source = "token")
	RegisterProfileCommand toCommand(RegisterCommand command, String cif, RegisterUserCredentialsDetails details);
}
