package com.financeapi.finance_api.user.repository;

import com.financeapi.finance_api.user.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
	public boolean existsByPesel(String pesel);
	public boolean existsByCif(String cif);
	public Optional<UserCredentials> findByPesel(String pesel);
}
