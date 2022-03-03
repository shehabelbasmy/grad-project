package com.mediacare.rest.service;

import com.mediacare.entity.RefreshToken;
import com.mediacare.rest.dao.RefreshTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

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
