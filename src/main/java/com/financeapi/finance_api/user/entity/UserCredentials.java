package com.financeapi.finance_api.user.entity;

import com.financeapi.finance_api.core.exception.BankingException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;

@Getter
@Entity
@Table(name = "user_credentials")
public class UserCredentials {

	@Id
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Setter
	@Column(name = "password_hash",nullable = false)
	private String passwordHash;

	@Column(unique = true, nullable = false, length = 11)
	private String pesel;

	@Column(name = "mothers_maiden_name", nullable = false)
	private String mothersMaidenName;

	@Setter
	@Column(name = "reset_token")
	private String resetToken;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	public UserCredentials() {
	}

	public UserCredentials(Long id, String username, String passwordHash, String pesel,
						   String mothersMaidenName, String resetToken, User user) {
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.pesel = pesel;
		this.mothersMaidenName = mothersMaidenName;
		this.resetToken = resetToken;
		this.user = user;
	}

	public void eraseToken() {
		this.resetToken = null;
	}

	public void checkToken(String userToken) {
		if  (this.resetToken == null) {
			throw new BankingException(USER_CREDENTIALS_TOKEN_NOT_FOUND);
		} else if (userToken == null || !userToken.equals(this.resetToken)) {
			String devLog = String.format("User provided token: %s, expected: %s", userToken, this.resetToken);
			eraseToken();
			throw new BankingException(JWT_TOKEN_NOT_VALID).extendMessage("Please make new request.").log(devLog, WARNING);
		}
	}
}
