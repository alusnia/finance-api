package com.financeapi.finance_api.registration.controller;

import com.financeapi.finance_api.core.security.global.JwtService;
import com.financeapi.finance_api.core.system.SuccessResponse;
import com.financeapi.finance_api.registration.controller.dto.CheckPeselRequest;
import com.financeapi.finance_api.registration.controller.dto.RegisterRequest;
import com.financeapi.finance_api.registration.mapper.RegistrationMapper;
import com.financeapi.finance_api.registration.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.financeapi.finance_api.core.security.global.TokenExpiration.*;
import static com.financeapi.finance_api.core.system.SuccessDetails.*;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
	private final RegistrationService registrationService;
	private final JwtService jwtService;
	private final RegistrationMapper registrationMapper;

	public RegistrationController (RegistrationService registrationService,  JwtService jwtService,  RegistrationMapper registrationMapper) {
		this.registrationService = registrationService;
		this.jwtService = jwtService;
		this.registrationMapper = registrationMapper;
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'ADMIN', 'EMPLOYEE')")
	@PostMapping("/verify")
	public ResponseEntity<SuccessResponse<String>> isPeselFree(
			@RequestBody CheckPeselRequest request,
			@RequestHeader("Authorization") String authHeader) {
		final String jwtToken = authHeader.substring("Bearer ".length());
		String callerId = jwtService.extractUserId(jwtToken);
		registrationService.savePeselSearch(registrationMapper.toCommand(request, callerId));
		if (registrationService.isPeselFree(registrationMapper.toQuery(request))) {
			return ResponseEntity.ok(new SuccessResponse<>(
					jwtService.generateToken(callerId, request.pesel(), REGISTRATION.getMinutes()),
					PESEL_FREE));
		} else {
			return ResponseEntity.ok(new SuccessResponse<>(PESEL_NOT_FREE));
		}
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'ADMIN', 'EMPLOYEE')")
	@PostMapping
	public ResponseEntity<SuccessResponse<Void>> registerUser(@RequestBody RegisterRequest request)
	{
		String pesel = jwtService.extractPesel(request.token());
		Long id = registrationService.registerUser(registrationMapper.toCommand(request, pesel));
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/users.{id}")
				.buildAndExpand(id)
				.toUri();
		return ResponseEntity.created(location).body(new SuccessResponse<>(USER_REGISTERED));
	}
}
