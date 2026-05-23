package com.financeapi.finance_api.transaction.controller;

import com.financeapi.finance_api.transaction.controller.dto.TransactionDetails;
import com.financeapi.finance_api.transaction.controller.dto.TransferRequest;
import com.financeapi.finance_api.account.entity.Account;
import com.financeapi.finance_api.transaction.entity.Transaction;
import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.transaction.mapper.TransactionMapper;
import com.financeapi.finance_api.account.repository.AccountRepository;
import com.financeapi.finance_api.transaction.repository.TransactionRepository;
import com.financeapi.finance_api.transaction.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.financeapi.finance_api.core.exception.BankingError.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	private final TransactionRepository transactionRepository;
	private final AccountRepository accountRepository;
	private final TransactionMapper transactionMapper;
	private final TransactionService transactionService;

	public TransactionController(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMapper transactionMapper, TransactionService transactionService) {
		this.transactionRepository = transactionRepository;
		 this.accountRepository = accountRepository;
		 this.transactionMapper = transactionMapper;
		 this.transactionService = transactionService;
	}

//	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin') or @accountSecurity.isOwner(authentication.principal, #transferRequest.accountId)")
//	@PostMapping("/transfer")
//	public ResponseEntity<TransactionDetails> makeTransfer(@RequestBody TransferRequest transferRequest) {
//		TransactionDetails transactionDetails = transactionService.transferMoney(
//				transferRequest.getAccountId(),
//				transferRequest.getReceiverAccountNumber(),
//				transferRequest.getTitle(),
//				transferRequest.getAmount()
//		);
//
//		URI location = ServletUriComponentsBuilder
//				.fromCurrentContextPath()
//				.path("/api/transactions/{id}")
//				.buildAndExpand(transactionDetails.getId())
//				.toUri();
//		return ResponseEntity.created(location).body(transactionDetails);
//	}

	

//	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin')")
//	@GetMapping()
//	public ResponseEntity<List<TransactionDetails>> getAllTransactions() {
//		return ResponseEntity.ok(transactionMapper.toDetails(transactionRepository.findAll()));
//	}
//
//	@PreAuthorize( "hasAnyRole('HEAD_ADMIN', 'Admin') or @accountSecurity.isOwner(authentication.principal, #accountId)")
//	@GetMapping("/{accountId}")
//	public ResponseEntity<List<TransactionDetails>> getTransactionsByAccount(@PathVariable Long accountId) {
//		Account account = accountRepository.findById(accountId)
//				.orElseThrow(() -> new BankingException(ACCOUNT_NOT_FOUND));
//		String accountNumber = account.getAccountNumber();
//		List<Transaction> transactions = transactionRepository.findAllAccountTransactions(accountNumber);
//		return ResponseEntity.ok(transactionMapper.toDetails(transactions));
//	}
}
