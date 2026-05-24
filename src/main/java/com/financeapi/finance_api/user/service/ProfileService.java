package com.financeapi.finance_api.user.service;

import com.financeapi.finance_api.core.notifications.EmailBuilder;
import com.financeapi.finance_api.core.notifications.EmailService;
import com.financeapi.finance_api.registration.service.command.RegisterProfileCommand;
import com.financeapi.finance_api.user.entity.Profile;
import com.financeapi.finance_api.user.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.financeapi.finance_api.core.notifications.EmailBuilder.variable.*;
import static com.financeapi.finance_api.core.notifications.EmailEnum.*;

@Service
@RequiredArgsConstructor
public class ProfileService {
	private ProfileRepository profileRepository;
	private EmailService emailService;

	public Profile registerProfile(RegisterProfileCommand command) {
		Profile profile = new Profile();
		profile.setFirstName(command.name());
		profile.setLastName(command.surname());
		profile.setEmail(command.email());
		EmailBuilder emailBuilder = new EmailBuilder(REGISTRATION)
				.replaceNames(SUBJECT, "name", command.name())
				.replaceNames(BODY, "name", command.name())
				.replaceNames(BODY, "cif", command.cif())
				.replaceNames(BODY, "login_link", "http://localhost:8080/api/password/reset/" + command.token());
		emailService.sendRegistrationEmail(command.email(),  emailBuilder);
		profileRepository.save(profile);
		return profile;
	}
}
