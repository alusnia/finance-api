package com.financeapi.finance_api.registration.service;

import com.financeapi.finance_api.registration.entity.PeselSearch;
import com.financeapi.finance_api.registration.mapper.RegistrationMapper;
import com.financeapi.finance_api.registration.repository.PeselSearchesRepository;
import com.financeapi.finance_api.registration.service.command.RegisterUserCredentialsCommand;
import com.financeapi.finance_api.registration.service.command.RegisterCommand;
import com.financeapi.finance_api.registration.service.query.CheckPeselQuery;
import com.financeapi.finance_api.registration.service.command.SaveCheckPeselCommand;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.user.repository.UserCredentialsRepository;
import com.financeapi.finance_api.user.repository.UserRepository;
import com.financeapi.finance_api.user.service.ProfileService;
import com.financeapi.finance_api.user.service.UserCredentialsService;
import com.financeapi.finance_api.user.service.UserService;
import com.financeapi.finance_api.registration.service.command.RegisterProfileCommand;
import com.financeapi.finance_api.registration.service.dto.RegisterUserCredentialsDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	private final PeselSearchesRepository peselSearchesRepository;
	private final UserRepository userRepository;
	private final UserCredentialsRepository userCredentialsRepository;
	private final UserService userService;
	private final UserCredentialsService userCredentialsService;
	private final ProfileService profileService;
	private final RegistrationMapper registrationMapper;

	public boolean isPeselFree(CheckPeselQuery checkPeselQuery) {
		return  userCredentialsRepository.existsByPesel(checkPeselQuery.pesel());
	}

	public void savePeselSearch(SaveCheckPeselCommand saveCheckPeselCommand) {
		PeselSearch peselSearch = new PeselSearch(saveCheckPeselCommand);
		peselSearchesRepository.save(peselSearch);
	}

	@Transactional
	public Long registerUser(RegisterCommand command) {
		User user = userService.createUser();
		RegisterUserCredentialsCommand registerUserCredentialsCommand = registrationMapper.toCommand(command);
		RegisterUserCredentialsDetails details = userCredentialsService.registerUserCredentialsDetails(registerUserCredentialsCommand);
		RegisterProfileCommand profileCommand = registrationMapper.toCommand(command, details.userCredentials().getCif(), details);
		user.setUserCredentials(details.userCredentials());
		user.setProfile(profileService.registerProfile(profileCommand));
		userRepository.save(user);
		return user.getId();
	}

}
