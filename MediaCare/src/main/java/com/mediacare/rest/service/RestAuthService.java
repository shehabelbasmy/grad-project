package com.mediacare.rest.service;

import com.mediacare.entity.Patient;
import com.mediacare.enums.Authority;
import com.mediacare.mapper.UserMapper;
import com.mediacare.mvc.dto.NewUserDto;
import com.mediacare.rest.dao.PatientRepository;
import com.mediacare.rest.dto.AuthenticationResponse;
import com.mediacare.rest.dto.LoginRequest;
import com.mediacare.rest.dto.RefreshTokenRequest;
import com.mediacare.rest.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
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

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RestAuthService {

	private final PatientRepository patientRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	private final MessageSource messagesource;
	private final UserMapper userMapper;
	private final AuthenticationManager authManagerRest;

	@Transactional
	public AuthenticationResponse registerNewPatient(NewUserDto newUser) {

		Patient patient = userMapper.newUserToPatient(newUser);
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		patientRepository.save(patient);
		String jwtToken=jwtProvider.generateToken(newUser.getEmail(),patient.getAuthority());
		String refreshToken=refreshTokenService.generateRefreshToken().getToken();

		return buildAuthenticationResponse(refreshToken,jwtToken);
	}

	public ResponseEntity<?> login(LoginRequest loginRequest) {

		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword());
		Authentication authentication = this.authManagerRest.authenticate(userToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String refreshToken = refreshTokenService.generateRefreshToken().getToken();
		Authority authority = Authority.valueOf(authentication.getAuthorities().iterator().next().getAuthority());
		String jwtToken = jwtProvider.generateToken(loginRequest.getEmail(),authority);
		AuthenticationResponse response =
			AuthenticationResponse.builder()
				.refreshToken(refreshToken)
				.authenticationToken(jwtToken)
				.build();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	public ResponseEntity<?> createNewRefreshtoken(RefreshTokenRequest refreshRequest) {
		AuthenticationResponse newResponse;
		if (jwtProvider.validateToken(refreshRequest.getJwtToken())) {
			if (jwtProvider.isTokenExpired(refreshRequest.getJwtToken())) {
				String refreshTest = refreshTokenService.checkForTokenExist(refreshRequest.getRefreshToken());
				if (refreshTest != null) {
					String newToken = jwtProvider.newTokenFromSameJwt(refreshRequest.getJwtToken());
					newResponse = buildAuthenticationResponse(refreshTest, newToken);
					return ResponseEntity.status(HttpStatus.OK).body(newResponse);
				}
			}
			newResponse = buildAuthenticationResponse(refreshRequest.getRefreshToken(), refreshRequest.getJwtToken());
			return ResponseEntity.status(HttpStatus.OK).body(newResponse);
		}
		return new ResponseEntity<>("You Are Logged Out",HttpStatus.BAD_REQUEST);
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
