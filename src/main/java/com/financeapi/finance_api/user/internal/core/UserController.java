package com.financeapi.finance_api.user.internal.core;

import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.core.system.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController {
	private final UserRepository userRepository;
	private final UserService userService;
	private final UserMapper mapper;

	@PreAuthorize("hasRole('HEAD_ADMIN')")
	@PatchMapping("/{userId}/role")
	ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String role) {
		Role userRole;

		try {
			userRole = Role.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			String validRoles = "Valid roles are: " + Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.joining(", "));
			throw new BankingException(USER_INVALID_ROLE, WARNING).log(validRoles, WARNING);
		}
		Optional<User> possibleUser = userRepository.findById(userId);
		if (possibleUser.isEmpty()) {
			throw new BankingException(USER_NOT_FOUND, WARNING);
		}
		User user = possibleUser.get();
		if (user.getRole().equals(userRole)) {
			throw new BankingException(USER_INVALID_ROLE, WARNING).log("User already has that role", WARNING);
		}
		user.setRole(userRole);
		userRepository.save(user);
		return ResponseEntity.ok("User with id: " + userId + " role updated to: " + role.toUpperCase() + " successfully.");
	}

	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin') or authentication.principal == #id")
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		Optional<User> possibleUser = userRepository.findById(id);
		if (possibleUser.isEmpty()) {
			throw new BankingException(USER_NOT_FOUND, WARNING);
		} else {
			userRepository.delete(possibleUser.get());
			return ResponseEntity.noContent().build();
		}
	}
}
