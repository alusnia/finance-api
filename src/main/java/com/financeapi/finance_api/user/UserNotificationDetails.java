package com.financeapi.finance_api.user;

import com.financeapi.finance_api.user.internal.core.User;

public record UserNotificationDetails(
		String firstName,
		String lastName,
		String email,
		String telephoneNumber,
		String address
) {
	public static UserNotificationDetails from(User user) {
		return new UserNotificationDetails(
				user.getProfile().getFirstName(),
				user.getProfile().getFirstName(),
				user.getProfile().getEmail(),
				user.getProfile().getTelephoneNumber(),
				user.getProfile().getAddress()
		);
	}
}
