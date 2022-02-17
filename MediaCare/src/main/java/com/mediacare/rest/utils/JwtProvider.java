package com.mediacare.rest.utils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.mediacare.entity.MyUser;
import com.mediacare.enums.Authority;
import com.mediacare.exception.MediaCareException;
import com.mediacare.util.SpringUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

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
		
		//executor=new ScheduledThreadPoolExecutor(1);
		executor = Executors.newSingleThreadScheduledExecutor();
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = getClass().getResourceAsStream("/mediacare.jks");
			keyStore.load(inputStream, "mediacare".toCharArray());

		} catch (Exception e) {
			throw new MediaCareException("Exception occurred while loading keystore", e);
		}

	}

	public String generateToken(SpringUser springUser) {
		
		return Jwts.builder()
					.setClaims(createClaims(springUser))
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
			
		} catch (ExpiredJwtException ex) {
			return ex.getClaims().get("email", String.class);
		}
		catch (Exception e) {
			return null;
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

	public boolean validateToken(String jwt){

		try {
			parseJwtToke(jwt,null);
			
			if(jwtQueue.contains(jwt)) {
				
				return false;
			}
		}catch (Exception e) { 
			
			if (e instanceof ExpiredJwtException) {
				return true;
			}
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
			
		} catch (ExpiredJwtException e) {

			if (e instanceof ExpiredJwtException) {
				return true;	
			}
			return false;
		}
		
	}

	public String newtokenFromSameJwt(String jwtToken) {
		MyUser myuser=
				MyUser.builder()
				.email(getEmailFromJwt(jwtToken))
				.authority(Authority.valueOf(getRoleFromJwt(jwtToken).iterator().next().getAuthority()))
				.build();

		return generateToken(new SpringUser(myuser));
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

		System.out.println("Logout"+Instant.now());
				
	}
}
