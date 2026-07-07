package com.financeapi.finance_api.core.security;

import com.financeapi.finance_api.core.exception.BankingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static com.financeapi.finance_api.core.exception.BankingError.EMPTY_ARGUMENT;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.WARNING;

@Service
@RequiredArgsConstructor
public class PasswordService {
	private final PasswordEncoder passwordEncoder;
	private final SecureRandom random = new SecureRandom();

	public String generatePassword() {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 16; i++) {
			builder.append(random.nextLong());
		}
		return builder.toString();
	}

	public String hashPassword( String password) {
		if (password == null) {
			throw new BankingException(EMPTY_ARGUMENT).log("Password was empty", WARNING);
		}
		return passwordEncoder.encode(password);
	}

	public String generateHashedPassword() {
		return hashPassword(generatePassword());
	}

	public Boolean matches(String password, String hashedPassword) {
		if (password == null) {
			throw new BankingException(EMPTY_ARGUMENT).log("Password was empty", WARNING);
		}
		if (hashedPassword == null) {
			throw new BankingException(EMPTY_ARGUMENT).log("Hashed password was empty", WARNING);
		}
		return passwordEncoder.matches(hashedPassword, password);
	}
}
