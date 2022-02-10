package com.mediacare.utils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.mediacare.enums.Authority;
import com.mediacare.exception.MediaCareException;
import com.mediacare.service.UserDetailsServiceImpl;
import com.mediacare.util.SpringUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtProvider {

	private KeyStore keyStore;

	@Value("${jwt.expiration.time}")
	private Long jwtExpirationMills;

	@Autowired
	private UserDetailsServiceImpl userService;
	
	@PostConstruct
	public void init() {

		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = getClass().getResourceAsStream("/mediacare.jks");
			keyStore.load(inputStream, "mediacare".toCharArray());

		} catch (Exception e) {
			throw new MediaCareException("Exception occurred while loading keystore", e);
		}

	}

	public String generateToken(Authentication authentication) {
		
		return Jwts.builder()
					.setClaims(createClaims((SpringUser)authentication.getPrincipal()))
					.signWith(getPrivateKey())
					.setIssuedAt(Date.from(Instant.now()))
					.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMills)))
					.compact();
	}
	
	
	private Map<String, String> createClaims(SpringUser springUser){
		
		Map<String, String> clamis = new HashMap<String, String>();
		
		String role=springUser.getAuthorities().iterator().next().getAuthority();
		
		String roleUUID=Authority.valueOf(role).getUUID();
		
		clamis.put("email", springUser.getUsername());
		
		clamis.put("role", roleUUID);
		
		return clamis;
	}

	public String getEmailFromJwt(String jwt) {

		try {
			return parseJwtToke(jwt, "email");
			
		} catch (ExpiredJwtException e) {
			return e.getClaims().get("email", String.class);
		}
	}


	private PrivateKey getPrivateKey() {

		try {
			return (PrivateKey) keyStore.getKey(
					"mediacare", 
					"mediacare".toCharArray());
		} catch (Exception e) {
			throw new MediaCareException(
					"Error occurred while retrieving public key from keyStore",
					e);
		}

	}

	public boolean validateToken(String jwt,
			HttpServletResponse response)
			throws SignatureException,ExpiredJwtException{

		try {
			parseJwtToke(jwt,null);
			
		} catch (ExpiredJwtException ex) {
			
			return handleExpiredJwt(jwt, response);
			
		}catch (Exception e) {
			
			return false;
		}

		return true;

	}

	private boolean handleExpiredJwt(String jwt, HttpServletResponse response) {
		SpringUser springUser = 
				(SpringUser) userService.getUserByEmail(getEmailFromJwt(jwt));
		
		UsernamePasswordAuthenticationToken authToken=
			new UsernamePasswordAuthenticationToken(
					springUser, 
					null, 
					springUser.getAuthorities());
		
		if(!springUser.isLoggedOut()) {
			
			response.setHeader("Authorization", "Bearer " +generateToken(authToken));

			return true;
		}
		
		return false;
	}

	private String  parseJwtToke(String jwt,String property) {
		Jws<Claims> claims= Jwts.parserBuilder()
				.setSigningKey(getPublicKey())
				.build()
				.parseClaimsJws(jwt);
		
		if(property!=null) {		
			return claims.getBody().get(property, String.class);
		}	
		return null;
	}

	
	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("mediacare").getPublicKey();
		} catch (KeyStoreException e) {
			throw new MediaCareException("Exception occured while retrieving public key from keystore", e);

		}
	}

	public Long getJwtExpirationMills() {
		return jwtExpirationMills;
	}

	public Collection<? extends GrantedAuthority> getRoleFromJwt(String jwt) {
		
		String roleUUID ;
		try {
			roleUUID= parseJwtToke(jwt, "role");
			
		} catch (ExpiredJwtException e) {
			
			roleUUID=e.getClaims().get("role", String.class);
			
		}
		Authority authority = Authority.of(roleUUID);
		
		SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority.toString());
		
		return Collections.singletonList(simpleAuthority);
	}

}
