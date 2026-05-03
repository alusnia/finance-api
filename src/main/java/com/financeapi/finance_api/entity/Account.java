package com.financeapi.finance_api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@lombok.Getter
@Entity
@Table(name = "accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_number", nullable = false, unique = true, length = 28)
	private String accountNumber;

	@Column(nullable = false)
	private BigDecimal balance;

	@Column(nullable = false, length = 3)
	private String currency;

	@lombok.Setter
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Account() {
	}

	public Account(String accountNumber, BigDecimal balance, String currency, User user) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.currency = currency;
		this.user = user;
	}

	public boolean hasInsufficientFunds(BigDecimal amount) {
		return this.balance.compareTo(amount) < 0;
	}

	public void withdraw(BigDecimal amount) {
		this.balance = this.balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

	public boolean isTheSameAccount(Account account) {
		return this.accountNumber.equals(account.accountNumber);
	}

	public boolean isTheSameCurrency(Account account) {
		return this.currency.equals(account.currency);
	}

}
