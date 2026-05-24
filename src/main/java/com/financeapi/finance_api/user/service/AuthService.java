package com.financeapi.finance_api.user.service;

import com.financeapi.finance_api.user.controller.dto.LoginResponse;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.core.security.global.TokenExpiration;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.core.exception.BankingException.LogType;
import com.financeapi.finance_api.user.repository.UserRepository;
import com.financeapi.finance_api.core.security.global.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.financeapi.finance_api.core.exception.BankingError.*;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
	}

	public LoginResponse authenticate(String login, String password) {
		User user = userRepository.findByPesel(login)
				.orElseThrow(() -> new BankingException(AUTH_ACCESS_DENIED_LOGIN, LogType.WARNING));
		if (!passwordEncoder.matches(password, user.getUserCredentials().getPasswordHash())) {
			throw new BankingException(AUTH_ACCESS_DENIED_PASSWORD, LogType.WARNING);
		}
		long minutes = TokenExpiration.LOGIN.getMinutes();
		return new LoginResponse(jwtService.generateToken(user, TimeUnit.MINUTES.toMillis(minutes)));
	}
}
