package com.financeapi.finance_api.repository;

import com.financeapi.finance_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findBySenderAccountOrReceiverAccountOrderByDateDesc(String senderAccount, String receiverAccount);
}
