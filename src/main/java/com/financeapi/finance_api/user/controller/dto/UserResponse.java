package com.financeapi.finance_api.user.controller.dto;

import com.financeapi.finance_api.user.entity.Role;

@lombok.Setter
@lombok.Getter
public class UserResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
}
