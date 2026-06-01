package com.financeapi.finance_api.transaction.internal.core;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;
	private final TransactionService transactionService;

	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin') or @accountSecurity.isOwner(authentication.principal, #transferRequest.accountId)")
	@PostMapping("/transfer")
	public ResponseEntity<TransactionDetails> makeTransfer(@RequestBody TransferRequest transferRequest) {
		TransactionDetails transactionDetails = transactionService.transferMoney(
				transferRequest.getAccountId(),
				transferRequest.getReceiverAccountNumber(),
				transferRequest.getTitle(),
				transferRequest.getAmount()
		);

		URI location = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/api/transactions/{id}")
				.buildAndExpand(transactionDetails.getId())
				.toUri();
		return ResponseEntity.created(location).body(transactionDetails);
	}



	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin')")
	@GetMapping()
	public ResponseEntity<List<TransactionDetails>> getAllTransactions() {
		return ResponseEntity.ok(transactionMapper.toDetails(transactionRepository.findAll()));
	}

	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin') or @accountSecurity.isOwner(authentication.principal, #accountId)")
	@GetMapping("/{accountId}")
	public ResponseEntity<List<TransactionDetails>> getTransactionsByAccount(@PathVariable Long accountId) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND));
		String accountNumber = account.getAccountNumber();
		List<Transaction> transactions = transactionRepository.findAllAccountTransactions(accountNumber);
		return ResponseEntity.ok(transactionMapper.toDetails(transactions));
	}
}
