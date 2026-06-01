package com.financeapi.finance_api.user;

public record UserRegisteredEvent(
		String email,
		String firstName,
		String cif,
		String token
) {}
