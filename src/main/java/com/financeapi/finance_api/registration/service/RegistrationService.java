package com.financeapi.finance_api.registration.service;

import com.financeapi.finance_api.registration.entity.PeselSearch;
import com.financeapi.finance_api.registration.repository.PeselSearchesRepository;
import com.financeapi.finance_api.registration.service.query.CheckPeselQuery;
import com.financeapi.finance_api.registration.service.command.SaveCheckPeselCommand;
import com.financeapi.finance_api.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
	private final PeselSearchesRepository peselSearchesRepository;
	private final UserRepository userRepository;

	public RegistrationService(PeselSearchesRepository peselSearchesRepository, UserRepository userRepository) {
		this.peselSearchesRepository = peselSearchesRepository;
		this.userRepository = userRepository;
	}

	public boolean isPeselFree(CheckPeselQuery checkPeselQuery) {
		return  userRepository.existsByPesel(checkPeselQuery.pesel());
	}

	public void savePeselSearch(SaveCheckPeselCommand saveCheckPeselCommand) {
		PeselSearch peselSearch = new PeselSearch(saveCheckPeselCommand);
		peselSearchesRepository.save(peselSearch);
	}
}
