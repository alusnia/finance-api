package com.financeapi.finance_api.dto;

import com.financeapi.finance_api.entity.enums.Role;

@lombok.Setter
@lombok.Getter
public class UserResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
}
