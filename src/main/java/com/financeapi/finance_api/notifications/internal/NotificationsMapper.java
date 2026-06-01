package com.financeapi.finance_api.notifications.internal;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationsMapper {
	public EmailCommand toCommand(Email email);
	public EmailCommand toCommand(Email email, User user);
}
