package com.financeapi.finance_api.user.service;

import com.financeapi.finance_api.user.service.query.CheckPeselQuery;
import com.financeapi.finance_api.user.entity.Profile;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.user.entity.UserCredentials;
import com.financeapi.finance_api.user.mapper.UserMapper;
import com.financeapi.finance_api.user.repository.UserCredentialsRepository;
import com.financeapi.finance_api.user.repository.UserRepository;
import com.financeapi.finance_api.user.service.command.CreateUserCommand;
import com.financeapi.finance_api.user.service.dto.CreateUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final UserMapper mapper;
	private final UserCredentialsService userCredentialsService;
	private final UserCredentialsRepository userCredentialsRepository;

	public boolean isPeselFree(CheckPeselQuery checkPeselQuery) {
		return  !userCredentialsRepository.existsByPesel(checkPeselQuery.pesel());
	}

	@Transactional
	public CreateUserDetails createUser(CreateUserCommand command) {
		User user = mapper.toUserEntity(command);
		UserCredentials userCredentials = mapper.toUserCredentialsEntity(command);
		userCredentials.setResetToken(userCredentialsService.generateToken(user, command.tokenPurpose()));
		userCredentials.setCif(userCredentialsService.generateCif());
		user.setUserCredentials(userCredentials);
		userCredentials.setUser(user);
		Profile profile = mapper.toProfileEntity(command);
		user.setProfile(profile);
		profile.setUser(user);
		userRepository.save(user);
		return mapper.toDetails(user);
	}
}
