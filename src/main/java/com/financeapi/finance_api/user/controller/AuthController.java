package com.financeapi.finance_api.user.controller;

import com.financeapi.finance_api.user.controller.dto.LoginRequest;
import com.financeapi.finance_api.user.controller.dto.LoginResponse;
import com.financeapi.finance_api.user.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity <LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		String login = loginRequest.getLogin();
		String password = loginRequest.getPassword();
		return ResponseEntity.ok(authService.authenticate(login, password));
	}
}
