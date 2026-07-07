package com.financeapi.finance_api.core.security.global;

import com.financeapi.finance_api.core.system.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
	@Value("${security.jwt.token.secret-key:dG9KZXN0QmFyZHpvVGFqbnlLbHVjejEyMzQ1Njc4OTA=}")
	private String secretKeyString;

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(TokenCommand command) {
		return Jwts.builder()
				.subject(command.cif())
				.claim("id", command.id())
				.claim("role", command.role().toString())
				.claim("email", command.email())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + command.timeInMillis()))
				.signWith(getSignInKey())
				.compact();
	}

	private String extractCif(String token, SecretKey key) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}

	private Role extractRole(String token, SecretKey key) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("role", Role.class);
	}

	private String extractEmail(String token, SecretKey key) {
		return Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("email", String.class);
	}

	private Long extractId(String token, SecretKey key) {
		return Long.parseLong(Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("id", String.class)
		);
	}

	public TokenResponse extractInfo(String token) {
		SecretKey key = getSignInKey();
		return new TokenResponse(
				extractCif(token, key),
				extractId(token, key),
				extractRole(token, key),
				extractEmail(token, key)
		);
	}
}
