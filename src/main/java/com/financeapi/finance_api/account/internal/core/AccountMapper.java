package com.financeapi.finance_api.account.internal.core;

import com.financeapi.finance_api.account.NewAccountEvent;
import com.financeapi.finance_api.core.security.global.JwtPrincipal;
import com.financeapi.finance_api.user.UserNotificationDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface AccountMapper {
	@Mapping(target = "userId", source = "principal.id")
	NewAccountCommand toCommand(NewAccountRequest request, JwtPrincipal principal);
	Account toEntity(NewAccountCommand command, String accountNumber);
	NewAccountEvent toEvent(Account account, UserNotificationDetails details);
}
