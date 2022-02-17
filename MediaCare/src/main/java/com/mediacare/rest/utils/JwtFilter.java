package com.mediacare.rest.utils;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)throws ServletException, IOException {

		String jwt = getJwtFromRequest(request);

		if (validateToken(jwt)) {
			
			String email = jwtProvider.getEmailFromJwt(jwt);
			
			Collection<? extends GrantedAuthority> roles =jwtProvider.getRoleFromJwt(jwt);

			UsernamePasswordAuthenticationToken usernameToken = 
					new UsernamePasswordAuthenticationToken(
					email,
					null, 
					roles);

			usernameToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(usernameToken);
		}

		filterChain.doFilter(request, response);

	}

	private boolean validateToken(String jwt) {
		return  StringUtils.hasText(jwt)&&
				!jwtProvider.isTokenExpired(jwt)&&
				jwtProvider.validateToken(jwt);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		return bearerToken;
	}

}
