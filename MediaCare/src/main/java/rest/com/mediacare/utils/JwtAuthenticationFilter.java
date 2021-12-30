package com.mediacare.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final UserDetailsService userservice;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain filterChain)
									throws ServletException, IOException {
		String jwt = getJwtFromRequest(request);
		
		if (StringUtils.hasText(jwt)&&jwtProvider.validateToken(jwt)) {
			
			String email=jwtProvider.getEmailFromJwt(jwt);
			
			UserDetails userDetails=userservice.loadUserByUsername(email);
		
			UsernamePasswordAuthenticationToken usernameToken = 
					new UsernamePasswordAuthenticationToken(userDetails.getUsername(), 
					null,
					userDetails.getAuthorities());
			usernameToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			SecurityContextHolder.getContext().setAuthentication(usernameToken);
		}
		filterChain.doFilter(request, response);
		
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if (StringUtils.hasText(bearerToken)&&bearerToken.startsWith("bearer")) {
			return bearerToken.substring(7);
		}
		return bearerToken;
	}

}
