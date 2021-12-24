package com.mediacare.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mediacare.dao.RefreshTokenRepo;
import com.mediacare.entity.RefreshToken;
import com.mediacare.exception.MediaCareException;

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
	
	public void validateRefresToken(String refreshToken) {
		refreshTokenRepo.findByToken(refreshToken)
			.orElseThrow(() -> new MediaCareException("InvaildRefreshToken"));
	}
	
	public void deleteFreshtoken(String token) {
		refreshTokenRepo.deleteByToken(token);
	}
}
