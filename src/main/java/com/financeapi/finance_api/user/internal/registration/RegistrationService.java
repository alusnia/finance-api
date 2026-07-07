package com.financeapi.finance_api.user.internal.registration;

import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.core.security.PasswordService;
import com.financeapi.finance_api.core.security.global.JwtMapper;
import com.financeapi.finance_api.core.security.global.JwtService;
import com.financeapi.finance_api.core.security.global.TokenRequests;
import com.financeapi.finance_api.core.system.Role;
import com.financeapi.finance_api.user.UserRegisteredEvent;
import com.financeapi.finance_api.user.internal.auth.*;
import com.financeapi.finance_api.user.internal.core.User;
import com.financeapi.finance_api.user.internal.core.UserRepository;
import com.financeapi.finance_api.user.internal.profile.Profile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import static com.financeapi.finance_api.core.exception.BankingError.NUMBER_GENERATION;
import static com.financeapi.finance_api.core.security.global.TokenExpiration.*;

@Service
@RequiredArgsConstructor
class RegistrationService {
	private final AuthService authService;
	private final JwtService jwtService;
	private final JwtMapper  jwtMapper;
	private final PasswordService passwordService;
	private final RegistrationMapper registrationMapper;
	private final SecureRandom random = new SecureRandom();
	private final PeselSearchesRepository peselSearchesRepository;
	private final ApplicationEventPublisher applicationEventPublisher;
	private final UserRepository userRepository;

	private String generateCif() {
		for(int i = 0; i < 1000; i++) {
			long num = 1000000000L + (Math.abs(random.nextLong()) % 9000000000L);
			String cif = String.valueOf(num);
			if (!userRepository.existsByAuth_Cif(cif)) {
				return cif;
			}
		}
		throw new BankingException(NUMBER_GENERATION);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	void savePeselSearch(SaveCheckPeselCommand saveCheckPeselCommand) {
		PeselSearch peselSearch = registrationMapper.toEntity(saveCheckPeselCommand);
		peselSearchesRepository.save(peselSearch);
	}

	boolean isPeselFree(CheckPeselQuery checkPeselQuery) {
		return  !userRepository.existsByAuth_Pesel(checkPeselQuery.pesel());
	}

	@Transactional
	public RegisterUserRespond registerUser(RegisterCommand command) {
		String cif = generateCif();
		String hashedPassword = passwordService.generateHashedPassword();
		Auth auth = registrationMapper.toAuth(command, cif, hashedPassword);
		authService.setResetToken(auth);
		Profile profile = registrationMapper.toProfile(command);
		User user = new User(auth, profile);
		userRepository.save(user);
		applicationEventPublisher.publishEvent(new UserRegisteredEvent(
				command.email(),
				command.firstName(),
				cif,
				auth.getResetToken()
		));
		TokenRequests.Standard tokenRequest = new TokenRequests.Standard(
				cif,
				user.getId(),
				Role.USER,
				command.email(),
				LOGIN.getMinutes()
		);
		String token = jwtService.generateToken(jwtMapper.toCommand(tokenRequest));
		return registrationMapper.toRespond(user.getId(), token);
	}

	boolean peselWasSearched(String cif, String pesel) {
	LocalDateTime timeWindow = LocalDateTime.now().minusMinutes(REGISTRATION.getMinutes());
		return peselSearchesRepository.existsByCifAndPeselAndSearchedAtAfter(cif, pesel, timeWindow);
	}
}
