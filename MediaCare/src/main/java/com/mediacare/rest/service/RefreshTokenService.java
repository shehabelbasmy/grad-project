package com.mediacare.rest.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mediacare.dao.RefreshTokenRepo;
import com.mediacare.entity.RefreshToken;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepo refreshTokenRepo;
	
	public RefreshToken generateRefreshToken() {
		
		RefreshToken refreshToken=new RefreshToken(); 
		
		refreshToken.setToken(UUID.randomUUID().toString());
		
		return refreshTokenRepo.save(refreshToken);
	}
	
	public void deleteFreshtoken(String token) {
		
		refreshTokenRepo.deleteByToken(token);
	}

	public String checkForTokenExist(String refreshToken) {

		return refreshTokenRepo.isTokenExist(refreshToken);
		
	}
}
