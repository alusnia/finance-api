package com.financeapi.finance_api.user.internal.auth;

import com.financeapi.finance_api.core.security.PasswordService;
import com.financeapi.finance_api.core.security.SecurityLock;
import com.financeapi.finance_api.core.security.global.*;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.user.internal.core.User;
import com.financeapi.finance_api.user.internal.core.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.WARNING;
import static com.financeapi.finance_api.core.security.global.TokenExpiration.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
	private final JwtService jwtService;
	private final PasswordService  passwordService;
	private final JwtMapper jwtMapper;
	private final AuthMapper authMapper;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final UserRepository userRepository;

	private String generateToken(String cif) {
		long minutes = PASSWORD_RESET.getMinutes();
		return jwtService.generateToken(jwtMapper.toCommand(new TokenRequests.ChangePassword(cif, minutes)));
	}

	@Transactional
	void changePassword(ChangePasswordCommand command) {
		Long userId;
		try {
			userId = jwtService.extractInfo(command.token()).id();
		} catch (ExpiredJwtException e) {
			throw new BankingException(JWT_TOKEN_EXPIRED);
		} catch (JwtException e) {
			throw new BankingException(JWT_TOKEN_NOT_VALID);
		}
		Optional<User> potentialUser = userRepository.findById(userId);
		if (potentialUser.isEmpty()) {
			throw new BankingException(USER_NOT_FOUND);
		}
		User user = potentialUser.get();
		Auth auth = user.getAuth();
		auth.checkToken(command.token());
		auth.eraseToken();
		auth.setHashedPassword(passwordService.hashPassword(command.newPassword()));
	}

	public void setResetToken(Auth auth) {
		auth.setResetToken(generateToken(auth.getCif()));
	}

	@Transactional
	void forgotPassword(ForgotPasswordCommand command) {
		Optional<User> potentialUser = userRepository.findByAuth_Cif(command.cif());
		if (potentialUser.isEmpty()) {
			log.warn("Cannot find user with cif {}", command.cif());
			return ;
		}
		User user = potentialUser.get();
		setResetToken(user.getAuth());
		applicationEventPublisher.publishEvent(authMapper.toEvent(user.getAuth()));
	}

	String authenticate(LoginCommand command) {
		Optional<User> potentialUser = userRepository.findByAuth_Cif(command.cif());
		if (potentialUser.isEmpty()) {
			throw new BankingException(AUTH_ACCESS_DENIED_LOGIN, WARNING);
		}
		User user = potentialUser.get();
		Auth auth = user.getAuth();
		SecurityLock lock = auth.getSecurityLock();
		if (lock.isLocked()) {
			Long remainingMinutes = lock.getRemainingMinutes();
			BankingException ex = new BankingException(AUTH_ACCESS_DENIED_LOCK, WARNING);
			if (remainingMinutes != null) {
				ex.extendMessage("Remaining time is " + remainingMinutes + " minutes.");
			}
			throw ex;
		}
		if (!passwordService.matches(command.password(), auth.getHashedPassword())) {
			lock.wrongInput();
			userRepository.save(user);
			throw new BankingException(AUTH_ACCESS_DENIED_PASSWORD, WARNING);
		}
		lock.reset();
		TokenCommand tokenCommand = authMapper.toCommand(auth, LOGIN.getMinutes());
		userRepository.save(user);
		return jwtService.generateToken(tokenCommand);
	}
}

