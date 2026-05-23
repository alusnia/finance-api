package com.financeapi.finance_api.registration.repository;

import com.financeapi.finance_api.registration.entity.PeselSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeselSearchesRepository extends JpaRepository <PeselSearch, Long> {
	public Optional<PeselSearch> findByCallerId(Long callerId);
}
