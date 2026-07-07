package com.financeapi.finance_api.core.security.global;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.financeapi.finance_api.core.exception.BankingError.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final JwtMapper jwtMapper;

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
			final TokenResponse tokenResponse = jwtService.extractInfo(jwtToken);
			if (tokenResponse != null && tokenResponse.id() != null && tokenResponse.role() != null) {
				JwtPrincipal principal = jwtMapper.toPrincipal(tokenResponse);
				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + tokenResponse.role());
					List<SimpleGrantedAuthority> roles = Collections.singletonList(authority);
					UsernamePasswordAuthenticationToken pass = new UsernamePasswordAuthenticationToken(
							principal,
							null,
							roles
					);
					pass.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(pass);
					}
			}
		} catch (ExpiredJwtException e) {
			logger.error(JWT_TOKEN_EXPIRED.getDevLog());
		} catch (JwtException e) {
			logger.error(JWT_TOKEN_NOT_VALID.getDevLog());
		}
		filterChain.doFilter(request, response);
	}
}
