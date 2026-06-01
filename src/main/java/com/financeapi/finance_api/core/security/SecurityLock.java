package com.financeapi.finance_api.core.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Embeddable
public class SecurityLock {

	@Enumerated(EnumType.STRING)
	@Column(name = "lock_type", nullable = false)
	@Setter
    private LockType type;

	@Column(name = "retries_left", nullable = false)
    private int retriesLeft;

	@Column(name = "unlock_time")
    private LocalDateTime unlockTime;

	public SecurityLock() {
	}

    public SecurityLock(LockType type) {
        this.type = type;
        this.retriesLeft = type.getRetries();
        this.unlockTime = null;
    }

	public Long getRemainingMinutes() {
		if (type.equals(LockType.LOCKED)) {return null;}
		LocalDateTime now = LocalDateTime.now();
		return Duration.between(now, unlockTime).toMinutes();
	}

	public void reset() {
		this.type = LockType.OPEN;
		this.retriesLeft = this.type.getRetries();
		this.unlockTime = null;
	}

    public void wrongInput() {
        if (retriesLeft == 0) {
            this.type = type.next();
			this.unlockTime = LocalDateTime.now().plusMinutes(type.getMinutes());
			this.retriesLeft = type.getRetries();
        }
		else {
			this.retriesLeft--;
		}
    }

    public boolean isLocked() {
        if (type == LockType.OPEN) {
            return false;
        } else if (type == LockType.LOCKED) {
            return true;
        } else {
            LocalDateTime now = LocalDateTime.now();
            if (unlockTime == null) {
                return false;
            }
            return now.isBefore(unlockTime);
        }
    }
}
