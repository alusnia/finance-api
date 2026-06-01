package com.financeapi.finance_api.user.internal.auth;

import com.financeapi.finance_api.core.exception.BankingException;
import com.financeapi.finance_api.core.security.LockType;
import com.financeapi.finance_api.core.security.SecurityLock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.financeapi.finance_api.core.exception.BankingError.*;
import static com.financeapi.finance_api.core.exception.BankingException.LogType.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth {
	@Column(name = "CIF", unique = true, nullable = false)
	private String cif;

	@Setter(AccessLevel.PACKAGE)
	@Column(name = "hashed_password")
	private String hashedPassword;

	@Column(unique = true, nullable = false, length = 11)
	private String pesel;

	@Setter(AccessLevel.PACKAGE)
	@Column(name = "reset_token")
	private String resetToken;

	@Getter
	@Embedded
	private SecurityLock securityLock = new SecurityLock(LockType.OPEN);

	public Auth(String cif, String hashedPassword, String pesel) {
		this.cif = cif;
		this.hashedPassword = hashedPassword;
		this.pesel = pesel;
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
