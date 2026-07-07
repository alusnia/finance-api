package com.financeapi.finance_api.notifications.internal;

import com.financeapi.finance_api.account.NewAccountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.financeapi.finance_api.notifications.internal.Email.Variable.BODY;
import static com.financeapi.finance_api.notifications.internal.Email.Variable.SUBJECT;

@Component
@RequiredArgsConstructor
public class AccountNotificationListener {
	private final EmailService emailService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void onAccountCreated(NewAccountEvent event) {
		Email email = new Email(event.email(), EmailEnum.NEW_ACCOUNT)
				.replaceNames(SUBJECT, "currency", event.currency())
				.replaceNames(BODY, "firstname", event.firstName())
				.replaceNames(BODY, "currency", event.currency())
				.replaceNames(BODY, "accountNumber", event.accountNumber());
		emailService.addToOutBox(email);
	}
}
