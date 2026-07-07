package com.financeapi.finance_api.account.internal.core;

import com.financeapi.finance_api.core.security.global.JwtPrincipal;
import com.financeapi.finance_api.core.system.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.financeapi.finance_api.core.system.SuccessDetails.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;
	private final AccountMapper accountMapper;

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'Admin') or #userId.toString() == authentication.principal")
	@PostMapping("/open}")
	ResponseEntity<SuccessResponse<Void>> createAccount(
			@RequestBody NewAccountRequest request,
			@AuthenticationPrincipal JwtPrincipal principal
	) {
		Long accountId = accountService.createNewAccount(accountMapper.toCommand(request, principal));
		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/users/{id}")
				.buildAndExpand(accountId)
				.toUri();
		return ResponseEntity.created(location).body(new SuccessResponse<>(ACCOUNT_CREATED));
	}
//	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'Admin') or @accountSecurity.isOwner(authentication.principal, #id)")
//	@DeleteMapping("/{id}")
//	ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
//		Account account = accountRepository.findById(id)
//				.orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND));
//		accountRepository.delete(account);
//		return ResponseEntity.noContent().build();
	}
}
