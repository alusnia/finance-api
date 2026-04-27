package com.financeapi.finance_api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

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

	public Long getId() {
		return id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
