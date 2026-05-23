package com.financeapi.finance_api.registration.mapper;

import com.financeapi.finance_api.registration.controller.dto.CheckPeselRequest;
import com.financeapi.finance_api.registration.service.query.CheckPeselQuery;
import com.financeapi.finance_api.registration.service.command.SaveCheckPeselCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

	SaveCheckPeselCommand toCommand(CheckPeselRequest request, String callerId);
	CheckPeselQuery toQuery(CheckPeselRequest request);
}
