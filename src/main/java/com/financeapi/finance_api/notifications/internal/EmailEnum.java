package com.financeapi.finance_api.notifications.internal;

import lombok.Getter;

@Getter
public enum EmailEnum {
	REGISTRATION("Welcome on board ${firstName}!",
			"Hi ${firstName},\n\nWe are thrilled to have you with us! Your Finance API account has been successfully initialized.\n\nYour unique Login ID (CIF) is: ${cif}\n\nPlease keep this number safe and confidential – it is your primary key for secure login.\n\nTo complete your registration and activate your account, please click the link below to set up your password:\n${loginLink}\n\nIf you have any questions or need assistance, our support team is always here to help.\n\nBest regards,\nThe Finance API Team"),
	NEW_ACCOUNT("New account in ${currency} created!",
			"Hi ${firstName},\n\nGreat news! Your new ${currency} account has been successfully created and is now active.\n\nYour new account number is:\n${accountNumber}\n\nYou can now log in to your Finance API dashboard to view your account details, manage your funds, and start transacting in ${currency}.\n\nIf you did not request this account or need any assistance, please contact our support team immediately.\n\nBest regards,\nThe Finance API Team"),
	;
	private final String subject;
	private final String body;

	EmailEnum(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}
}
