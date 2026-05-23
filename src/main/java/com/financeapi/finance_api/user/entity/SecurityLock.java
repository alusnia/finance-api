package com.financeapi.finance_api.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Embeddable
public class SecurityLock {

	@Enumerated(EnumType.STRING)
	@Column(name = "lock_type")
	@Setter
    private LockType type;

    private int retriesLeft;

    private LocalDateTime unlockTime;

	public SecurityLock() {
	}

    public SecurityLock(LockType type) {
        this.type = type;
        this.retriesLeft = type.getRetries();
        this.unlockTime = null;
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
