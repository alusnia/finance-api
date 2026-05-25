package com.financeapi.finance_api.user.service;

import com.financeapi.finance_api.core.security.PasswordService;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.user.entity.UserCredentials;
import com.financeapi.finance_api.core.security.global.TokenExpiration;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.user.repository.UserCredentialsRepository;
import com.financeapi.finance_api.core.security.global.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Optional;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;
import static com.financeapi.finance_api.core.security.global.TokenExpiration.*;
import static java.util.concurrent.TimeUnit.*;

@Service
@RequiredArgsConstructor
public class UserCredentialsService {
	private final JwtService jwtService;
	private final UserCredentialsRepository userCredentialsRepository;
	private final PasswordService  passwordService;
	private final SecureRandom random = new SecureRandom();


	public String generateToken(User user) {
		return generateToken(user, PASSWORD_RESET);
	}

	public String generateToken(User user, TokenExpiration tokenExpiration) {
		long minutes = tokenExpiration.getMinutes();
		return jwtService.generateToken(user, MINUTES.toMillis(minutes));
	}

	@Transactional
	public String forgotPassword(String pesel) {
		if (pesel == null) {
			throw new BankingException(EMPTY_ARGUMENT).log("Pesel was empty", WARNING);
		}
		Optional<UserCredentials> potentialUserCredentials = userCredentialsRepository.findByPesel(pesel);
		if (potentialUserCredentials.isEmpty()) {
			return null;
		}
		UserCredentials userCredentials = potentialUserCredentials.get();
		userCredentials.setResetToken(generateToken(userCredentials.getUser()));
		return userCredentials.getResetToken();
	}

	@Transactional
	public void resetPassword(String token, String newPassword) {
		String userId;
		try {
			userId = jwtService.extractUserId(token);
		} catch (ExpiredJwtException e) {
			throw new BankingException(JWT_TOKEN_EXPIRED);
		} catch (JwtException e) {
			throw new BankingException(JWT_TOKEN_NOT_VALID);
		}
		UserCredentials userCredentials = userCredentialsRepository.findById(Long.parseLong(userId))
				.orElseThrow(() -> new BankingException(USER_NOT_FOUND));
		userCredentials.checkToken(token);
		userCredentials.eraseToken();
		userCredentials.setPasswordHash(passwordService.hashPassword(newPassword));
		userCredentialsRepository.save(userCredentials);
	}

	public String generateCif() {
		for(int i = 0; i < 1000; i++) {
			long num = 1000000000L + (Math.abs(random.nextLong()) % 9000000000L);
			String cif = String.valueOf(num);
			if (!userCredentialsRepository.existsByCif(cif)) {
				return cif;
			}
		}
		throw new BankingException(NUMBER_GENERATION);
	}
}

