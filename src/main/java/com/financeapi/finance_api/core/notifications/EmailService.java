package com.financeapi.finance_api.core.notifications;

import com.financeapi.finance_api.core.exception.BankingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String senderEmail;

	public void sendRegistrationEmail(String to, EmailBuilder emailBuilder) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(senderEmail);
			message.setTo(to);
			message.setSubject(emailBuilder.getSubject());
			message.setText(emailBuilder.getBody());
			mailSender.send(message);
			log.info("Email successfully sent to: {}", to);
		} catch (Exception e) {
			String logMessage = "Error sending email to: " + to + " " + emailBuilder.getSubject() + " " + emailBuilder.getBody();
			throw new BankingException(REGISTRATION_MAIL_NOT_SEND).log(logMessage, WARNING);
		}
	}
}
