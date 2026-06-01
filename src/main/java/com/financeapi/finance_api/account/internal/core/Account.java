package com.financeapi.finance_api.account.internal.core;

import com.financeapi.finance_api.core.system.Currencies;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@lombok.Getter
@Entity
@Table(name = "accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

	public enum AccountStatus {
		ACTIVE, FROZEN, CLOSED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "account_number", nullable = false, unique = true, length = 26)
	private String accountNumber;

	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal balance;

	@Column(nullable = false, length = 3)
	@Enumerated(EnumType.STRING)
	private Currencies currency;

	@Column(nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	public Account(Long userId, String accountNumber, Currencies currency) {
		this.userId = userId;
		this.accountNumber = accountNumber;
		this.balance = BigDecimal.ZERO;
		this.currency = currency;
		this.status = AccountStatus.ACTIVE;
	}

	void close() {
		this.status = AccountStatus.CLOSED;
	}

	void unFreeze() {
		if (this.status == AccountStatus.FROZEN) {
			this.status = AccountStatus.ACTIVE;
		}
	}

	void freeze() {
		this.status = AccountStatus.FROZEN;
	}

	boolean isActive() {
		return AccountStatus.ACTIVE.equals(status);
	}

	boolean hasInsufficientFunds(BigDecimal amount) {
		return this.balance.compareTo(amount) < 0;
	}

	void withdraw(BigDecimal amount) {
		this.balance = this.balance.subtract(amount);
	}

	void deposit(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

	boolean isTheSameAccount(Account account) {
		return this.accountNumber.equals(account.accountNumber);
	}

	boolean isTheSameCurrency(Account account) {
		return this.currency.equals(account.currency);
	}
}
