package com.financeapi.finance_api.notifications.internal;

import com.financeapi.finance_api.core.exception.BankingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	private final EmailOutboxRepository emailOutboxRepository;
	private final NotificationsMapper  notificationsMapper;

	@Value("${spring.mail.username}")
	private String senderEmail;
	@Value("${notification.email.number-of-retries}")
	Long retries;


	public void sendEmail(EmailCommand emailCommand) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(senderEmail);
			message.setTo(emailCommand.receiver());
			message.setSubject(emailCommand.subject());
			message.setText(emailCommand.body());
			mailSender.send(message);
			log.info("Email successfully sent to: {}", emailCommand.receiver());
		} catch (Exception e) {
			String logMessage = "Error sending email to: " + emailCommand.receiver() + " " + emailCommand.subject() + " " + emailCommand.body();
			throw new BankingException(EMAIL_NOT_SENT).log(logMessage, WARNING);
		}
	}

	public void addToOutBox(Email email)
	{
		emailOutboxRepository.save(email);
	}

	@Transactional
	@Scheduled(fixedDelay = 10000)
	public void sendAllPendingEmails()
	{
		List<Email> emails = emailOutboxRepository.findPendingEmails(10);
		emails.forEach(email -> {
			try {
				sendEmail(notificationsMapper.toCommand(email));
				email.succeed();
			} catch (Exception BankingException) {
				email.failed(retries);
			}
		});
	}
}
