package com.financeapi.finance_api.registration.service;

import com.financeapi.finance_api.core.notifications.EmailBuilder;
import com.financeapi.finance_api.core.notifications.EmailEnum;
import com.financeapi.finance_api.core.notifications.EmailService;
import com.financeapi.finance_api.registration.entity.PeselSearch;
import com.financeapi.finance_api.registration.mapper.RegistrationMapper;
import com.financeapi.finance_api.registration.repository.PeselSearchesRepository;
import com.financeapi.finance_api.registration.service.command.RegisterCommand;
import com.financeapi.finance_api.registration.service.command.SaveCheckPeselCommand;
import com.financeapi.finance_api.user.service.UserService;
import com.financeapi.finance_api.user.service.dto.CreateUserDetails;
import com.financeapi.finance_api.user.service.query.CheckPeselQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.financeapi.finance_api.core.notifications.EmailBuilder.variable.BODY;
import static com.financeapi.finance_api.core.notifications.EmailBuilder.variable.SUBJECT;
import static com.financeapi.finance_api.core.security.global.TokenExpiration.*;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	private final PeselSearchesRepository peselSearchesRepository;
	private final UserService userService;
	private final EmailService emailService;
	private final RegistrationMapper mapper;

	public void savePeselSearch(SaveCheckPeselCommand saveCheckPeselCommand) {
		PeselSearch peselSearch = mapper.toEntity(saveCheckPeselCommand);
		peselSearchesRepository.save(peselSearch);
	}

	public boolean isPeselFree(CheckPeselQuery checkPeselQuery) {
		return  userService.isPeselFree(checkPeselQuery);
	}

	@Transactional
	public Long registerUser(RegisterCommand command) {
		CreateUserDetails details = userService.createUser(mapper.toCommand(command, REGISTRATION));
		EmailBuilder emailBuilder = new EmailBuilder(EmailEnum.REGISTRATION)
				.replaceNames(SUBJECT, "firstname", command.firstName())
				.replaceNames(BODY, "firstname", command.firstName())
				.replaceNames(BODY, "cif", details.cif())
				.replaceNames(BODY, "login_link", "http://localhost:8080/api/password/reset/" + details.token());
		emailService.sendRegistrationEmail(command.email(), emailBuilder);
		return details.userId();
	}
}
