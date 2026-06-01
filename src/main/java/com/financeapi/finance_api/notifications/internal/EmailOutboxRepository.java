package com.financeapi.finance_api.notifications.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmailOutboxRepository extends JpaRepository<Email, Long> {
	@Query(value = """
		SELECT * FROM email_outbox
		WHERE status = 'PENDING' AND retry_after <= now()
		ORDER BY created_at ASC
		LIMIT :limit
		FOR UPDATE SKIP LOCKED
		""", nativeQuery = true)
	List<Email> findPendingEmails(@Param("limit") int limit);
}
