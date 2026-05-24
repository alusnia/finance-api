package com.financeapi.finance_api.core.notifications;

import lombok.Getter;

@Getter
public enum EmailEnum {
	REGISTRATION("Welcome on board ${name}!",
			"Hi ${name},\n\nWe are thrilled to have you with us! Your Finance API account has been successfully initialized.\n\nYour unique Login ID (CIF) is: ${cif}\n\nPlease keep this number safe and confidential – it is your primary key for secure login.\n\nTo complete your registration and activate your account, please click the link below to set up your password:\n${login_link}\n\nIf you have any questions or need assistance, our support team is always here to help.\n\nBest regards,\nThe Finance API Team"),
	;
	private final String subject;
	private final String body;

	EmailEnum(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}
}
