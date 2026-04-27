package com.financeapi.finance_api.repository;

import com.financeapi.finance_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByUser_Id(Long userId);

	Optional<Account> findByAccountNumber(String accountNumber);
}
