package com.financeapi.finance_api.user.repository;

import com.financeapi.finance_api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByEmail(String email);
	public Optional<User> findByPesel(String pesel);
	public boolean existsByPesel(String pesel);
}
