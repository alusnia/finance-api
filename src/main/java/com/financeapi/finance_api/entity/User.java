package com.financeapi.finance_api.entity;

import jakarta.persistence.*;

@lombok.Getter
@Entity
@Table(name = "bank_users")
public class User {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "pesel", nullable = false, unique = true)
	private String pesel;

	public User() {}

	public User(Long id, String firstName, String lastName, String email, String password, String pesel) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.pesel = pesel;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

}
