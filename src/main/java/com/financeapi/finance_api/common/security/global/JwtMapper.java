package com.financeapi.finance_api.core.security.global;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {
	public TokenCommand toCommand(TokenRequests.Standard request);

	public TokenCommand toCommand(TokenRequests.ChangePassword request);

	JwtPrincipal toPrincipal(TokenResponse response);
}
