package com.financeapi.finance_api.user.controller;

import com.financeapi.finance_api.user.controller.dto.UserRegistration;
import com.financeapi.finance_api.user.controller.dto.UserResponse;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.user.entity.Role;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'Admin')")
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() {

		List<User> users = userRepository.findAll();
		return ResponseEntity.ok(users.stream()
				 .map(user -> {
					 UserResponse userResponse = new UserResponse();
					 userResponse.setId(user.getId());
					 userResponse.setFirstName(user.getFirstName());
					 userResponse.setLastName(user.getLastName());
					 userResponse.setEmail(user.getEmail());
					 userResponse.setRole(user.getRole());
					 return userResponse;
				 }
				 .toList()));
	}

	@GetMapping("/me")
	public ResponseEntity<String> getMyProfile() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok("Logged in user: " + principal.toString());
	}

	@PreAuthorize("hasRole('HEAD_ADMIN')")
	@PatchMapping("/{userId}/role")
	public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String role) {
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

	@PostMapping("/register")
	public ResponseEntity<String> createUser(@RequestBody UserRegistration userRegistration) {
		User user = new User(

		);
		userRepository.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/users.{id}")
				.buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin') or authentication.principal == #id")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		Optional<User> possibleUser = userRepository.findById(id);
		if (possibleUser.isEmpty()) {
			throw new BankingException(USER_NOT_FOUND, WARNING);
		} else {
			userRepository.delete(possibleUser.get());
			return ResponseEntity.noContent().build();
		}
	}
}
