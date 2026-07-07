package com.financeapi.finance_api.user.internal.core;

import com.financeapi.finance_api.core.system.Role;
import com.financeapi.finance_api.user.internal.auth.Auth;
import com.financeapi.finance_api.user.internal.profile.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.financeapi.finance_api.core.system.Role.USER;

@lombok.Getter
@Entity
@Table(name = "bank_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role = USER;

	@Embedded
	private Auth auth;

	@Embedded
	private Profile profile;

	public User(Auth auth, Profile profile) {
		this.auth = auth;
		this.profile = profile;
	}
}
