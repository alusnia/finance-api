package com.financeapi.finance_api.user.entity;

import com.financeapi.finance_api.account.entity.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.financeapi.finance_api.user.entity.Role.USER;

@lombok.Getter
@Entity
@Builder
@Table(name = "bank_users")
public class User {

	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@Setter
	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role = USER;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Account> accounts = new ArrayList<>();

	@Setter
	@Builder.Default
	@Embedded
	private SecurityLock securityLock = new SecurityLock(LockType.OPEN);

	@Setter
	@OneToOne(mappedBy = "user",  cascade = CascadeType.ALL)
	private Profile profile;

	@Setter
	@OneToOne(mappedBy = "user",  cascade = CascadeType.ALL)
	private UserCredentials userCredentials;

	public User() {}

	public User(Role role, List<Account> accounts,  SecurityLock securityLock, Profile profile, UserCredentials userCredentials) {
		this.role = role;
		this.accounts = accounts;
		this.securityLock = securityLock;
		this.profile = profile;
		this.userCredentials = userCredentials;
	}
}
