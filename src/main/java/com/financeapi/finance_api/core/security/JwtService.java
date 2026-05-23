package com.financeapi.finance_api.core.security;

import com.financeapi.finance_api.user.entity.User;
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

	public String generateToken(User user,  long timeInMillis) {
		return Jwts.builder()
				.subject(user.getId().toString())
				.claim("role", user.getRole().toString())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + timeInMillis))
				.signWith(getSignInKey())
				.compact();
	}

	public String extractRole(String token) {
		return Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("role", String.class);
	}

	public String extractUserId(String token) {
		return Jwts.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
}
