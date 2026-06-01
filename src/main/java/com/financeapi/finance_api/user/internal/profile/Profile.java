package com.financeapi.finance_api.user.internal.profile;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
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

	public Profile(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

}
