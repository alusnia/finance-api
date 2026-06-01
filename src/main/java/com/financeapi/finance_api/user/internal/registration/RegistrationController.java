package com.financeapi.finance_api.user.internal.registration;

import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.core.security.global.JwtPrincipal;
import com.financeapi.finance_api.core.system.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.system.SuccessDetails.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {
	private final RegistrationService registrationService;
	private final RegistrationMapper registrationMapper;

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'ADMIN', 'EMPLOYEE')")
	@PostMapping("/verify")
	ResponseEntity<SuccessResponse<Void>> isPeselFree(
			@RequestBody CheckPeselRequest request,
			@AuthenticationPrincipal JwtPrincipal principal) {

		registrationService.savePeselSearch(registrationMapper.toCommand(request, principal));
		if (registrationService.isPeselFree(registrationMapper.toQuery(request))) {
			return ResponseEntity.ok(new SuccessResponse<>(PESEL_FREE));
		} else {
			return ResponseEntity.ok(new SuccessResponse<>(PESEL_NOT_FREE));
		}
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'ADMIN', 'EMPLOYEE')")
	@PostMapping
	ResponseEntity<SuccessResponse<String>> registerUser(
			@RequestBody RegisterRequest request,
			@AuthenticationPrincipal JwtPrincipal principal) {

		String callerCif = principal.cif();
		if (!registrationService.peselWasSearched(callerCif, request.pesel())) {
			throw new BankingException(PESEL_NOT_SEARCHED);
		}
		RegisterUserRespond respond = registrationService.registerUser(registrationMapper.toCommand(request));
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/users/{id}")
				.buildAndExpand(respond.id())
				.toUri();
		return ResponseEntity.created(location).body(new SuccessResponse<>(respond.token(), USER_REGISTERED));
	}
}
