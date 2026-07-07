package com.financeapi.finance_api.notifications.internal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Table(name = "email_outbox")
@Getter
@Entity
public class Email {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String receiver;
	@Column
	private String subject;
	@Column
	private String body;
	@Column
	@Enumerated(EnumType.STRING)
	private EmailStatus status = EmailStatus.PENDING;
	@Column
	private long retries = 0;
	@Column(name = "retry_after")
	private LocalDateTime retryAfter = LocalDateTime.now();
	@Column(name = "created_at")
	private final LocalDateTime createdAt = LocalDateTime.now();


	public enum EmailStatus {
		PENDING, SENT, FAILED
	}

	public enum Variable {
		SUBJECT, BODY
	}

	public Email() {}

	//[New Custom]
	public Email(String receiver, String subject, String body) {
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
	}

	//[Custom]
	public Email(String receiver, String subject, String body, EmailStatus status) {
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
		this.status = status;
	}

	//[New Generic]
	public Email(String receiver, EmailEnum email) {
		this.receiver = receiver;
		this.subject = email.getSubject();
		this.body = email.getBody();
	}

	public Email replaceNames(Variable type, String key, String value) {
		key = "${" + key + "}";
		switch (type) {
			case SUBJECT:
				this.subject = subject.replace(key, value);
				break;
			case BODY:
				this.body = body.replace(key, value);
				break;
		}
		return this;
	}

	private long calculateSleepTime() {
		long multiplier = 1L << retries;
		return  retries * multiplier;
	}

	public void failed(long numberOfRetries) {
		retries++;
		if (retries == numberOfRetries) {
			this.status = EmailStatus.FAILED;
			log.warn("Attempt to send email was stopped.");
		} else  {
			long minutes = calculateSleepTime();
			this.retryAfter = LocalDateTime.now().plusMinutes(minutes);
			log.warn("Attempt number {} will occur after {} minutes.", this.retries, minutes);
		}
	}

	public void succeed() {
		this.status = EmailStatus.SENT;
	}
}
