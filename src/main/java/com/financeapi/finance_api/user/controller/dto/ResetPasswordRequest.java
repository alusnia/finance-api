package com.financeapi.finance_api.user.controller.dto;


import lombok.Getter;

@Getter
public class ResetPasswordRequest {
	private String token;
	private String newPassword;
}
