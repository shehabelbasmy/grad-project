package com.mediacare.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.mediacare.exception.MediaCareException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	private KeyStore keyStore;
	
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationMills;
	
	@PostConstruct
	public void init() {
		
		
		try {
			keyStore=KeyStore.getInstance("JKS");
			InputStream inputStream = getClass().getResourceAsStream("/mediacare.jks");
			keyStore.load(inputStream, "mediacare".toCharArray());
			
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			throw new MediaCareException("Exception occurred while loading keystore",e);
		}
		
	}
	
	public String generateToken(Authentication authentication) {
		User springUser = (User) authentication.getPrincipal();
		
		return Jwts.builder()
				   .setSubject(springUser.getUsername())
				   .setIssuedAt(Date.from(Instant.now()))
				   .signWith(getPrivateKey())
				   .setExpiration(Date.from(Instant.now().minusMillis(jwtExpirationMills)))
				   .compact();
		
	}
	public String generateTokenWithEmail(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMills)))
				.compact();
	}
	private PrivateKey getPrivateKey() {
		
		try {
			return (PrivateKey) keyStore.getKey("mediacare", "mediacare".toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new MediaCareException("Error occurred while retrieving public key from keyStore", e);
		}
		
		
	}
	
	public boolean validateToken(String jwt) {
		
		Jwts.parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJwt(jwt);
		
		return true;
	}
	
	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("mediacare").getPublicKey();
		} catch (KeyStoreException e) {
			throw new MediaCareException("Exception occured while retrieving public key from keystore", e);
			
		}
	}
	
	public String getEmailFromJwt(String jwt) {
		
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(getPrivateKey())
							.build()
							.parseClaimsJws(jwt)
							.getBody();
		return claims.getSubject();
	}
	
	public Long getJwtExpirationMills() {
		return jwtExpirationMills;
	}
}
