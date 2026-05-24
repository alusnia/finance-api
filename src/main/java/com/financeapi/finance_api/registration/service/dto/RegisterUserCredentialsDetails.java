package com.financeapi.finance_api.registration.service.dto;

import com.financeapi.finance_api.user.entity.UserCredentials;

public record RegisterUserCredentialsDetails(UserCredentials userCredentials, String token) { }
