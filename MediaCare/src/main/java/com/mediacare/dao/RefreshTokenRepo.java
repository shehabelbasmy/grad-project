package com.mediacare.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mediacare.entity.RefreshToken;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	public Optional<RefreshToken> findByToken(String refreshToken);

	public void deleteByToken(String token);

	@Procedure(procedureName = "checkForRefreshToken")
	public String isTokenExist(@Param("token_in") String toke_in);

}
