package com.financeapi.finance_api.core.notifications;

import lombok.Getter;

@Getter
public class EmailBuilder {
	public enum variable {
		SUBJECT, BODY
	}

	private String subject;
	private String body;

	//[Custom]
	public EmailBuilder(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}

	//[Generic]
	public EmailBuilder(EmailEnum email) {
		this.subject = email.getSubject();
		this.body = email.getBody();
	}

	public EmailBuilder replaceNames(variable type, String key, String value) {
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
}
