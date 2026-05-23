package com.financeapi.finance_api.account.controller;

import com.financeapi.finance_api.account.controller.dto.AccountDetails;
import com.financeapi.finance_api.account.controller.dto.AccountRegistration;
import com.financeapi.finance_api.account.controller.dto.AccountRegistrationResponse;
import com.financeapi.finance_api.account.entity.Account;
import com.financeapi.finance_api.user.entity.User;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.account.mapper.AccountMapper;
import com.financeapi.finance_api.account.repository.AccountRepository;
import com.financeapi.finance_api.user.repository.UserRepository;
import com.financeapi.finance_api.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.financeapi.finance_api.core.exception.BankingError.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final AccountService accountService;
	private final AccountMapper accountMapper;

	public AccountController(AccountRepository accountRepository, UserRepository userRepository, AccountService accountService, AccountMapper accountMapper) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.accountService = accountService;
		this.accountMapper = accountMapper;
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'Admin')")
	@GetMapping()
	public ResponseEntity<List<AccountDetails>> getAllAccounts() {
		return ResponseEntity.ok(accountMapper.toDetails(accountRepository.findAll()));
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'Admin') or #userId.toString() == authentication.principal")
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AccountDetails>> getAccountsByUser(@PathVariable Long userId) {
		return ResponseEntity.ok(accountMapper.toDetails(accountRepository.findByUser_Id(userId)));
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'Admin') or #userId.toString() == authentication.principal")
	@PostMapping("/user/{userId}")
	public ResponseEntity<AccountRegistrationResponse> createAccount(@PathVariable Long userId, @RequestBody AccountRegistration accountRegistration) {
		String currency = accountRegistration.getCurrency();
		User owner = userRepository.findById(userId)
				.orElseThrow(() -> new BankingException(
						"NOT_FOUND",
						"User not found with id: " + userId,
						HttpStatus.NOT_FOUND
				));

		String accountNumber = accountService.generateUniqueAccountNumber();
		if (!accountService.isCurrencyValid(currency)) {
			throw new BankingException("INVALID_CURRENCY", "Currency is not valid", HttpStatus.BAD_REQUEST);
		}
		Account account = new Account(
				accountNumber,
				BigDecimal.ZERO,
				currency,
				owner
		);
		accountRepository.save(account);
		AccountRegistrationResponse response = new AccountRegistrationResponse("Account created successfully.", accountNumber);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PreAuthorize("hasAnyRole('HEAD_ADMIN', 'Admin') or @accountSecurity.isOwner(authentication.principal, #id)")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND));
		accountRepository.delete(account);
		return ResponseEntity.noContent().build();
	}
}
