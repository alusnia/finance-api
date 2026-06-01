package com.financeapi.finance_api.user;

import com.financeapi.finance_api.core.exception.BankingError;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.user.internal.core.User;
import com.financeapi.finance_api.user.internal.core.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFacade {
	private UserRepository userRepository;

	public UserNotificationDetails exportUserNotificationDetails(Long userId)
	{
		Optional<User> potentialUser = userRepository.findById(userId);
		if (potentialUser.isEmpty()) {
			throw new BankingException(BankingError.USER_NOT_FOUND);
		}
		User user = potentialUser.get();
		return UserNotificationDetails.from(user);
	}
}
