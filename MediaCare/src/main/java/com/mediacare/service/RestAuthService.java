package com.mediacare.service;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mediacare.dao.UserRepository;
import com.mediacare.rest.dto.AuthenticationResponse;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.mvc.dto.NewUserForm;
import com.mediacare.rest.dto.RefreshTokenRequest;
import com.mediacare.entity.MyUser;
import com.mediacare.enums.Authority;
import com.mediacare.util.SpringUser;
import com.mediacare.rest.utils.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestAuthService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	private final MessageSource messagesource;

	@Transactional
	public void register(NewUserForm newUser) {

		userRepo.save(buildUserEntity(newUser));
	}

	private MyUser buildUserEntity(NewUserForm newUser) {

		return MyUser.builder().authority(Authority.ADMIN).email(newUser.getEmail())
				.password(passwordEncoder.encode(newUser.getPassword())).firstName(newUser.getFirstName())
				.lastName(newUser.getLastName()).enabled(true).build();
	}

	@Transactional
	public ResponseEntity<AuthenticationResponse> login(LoginRequest loginRequest) {

		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword());

		Authentication authentication = this.authenticationManager.authenticate(userToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String refreshToken = refreshTokenService.generateRefreshToken().getToken();

		String jwtToken = jwtProvider.generateToken((SpringUser) authentication.getPrincipal());
		
		System.out.println("Jwt Generated"+Instant.now());

		AuthenticationResponse response = AuthenticationResponse.builder().email(loginRequest.getEmail()).refreshToken(refreshToken)
				.authenticationToken(jwtToken).build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	public ResponseEntity<AuthenticationResponse> createNewRefreshtoken(RefreshTokenRequest refreshRequest) {

		AuthenticationResponse newResponse;

		if (jwtProvider.validateToken(refreshRequest.getJwtToken())) {

			if (jwtProvider.isTokenExpired(refreshRequest.getJwtToken())) {

				String refreshTest = refreshTokenService.checkForTokenExist(refreshRequest.getRefreshToken());

				if (refreshTest != null) {

					String newtoken = jwtProvider.newtokenFromSameJwt(refreshRequest.getJwtToken());

					newResponse = buildAuthenticationResponse(refreshTest, newtoken);

					return ResponseEntity.status(HttpStatus.OK).body(newResponse);
				}

			}
			newResponse = buildAuthenticationResponse(refreshRequest.getRefreshToken(), refreshRequest.getJwtToken());

			return ResponseEntity.status(HttpStatus.OK).body(newResponse);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	private AuthenticationResponse buildAuthenticationResponse(String refreshTest, String jwtTest) {
		return AuthenticationResponse.builder().authenticationToken(jwtTest).refreshToken(refreshTest).build();
	}

	public ResponseEntity<?> logout(RefreshTokenRequest refreshRequest) {
		refreshTokenService.deleteFreshtoken(refreshRequest.getRefreshToken());
		jwtProvider.invalidateToken(refreshRequest.getJwtToken());
		String message=new MessageSourceAccessor(messagesource).getMessage("logoutmessage");
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

}
