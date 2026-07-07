package com.financeapi.finance_api.user.internal.auth;

import com.financeapi.finance_api.core.system.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.financeapi.finance_api.core.system.SuccessDetails.LOGIN_SUCCESS;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
class AuthController {
	private final AuthMapper mapper;
	private final AuthService authService;

	@PostMapping("/login")
	ResponseEntity <SuccessResponse<String>> login(@RequestBody LoginRequest loginRequest) {
		String token = authService.authenticate(mapper.toCommand(loginRequest));
		return ResponseEntity.ok(new SuccessResponse<>(token, LOGIN_SUCCESS));
	}

	@PostMapping("/password/forgot")
	ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordRequest request) {
		authService.forgotPassword(mapper.toCommand(request));
		return ResponseEntity.ok("Message sent successfully! Please check your email.");
	}

	@PostMapping("/password/reset/{token}")
	ResponseEntity<String> resetPassword(@PathVariable String token, @RequestBody ChangePasswordRequest request) {
		authService.changePassword(mapper.toCommand(request, token));
		return ResponseEntity.ok("Password saved successfully.");
	}
}
