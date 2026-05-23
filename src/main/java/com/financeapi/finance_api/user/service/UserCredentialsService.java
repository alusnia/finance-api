package com.financeapi.finance_api.user.service;

import com.financeapi.finance_api.user.entity.UserCredentials;
import com.financeapi.finance_api.core.security.TokenExpiration;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.user.repository.UserCredentialsRepository;
import com.financeapi.finance_api.core.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;
import static java.util.concurrent.TimeUnit.*;

@Service
public class UserCredentialsService {
	private final JwtService jwtService;
	private final UserCredentialsRepository userCredentialsRepository;

	public UserCredentialsService(JwtService jwtService, UserCredentialsRepository userCredentialRepository) {
		this.jwtService = jwtService;
		this.userCredentialsRepository = userCredentialRepository;
	}

	private void generateToken(UserCredentials userCredentials) {
		long minutes = TokenExpiration.PASSWORD_RESET.getMinutes();
		userCredentials.setResetToken(jwtService.generateToken(userCredentials.getUser(), MINUTES.toMillis(minutes)));
		userCredentialsRepository.save(userCredentials);
	}

//	@Transactional
//	public String forgotPassword(String email) {
//		if (email == null) {
//			throw new BankingException(EMPTY_ARGUMENT).log("Email was empty", WARNING);
//		}
//
//	}

//	@Transactional
//	public void resetPassword(String token, String newPassword) {
//		String userId;
//		try {
//			userId = jwtService.extractUserId(token);
//		} catch (ExpiredJwtException e) {
//			throw new BankingException(JWT_TOKEN_EXPIRED);
//		} catch (JwtException e) {
//			throw new BankingException(JWT_TOKEN_NOT_VALID);
//		}
//		UserCredentials userCredentials = userCredentialsRepository.findById(Long.parseLong(userId))
//				.orElseThrow(() -> new BankingException(USER_NOT_FOUND));
//		userCredentials.checkToken(token);
//		userCredentials.eraseToken();
//		userCredentials.setPasswordHash(passwordService.encode(newPassword));
//		userCredentialsRepository.save(userCredentials);
//	}
}
