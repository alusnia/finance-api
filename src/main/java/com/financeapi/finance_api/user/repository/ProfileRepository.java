package com.financeapi.finance_api.user.repository;

import com.financeapi.finance_api.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
