package com.financeapi.finance_api.user.repository;

import com.financeapi.finance_api.user.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
}
