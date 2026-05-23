package com.financeapi.finance_api.user.entity;

public enum LockType {
    OPEN(0, 5),
    FIFTEEN_MINUTES(15, 3),
    THIRTY_MINUTES(30, 2),
    ONE_HOUR(60, 0),
    LOCKED(0, 0);

    private final int minutes   ;
    private final int retries;

    private LockType(int minutes,  int retries ) {
        this.minutes = minutes;
        this.retries = retries;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getRetries() {
        return this.retries;
    }

    public LockType next() {
        LockType[] values = LockType.values();
        int nextIndex = this.ordinal() + 1;
		if (nextIndex >= values.length) {
			return this;
		} else {
			return values[nextIndex];
		}
    }
}
