package com.financeapi.finance_api.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Table(name = "profiles")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "telephone_number", nullable = false, unique = true)
	private String telephoneNumber;

	@Column(name = "country")
	private String country;

	@Column(name = "address")
	private String address;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	public Profile() {}

	public Profile(String firstName, String lastName, String email, String telephoneNumber, String country, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telephoneNumber = telephoneNumber;
		this.country = country;
		this.address = address;
	}
}
