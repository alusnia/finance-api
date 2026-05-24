package com.financeapi.finance_api.user.controller;

import com.financeapi.finance_api.user.controller.dto.ResetPasswordRequest;
import com.financeapi.finance_api.user.service.UserCredentialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credentials")
public class UserCredentialsController {
	private final UserCredentialsService userCredentialsService;

	public UserCredentialsController(UserCredentialsService userCredentialsService) {
		this.userCredentialsService = userCredentialsService;
	}

	@PostMapping("/password/reset/{token}")
	public ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ResetPasswordRequest request) {
		String newPassword = request.getNewPassword();
		userCredentialsService.resetPassword(token, newPassword);
		return ResponseEntity.ok("Password saved successfully.");
	}

	@PostMapping("/password/forgot")
	public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordRequest request) {
		String key = request.getKey();
		String type = userCredentialsService.forgotPassword(key);
		return ResponseEntity.ok("Message sent successfully! Please check your email.");
	}
}
