package com.financeapi.finance_api.user.internal.registration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

interface PeselSearchesRepository extends JpaRepository <PeselSearch, Long> {
	boolean existsByCifAndPeselAndSearchedAtAfter(String phoneNumber, String code, LocalDateTime time);
}
