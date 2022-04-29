package com.mediacare.rest.utils;

import com.mediacare.enums.Authority;
import com.mediacare.exception.MediaCareException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class JwtProvider {

	private KeyStore keyStore;
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationMills;
	@Autowired
	private Queue<String> jwtQueue;
	private ScheduledExecutorService executor;
	@Autowired
	private Runnable clearJwtTask;
	
	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadScheduledExecutor();
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = getClass().getResourceAsStream("/mediacare.jks");
			keyStore.load(inputStream, "mediacare".toCharArray());

		} catch (Exception e) {
			throw new MediaCareException("Exception occurred while loading keystore", e);
		}

	}
	public String generateToken(String email,Authority authority) {

		return Jwts.builder()
				.setClaims(createClaims(email,authority))
				.signWith(getPrivateKey())
				.setIssuedAt(Date.from(Instant.now()))
				//Date.from(Instant.now().plusMillis(jwtExpirationMills))
				.setExpiration(null)
				.compact();
	}
	
	private Map<String, String> createClaims(String email,Authority authority){
		Map<String, String> claims = new HashMap<String, String>();
		claims.put("email", email);
		claims.put("role", authority.getUUID());
		
		return claims;
	}

	public String getEmailFromJwt(String jwt) {

		try {
			return parseJwtToke(jwt, "email");
			
		} catch (ExpiredJwtException ex) {
			return ex.getClaims().get("email", String.class);
		}
		catch (Exception e) {
			return null;
		}
	}


	private PrivateKey getPrivateKey() {

		try {
			return (PrivateKey) keyStore.getKey("mediacare", "mediacare".toCharArray());
		} catch (Exception e) {
			throw new MediaCareException(
					"Error occurred while retrieving public key from keyStore", e);
		}
	}

	public boolean validateToken(String jwt){

		try {
			parseJwtToke(jwt,null);
			if(jwtQueue.contains(jwt)) {
				return false;
			}
		}catch (ExpiredJwtException e){
			return true;
		}
		catch (Exception e) {
			return false;
		}
		return true;
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
		} catch (Exception e) {
			throw new MediaCareException("Exception occured while retrieving public key from keystore", e);
			
		}
	}

	public Collection<? extends GrantedAuthority> getRoleFromJwt(String jwt) {
		String roleUUID="";
		try {
			roleUUID = parseJwtToke(jwt, "role");
		} catch (ExpiredJwtException ex) {
			roleUUID = ex.getClaims().get("role", String.class);
		}
		Authority authority = Authority.of(roleUUID);
		SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority.toString());
		return Collections.singletonList(simpleAuthority);
	}

	public boolean isTokenExpired(String jwtToken) {
		try {
			parseJwtToke(jwtToken, null);
			return false;
		} catch (ExpiredJwtException e){
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public String newTokenFromSameJwt(String jwtToken) {

		Authority authority = Authority.valueOf(getRoleFromJwt(jwtToken).iterator().next().getAuthority());
		return generateToken(getEmailFromJwt(jwtToken),authority);
	}
	
	public void invalidateToken(String jwt) {
		if (jwtQueue.isEmpty()) {
			executor.scheduleAtFixedRate(
			clearJwtTask,
			jwtExpirationMills,
			jwtExpirationMills,
			TimeUnit.MILLISECONDS);
		}
		jwtQueue.add(jwt);
	}
}
