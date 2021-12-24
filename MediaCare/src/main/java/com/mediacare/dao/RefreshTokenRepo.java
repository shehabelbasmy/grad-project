package com.mediacare.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediacare.entity.RefreshToken;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	public Optional<RefreshToken> findByToken(String refreshToken);

	public void deleteByToken(String token);

}
