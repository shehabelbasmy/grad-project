package com.mediacare.service;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mediacare.dao.UserRepository;
import com.mediacare.dto.NewUserForm;
import com.mediacare.entity.MyUser;
import com.mediacare.enums.Authority;
import com.mediacare.rest.dto.AuthetcationResponse;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.utils.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	
	@Transactional
	public void register(NewUserForm newUser) {

		userRepo.save(buildUserEntity(newUser));
	}
	
	private MyUser buildUserEntity(NewUserForm newUser) {
		
		return MyUser.builder()
			  .authority(Authority.ADMIN)
			  .email(newUser.getEmail())
			  .password(passwordEncoder.encode(newUser.getPassword()))
			  .firstName(newUser.getFirstName())
			  .lastName(newUser.getLastName())
			  .enabled(true)
			  .build();
		
		
	}

	@Transactional
	public AuthetcationResponse login(LoginRequest loginRequest) {
		
		UsernamePasswordAuthenticationToken userToken =new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
		
		Authentication authentication = this.authenticationManager.authenticate(userToken);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtProvider.generateToken(authentication);
		
		return AuthetcationResponse
				.builder()
				.email(loginRequest.getEmail())
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.authenticationToken(token)
				.expiredAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationMills()))
				.build();
	}

}
