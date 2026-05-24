package com.financeapi.finance_api.core.security.global;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.financeapi.finance_api.core.exception.BankingError.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	JwtAuthenticationFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
	                                @NonNull HttpServletResponse response,
	                                @NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String jwtToken = authorizationHeader.substring("Bearer ".length());
		try {
			final String userId = jwtService.extractUserId(jwtToken);
			final String userRole = jwtService.extractRole(jwtToken);
			if (userId != null && userRole != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userRole);
				List<SimpleGrantedAuthority> roles = Collections.singletonList(authority);
				UsernamePasswordAuthenticationToken pass = new UsernamePasswordAuthenticationToken(
						userId,
						null,
						roles
				);
				SecurityContextHolder.getContext().setAuthentication(pass);
			}
		} catch (ExpiredJwtException e) {
			logger.error(JWT_TOKEN_EXPIRED.getDevLog());
		} catch (JwtException e) {
			logger.error(JWT_TOKEN_NOT_VALID.getDevLog());
		}
		filterChain.doFilter(request, response);
	}
}
