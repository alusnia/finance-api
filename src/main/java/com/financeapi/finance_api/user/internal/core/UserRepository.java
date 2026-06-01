package com.financeapi.finance_api.user.internal.core;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsByAuth_Cif(String cif);
	boolean existsByAuth_Pesel(String pesel);
	Optional<User> findByAuth_Cif(String cif);
}
