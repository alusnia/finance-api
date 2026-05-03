package com.financeapi.finance_api.controller;

import com.financeapi.finance_api.dto.TransferRequest;
import com.financeapi.finance_api.entity.Account;
import com.financeapi.finance_api.entity.User;
import com.financeapi.finance_api.repository.AccountRepository;
import com.financeapi.finance_api.repository.UserRepository;
import com.financeapi.finance_api.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	private final AccountService accountService;

	public AccountController(AccountRepository accountRepository, UserRepository userRepository, AccountService accountService) {
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.accountService = accountService;
	}

	@GetMapping()
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@PostMapping("/user/{userId}")
	public Account createAccount(@PathVariable Long userId, @RequestBody Account account) {
		User owner = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
		account.setUser(owner);

		return accountRepository.save(account);
	}

	@GetMapping("/user/{userId}")
	public List<Account> getAccountsByUser(@PathVariable Long userId) {
		return accountRepository.findByUser_Id(userId);
	}

	@PostMapping("/transfer")
	public String makeTransfer(@RequestBody TransferRequest transferRequest) {
		accountService.transferMoney(
				transferRequest.getAccountId(),
				transferRequest.getReceiverAccountNumber(),
				transferRequest.getTitle(),
				transferRequest.getAmount()
		);
		return "Transfer successful";
	}

	@DeleteMapping("/{id}")
	public void deleteAccount(@PathVariable @RequestBody Long id) {
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + id));
		accountRepository.delete(account);
	}
}
