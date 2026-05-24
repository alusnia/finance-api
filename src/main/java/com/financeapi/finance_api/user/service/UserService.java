package com.financeapi.finance_api.user.service;

import com.financeapi.finance_api.user.entity.Role;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private UserRepository userRepository;

	public User createUser() {
		User user = new User();
		user.setRole(Role.USER);
		return user;
	}
}
