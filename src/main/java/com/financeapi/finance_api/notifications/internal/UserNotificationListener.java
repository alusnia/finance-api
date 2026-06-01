package com.financeapi.finance_api.notifications.internal;

import com.financeapi.finance_api.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.financeapi.finance_api.notifications.internal.Email.Variable.*;

@Component
@RequiredArgsConstructor
public class UserNotificationListener {
	private final EmailService emailService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onUserRegistered(UserRegisteredEvent event) {
		Email email = new Email(event.email(), EmailEnum.REGISTRATION)
				.replaceNames(SUBJECT, "firstName", event.firstName())
				.replaceNames(BODY, "firstName", event.firstName())
				.replaceNames(BODY, "cif", event.cif())
				.replaceNames(BODY, "loginLink", "http://localhost:8080/api/password/reset/" + event.token());
		emailService.addToOutBox(email);
	}
}
