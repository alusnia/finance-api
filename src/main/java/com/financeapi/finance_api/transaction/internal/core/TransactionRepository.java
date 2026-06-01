package com.financeapi.finance_api.transaction.internal.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query("SELECT t FROM Transaction t WHERE t.senderAccountNumber = :accountNumber OR t.receiverAccountNumber= :accountNumber ORDER BY t.date DESC")
	List<Transaction> findAllAccountTransactions(String accountNumber);
}
