package com.mediacare.rest.dao;

import com.mediacare.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

	void deleteByToken(String token);

	@Procedure(procedureName = "checkForRefreshToken")
	String isTokenExist(@Param("token_in") String toke_in);

}
